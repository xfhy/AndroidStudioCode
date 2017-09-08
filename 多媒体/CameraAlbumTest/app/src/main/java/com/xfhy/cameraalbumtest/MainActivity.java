package com.xfhy.cameraalbumtest;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 2017年2月26日22:04:32
 * 调用摄像头拍照
 * 选择本地相册的图片并显示
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 申请码
     */
    private static final int TAKE_PHOTO = 1;    //照相
    private static final int CHOOSE_PHOTO = 2;  //打开相册

    private static final String TAG = "MainActivity";

    private Button bt_take_photo;   //拍照按钮
    private Button bt_choose_from_album;
    private ImageView iv_picture;  //显示图片

    private Uri imageUri;  //标示图片路径的uri

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_take_photo = (Button) findViewById(R.id.bt_take_photo);
        bt_choose_from_album = (Button) findViewById(R.id.bt_choose_from_album);
        iv_picture = (ImageView) findViewById(R.id.iv_picture);

        bt_take_photo.setOnClickListener(this);
        bt_choose_from_album.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_take_photo:
                takePhoto();
                break;
            case R.id.bt_choose_from_album:
                requestPermission();
                break;
            default:
                break;
        }
    }

    /**
     * 申请权限   需要选择相册中的图片,则需要读SD卡的权限
     */
    private void requestPermission() {
        //检查是否有读SD卡的权限   不相等则需要申请权限
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请读SD的权限
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            openAlbum();
        }
    }

    /**
     * 打开相册  进行图片选择
     */
    private void openAlbum() {
        //
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);  //打开相册
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {   //根据申请码进行判断
            case 1:
                //判断权限是否申请成功
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "申请读SD卡权限失败", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 调用摄像头拍照
     */
    private void takePhoto() {
        //1. 创建File对象,用于存储拍照后的图片
        //这里的getExternalCacheDir()是应用的关联缓存目录,在SD卡下面(/sdcard/Android/data/<package name>/cache)
        //这里在Android 6.0运行时不用进行运行时权限处理
        File outputImage = new File(getExternalCacheDir(), "output_image.jpg");

        //2. 判断文件是否存在
        try {
            if (outputImage.exists()) {
                outputImage.delete();
                outputImage.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //3. 判断当前用户的设备的系统版本是否大于24(android 7.0)
        if (Build.VERSION.SDK_INT >= 24) {
            //4. 将File对象转化为一个封装好的Uri对象
            imageUri = FileProvider.getUriForFile(MainActivity.this,
                    "com.xfhy.cameraalbumtest.fileprovider", outputImage);
        } else {
            imageUri = Uri.fromFile(outputImage);
        }

        //5.启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //请求码
        switch (requestCode) {
            case TAKE_PHOTO:
                //如果返回码是RESULT_OK,则是成功拍照了的
                if (resultCode == RESULT_OK) {
                    try {
                        //6. 将拍摄的照片显示出来
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        iv_picture.setImageBitmap(bitmap);  //设置显示图片
                        Log.i(TAG, "onActivityResult: ");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    //判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        //4.4及以上系统使用这个方法处理图片
                        handleImageOnKitKat(data);
                    } else {
                        //4.4以下系统使用这个方法处理图片
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }


    /**
     * 4.4及以上系统使用这个方式处理图片
     * @param data
     */
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if(DocumentsContract.isDocumentUri(this,uri)){
            //如果是document类型的Uri,则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                //解析出数字格式的id
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID+"="+id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            } else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath = getImagePath(contentUri,null);
            }
        } else if("content".equalsIgnoreCase(uri.getScheme())){
            //如果是content类型的uri,则使用普通方式处理
            imagePath = getImagePath(uri,null);
        } else if("file".equalsIgnoreCase(uri.getScheme())){
            //如果是file类型的uri,直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath);
    }

    /**
     * 显示图片
     * @param imagePath
     */
    private void displayImage(String imagePath) {
        if(imagePath != null){
            //BitmapFactory可以将指定路径文件转换成Bitmap对象
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            iv_picture.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取图片路径
     * @param uri
     * @param selection
     * @return
     */
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过Uri和selection来获取图片真实的路径
        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        //首先需要判断cursor是否为空
        if (cursor != null) {
            if(cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            //最后一定要记得关闭cursor
            cursor.close();
        }
        return path;
    }

    /**
     * 4.4以下系统使用这个方法处理图片
     *
     * @param data
     */
    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri,null);
        displayImage(imagePath);
    }
}

package com.xfhy.contactstest;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.xfhy.contactstest.bean.MContact;

import java.util.ArrayList;
import java.util.List;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

public class MainActivity extends AppCompatActivity {

    ContactAdapter adapter;
    List<MContact> contactList = new ArrayList<>();
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        RecyclerView rv_contact = (RecyclerView) findViewById(R.id.rv_contact);

        //1. 创建适配器  并传入数据
        adapter = new ContactAdapter(contactList,mContext);
        //2. 创建LinearLayoutManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //设置LinearLayoutManager
        rv_contact.setLayoutManager(linearLayoutManager);
        ItemDecoration itemDecoration = new ItemDecoration(this,ItemDecoration.VERTICAL_LIST);
        rv_contact.addItemDecoration(itemDecoration);
        //3. 设置适配器
        rv_contact.setAdapter(adapter);

        //申请权限
        PermissionGen.needPermission(this, 100,
                new String[] {
                        Manifest.permission.READ_CONTACTS,    //读取联系人
                        Manifest.permission.CALL_PHONE,       //打电话
                }
        );

        /*//检查用户是否已经授权了读取联系人的权限     如果相等则授权了
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            //不相等   则申请权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS}, 1);
        } else {
            readContacts();
        }*/



    }



    /**
     * 读取联系人
     */
    private void readContacts() {
        Cursor cursor = null;

        try{
            //查询联系人数据   得到Cursor对象
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,null,null,null);
            if(cursor != null){
                while(cursor.moveToNext()){
                    //获取联系人姓名
                    String name = cursor.getString(cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    //获取联系人号码
                    String phone = cursor.getString(cursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER));
                    contactList.add(new MContact(name,phone));
                }
                adapter.notifyItemInserted(contactList.size()-1);   //更新RecyclerView
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            //最后一定要记得关闭cursor
            if(cursor != null){
                cursor.close();
            }
        }

    }

    //每申请一此危险权限,这个方法就会被调一次
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
       /* super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //根据申请码 进行判断
        switch (requestCode) {
            case 1:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    readContacts();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }

                break;
            default:
                break;
        }*/
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    //申请权限成功时
    @PermissionSuccess(requestCode = 100)
    public void doSomething(){
        Toast.makeText(this, "Contact permission is granted", Toast.LENGTH_SHORT).show();
        readContacts();   //读取联系人
    }

    //申请权限失败时
    @PermissionFail(requestCode = 100)
    public void doFailSomething(){
        Toast.makeText(this, "Contact permission is not granted", Toast.LENGTH_SHORT).show();
    }

    class ItemDecoration extends RecyclerView.ItemDecoration{
        //使用系统自带的listDivider
        private final int[] ATTRS = new int[]{
                android.R.attr.listDivider
        };

        public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
        public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

        private Drawable mDivider;
        private int mOrientation;

        public ItemDecoration(Context context, int orientation) {
            //使用TypeArray加载该系统资源
            final TypedArray ta = context.obtainStyledAttributes(ATTRS);
            mDivider = ta.getDrawable(0);
            //缓存
            ta.recycle();
            setOrientation(orientation);
        }

        public void setOrientation(int orientation){
            if(orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST){
                throw new IllegalArgumentException("invalid orientation");
            }
            mOrientation = orientation;
        }
        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            if(mOrientation == VERTICAL_LIST){
                drawVertical(c,parent);
            }else{
                drawHorizontal(c,parent);
            }
        }

        public void drawVertical(Canvas c,RecyclerView parent){
            //获取分割线的左边距，即RecyclerView的padding值
            final int left = parent.getPaddingLeft();
            //分割线右边距
            final int right = parent.getWidth() - parent.getPaddingRight();
            final int childCount = parent.getChildCount();
            //遍历所有item view，为它们的下方绘制分割线
            for(int i=0;i<childCount;i++){
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                final int top = child.getBottom() + params.bottomMargin;
                final int bottom = top + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left,top,right,bottom);
                mDivider.draw(c);
            }
        }

        public void drawHorizontal(Canvas c, RecyclerView parent) {
            final int top = parent.getPaddingTop();
            final int bottom = parent.getHeight() - parent.getPaddingBottom();

            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                final int left = child.getRight() + params.rightMargin;
                final int right = left + mDivider.getIntrinsicHeight();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            if(mOrientation == VERTICAL_LIST){
                //设置偏移的高度是mDivider.getIntrinsicHeight，该高度正是分割线的高度
                outRect.set(0,0,0,mDivider.getIntrinsicHeight());
            }else{
                outRect.set(0,0,mDivider.getIntrinsicWidth(),0);
            }
        }
    }

}

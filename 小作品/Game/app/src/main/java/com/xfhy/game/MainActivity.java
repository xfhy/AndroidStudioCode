package com.xfhy.game;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * ① 实现静态布局的排版
 * GridLayout
 * ② 随机初始化资源
 * 设计一个判定游戏结束的模型
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, Handler
        .Callback {

    private static final int UPDATE_TIME = 100;
    private GridLayout mGroup;

    private int[] pics = {R.drawable.img_xiaoxiong_00x00, R.drawable.img_xiaoxiong_00x01, R
            .drawable.img_xiaoxiong_00x02
            , R.drawable.img_xiaoxiong_01x00, R.drawable.img_xiaoxiong_01x01, R.drawable
            .img_xiaoxiong_01x02
            , R.drawable.img_xiaoxiong_02x00, R.drawable.img_xiaoxiong_02x01, R.drawable
            .img_xiaoxiong_02x02};

    private int[] picIndex = {0, 1, 2, 3, 4, 5, 6, 7, 8};

    private Bitmap[] bitmaps = new Bitmap[9];

    private int blankIndex = 8;
    private Button mStart;
    private TextView mGameTime;
    private int time;
    // 是否游戏中的一个标记
    private boolean isGaming;

    private Handler mHandler = new Handler(this);

    /**
     * Android 中使用Bitmap来存放图片
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBitmaps();
        initView();
        randomInitView();
    }

    private void initBitmaps() {
        // 加载完成的图片
        Bitmap bitmapResource = BitmapFactory.decodeResource(getResources(), R.drawable.wzry);
        // 获取原图的宽高
        int width = bitmapResource.getWidth();
        int height = bitmapResource.getHeight();

        int singleWidth = width / 3;
        int singleHeight = height / 3;

        // 切割Bitmap
        for (int i = 0; i < 9; i++) {
            /**
             *  0x0 0x1 0x2
             *  1x0 1x1 1x2
             *  2x0 2x1 2x2
             */

            Bitmap bitmap = Bitmap.createBitmap(bitmapResource, (i % 3) * singleWidth, (i / 3) *
                    singleHeight, singleWidth, singleHeight);

            bitmaps[i] = bitmap;

        }

    }

    // 初始化控件
    private void initView() {
        mGroup = (GridLayout) findViewById(R.id.group_pic);
        // 获取子View的个数
        int childCount = mGroup.getChildCount();
        // 遍历子View，为所有的子View添加点击监听
        for (int i = 0; i < childCount; i++) {
            mGroup.getChildAt(i).setOnClickListener(this);
        }

        mStart = (Button) findViewById(R.id.game_start);
        mStart.setOnClickListener(this);
        mGameTime = (TextView) findViewById(R.id.game_time);

    }

    // 随机初始化样式
    private void randomInitView() {
        int childCount = mGroup.getChildCount();

        blankIndex = 8;

        // 随机打乱
        for (int i = 0; i < 20; i++) {

            int rand1, rand2; // 随机数的范围 0 - 7

            // rand1 0 - 7  生成第一个
            rand1 = (int) (Math.random() * 8);

            do {
                // 生成第二个随机数
                rand2 = (int) (Math.random() * 8);
                // 如果两个随机数不同，那么循环结束
                if (rand1 != rand2) {
                    break;
                }
                // 如果相同，继续重新生成rand2
            } while (true);

            // 交换索引值
            swap(rand1, rand2);

        }


        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) mGroup.getChildAt(i);
            // 设置填充
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

//            imageView.setImageResource(pics[picIndex[i]]);
            imageView.setImageBitmap(bitmaps[picIndex[i]]);

            imageView.setVisibility(View.VISIBLE);

            // 设置所有child为不可点击状态
            imageView.setClickable(false);

            if (i == childCount - 1) {
                imageView.setVisibility(View.INVISIBLE);
            }
        }
    }

    // 交换
    private void swap(int rand1, int rand2) {
//        int temp = picIndex[rand1];
//        picIndex[rand1] = picIndex[rand2];
//        picIndex[rand2] = temp;
        /**
         *  不引入第三变量 实现 两个数交换
         *
         *  picIndex[rand1] 值为3    二进制     11
         *  picIndex[rand2] 值为2    二进制     10
         */
        picIndex[rand1] = picIndex[rand1] ^ picIndex[rand2];
        // 异或   01

        picIndex[rand2] = picIndex[rand1] ^ picIndex[rand2];
        // 异或   11

        picIndex[rand1] = picIndex[rand1] ^ picIndex[rand2];
        // 异或  10


    }

    /**
     * alt + shift + ↑ 向上移动代码
     * alt + shift + ↓ 向下移动代码
     */

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.game_start:
                if (!isGaming) {
                    isGaming = true;
                    mStart.setText("重新开始");
                    gameStart();
                } else {
                    // 重置游戏
                    time = 0;
                    mHandler.removeMessages(UPDATE_TIME);

                    randomInitView();

                    gameStart();
                }

                break;
            default:
                // 获取点击的位置的索引 position
                int indexOfChild = mGroup.indexOfChild(v);
//        Toast.makeText(this, "position->"+ indexOfChild, Toast.LENGTH_SHORT).show();
                move(indexOfChild);
        }


    }

    private void gameStart() {
        int childCount = mGroup.getChildCount();
        // 把child 点击功能打开
        for (int i = 0; i < childCount; i++) {
            mGroup.getChildAt(i).setClickable(true);
        }
        // 开始计时
        mHandler.sendEmptyMessageDelayed(UPDATE_TIME, 1000);
    }

    private void move(int clickIndex) {
        // 点击的y坐标
        int clickY = clickIndex / 3;
        // 点击的x坐标
        int clickX = clickIndex % 3;

        // 空着的 y 坐标
        int blankY = blankIndex / 3;
        // 空着的 x 坐标
        int blankX = blankIndex % 3;

        // 算出 x 和 y的 差值
        int x = Math.abs(clickX - blankX);
        int y = Math.abs(clickY - blankY);

        // 书写可移动规则
        if ((x == 0 && y == 1) || (x == 1 && y == 0)) {
//            Toast.makeText(this, "可移动", Toast.LENGTH_SHORT).show();
            // 隐藏当前点击的位置
            mGroup.getChildAt(clickIndex).setVisibility(View.INVISIBLE);

            // 显示原来的空白位置
            ImageView imageView = (ImageView) mGroup.getChildAt(blankIndex);
            imageView.setVisibility(View.VISIBLE);
            // 设置点击的图片资源
//            imageView.setImageResource(pics[picIndex[clickIndex]]);
            imageView.setImageBitmap(bitmaps[picIndex[clickIndex]]);

            swap(clickIndex, blankIndex);
            // 记录空白位置的索引
            blankIndex = clickIndex;

        } else {
//            Toast.makeText(this, "不可移动", Toast.LENGTH_SHORT).show();
        }

        gameOver();
    }

    private void gameOver() {
        // 判定游戏结束了
        int length = picIndex.length;

        boolean isOver = true;

        for (int i = 0; i < length; i++) {
            if (i != picIndex[i]) {
                // 游戏还没结束
                isOver = false;
            }
        }

        if (isOver) {
            // 游戏结束
            int childCount = mGroup.getChildCount();
            // 还原最后一张图片
//            ((ImageView) mGroup.getChildAt(childCount - 1)).setImageResource
// (pics[picIndex[childCount - 1]]);
            ((ImageView) mGroup.getChildAt(childCount - 1)).setImageBitmap
                    (bitmaps[picIndex[childCount - 1]]);

            for (int i = 0; i < childCount; i++) {

                View view = mGroup.getChildAt(i);
                view.setVisibility(View.VISIBLE);
                view.setClickable(false);

            }

            isGaming = false;

//            Toast.makeText(this, "你真是个小天才", Toast.LENGTH_SHORT).show();

            // 停止计时
            mHandler.removeMessages(UPDATE_TIME);

            AlertDialog.Builder builder = new AlertDialog.Builder(this).setMessage("你真是个小天才！")
                    .setTitle("恭喜过关")
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 确认的操作
                            Toast.makeText(MainActivity.this, "恭喜确认", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //
                            Toast.makeText(MainActivity.this, "还可以继续努力", Toast.LENGTH_SHORT).show();
                        }
                    });

            builder.create().show();

        }

    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case UPDATE_TIME:
                // 设置时间
                time++;
                mGameTime.setText(String.valueOf(time));

                // 继续发送计时标记
                mHandler.sendEmptyMessageDelayed(UPDATE_TIME, 1000);
                break;
        }
        return false;
    }
}

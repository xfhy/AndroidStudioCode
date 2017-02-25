package com.xfhy.basicanimation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

/**
 * 2016年10月31日16:35:30
 * Android基本动画
 */
public class MainActivity extends AppCompatActivity {

    private ImageView my_view = null;
    private Button btn_alpha = null;
    private Button btn_scale = null;
    private Button btn_translate = null;
    private Button btn_rotate = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        my_view = (ImageView)findViewById(R.id.my_view);
        btn_alpha = (Button)findViewById(R.id.btn_alpha);
        btn_scale = (Button)findViewById(R.id.btn_scale);
        btn_translate = (Button)findViewById(R.id.btn_translate);
        btn_rotate = (Button)findViewById(R.id.btn_rotate);

        btn_alpha.setOnClickListener(new OnClickListenerlmpl());
        btn_scale.setOnClickListener(new OnClickListenerlmpl());
        btn_translate.setOnClickListener(new OnClickListenerlmpl());
        btn_rotate.setOnClickListener(new OnClickListenerlmpl());
    }

    private class OnClickListenerlmpl implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_alpha:
                    alphaTest();
                    break;
                case R.id.btn_scale:
                    scaleTest();
                    break;
                case R.id.btn_translate:
                    translateTest();
                    break;
                case R.id.btn_rotate:
                    rotateTest();
                    break;
            }
        }
    }

    //渐变操作
    private void alphaTest(){
        AnimationSet set = new AnimationSet(true);    //定义一个动画集

                /*-----------------渐变操作(alpha)----------------------------*/
        //传递alpha的开始值和结束值创建AlphaAnimation类对象
        AlphaAnimation alpha = new AlphaAnimation(1,0);  //完全显示到完全透明
        alpha.setDuration(2000);   //3秒完成动画        设置动画的持续时间,单位是毫秒
        set.addAnimation(alpha);   //增加动画到动画集合里面

        //启动动画(开始播放动画集合里面的动画)
        MainActivity.this.my_view.startAnimation(set);
        //my_view.setVisibility(View.GONE);    //播放完动画即刻消失
    }

    //伸缩操作
    private void scaleTest(){
        AnimationSet set = new AnimationSet(true);    //定义一个动画集
         /*-------------------伸缩操作(scale)---------------------------------*/
        ScaleAnimation scale = new ScaleAnimation(
                1,0.0f,    //开始点X,结束点X    X轴从满屏缩小到无
                1,0.0f,    //开始点Y,结束点Y    Y轴从满屏缩小到无
                Animation.RELATIVE_TO_SELF,0.5f,   //以自身0.5宽度为轴缩放
                Animation.RELATIVE_TO_SELF,0.5f    //以自身0.5高度为轴缩放
        );
        scale.setDuration(2000);
        set.addAnimation(scale);
        //启动动画(开始播放动画集合里面的动画)
        MainActivity.this.my_view.startAnimation(set);
    }

    //平移操作
    private void translateTest(){
        AnimationSet set = new AnimationSet(true);   //定义一个动画集
        TranslateAnimation tran = new TranslateAnimation(
                //移动方式是以自身为轴进行移动
                Animation.RELATIVE_TO_SELF,0.0f,  //X轴开始位置
                Animation.RELATIVE_TO_SELF,1.5f,  //X轴结束位置
                Animation.RELATIVE_TO_SELF,0.0f,  //Y轴开始位置
                Animation.RELATIVE_TO_SELF,1.5f   //Y轴结束位置
        );
        tran.setDuration(2000);  //2秒完成动画
        set.addAnimation(tran);  //添加动画到动画集
        MainActivity.this.my_view.startAnimation(set);   //开始动画
    }

    //旋转操作
    private void rotateTest(){
        AnimationSet set = new AnimationSet(true);
        RotateAnimation rotate = new RotateAnimation(
                0,360,  //旋转角度
                Animation.RELATIVE_TO_PARENT,0.2f,  //X轴位置为1/5个屏幕宽度
                Animation.RELATIVE_TO_PARENT,0.0f); //Y轴从原点计算
        rotate.setDuration(2000);
        set.addAnimation(rotate);
        MainActivity.this.my_view.startAnimation(set);
    }

}

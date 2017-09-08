package com.xfhy.x2048;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private long mExitTime;                   //退出时间
    private Button original = null;           //经典版
    private Button lovers = null;             //情侣版
    private Button reverse = null;            //逆向版
    private Button bereavement = null;        //丧病版
    private Button dynasty = null;            //朝代版
    private Button themeetingpoint = null;    //甲乙版
    private Button about_author = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取相应的控件
        original = (Button) findViewById(R.id.original);
        lovers = (Button) findViewById(R.id.lovers);
        reverse = (Button) findViewById(R.id.reverse);
        bereavement = (Button) findViewById(R.id.bereavement);
        dynasty = (Button) findViewById(R.id.dynasty);
        themeetingpoint = (Button) findViewById(R.id.themeetingpoint);
        about_author = (Button) findViewById(R.id.about_author);

        original.setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            // TODO Auto-generated method stub
                                            System.out.println("经典版");
                                            Config.LINES = 4;
                                            Config.type = 0;
                                            //生成一个intent对象
                                            Intent intent = new Intent();
                                            //前一个到后一个
                                            intent.setClass(MainActivity.this, SecondActivity
                                                    .class);
                                            MainActivity.this.startActivity(intent);
                                        }
                                    }
        );

        lovers.setOnClickListener(new OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          // TODO Auto-generated method stub
                                          System.out.println("情侣版");
                                          Config.LINES = 4;
                                          Config.type = 1;
                                          //生成一个intent对象
                                          Intent intent = new Intent();
                                          //前一个到后一个
                                          intent.setClass(MainActivity.this, SecondActivity.class);
                                          MainActivity.this.startActivity(intent);
                                      }
                                  }
        );

        reverse.setOnClickListener(new OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           // TODO Auto-generated method stub
                                           System.out.println("逆向版");
                                           Config.LINES = 4;
                                           Config.type = 2;
                                           //生成一个intent对象
                                           Intent intent = new Intent();
                                           //前一个到后一个
                                           intent.setClass(MainActivity.this, SecondActivity.class);
                                           MainActivity.this.startActivity(intent);
                                       }
                                   }
        );

        bereavement.setOnClickListener(new OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               // TODO Auto-generated method stub
                                               System.out.println("丧病版");
                                               Config.LINES = 8;
                                               Config.type = 3;
                                               //生成一个intent对象
                                               Intent intent = new Intent();
                                               //前一个到后一个
                                               intent.setClass(MainActivity.this, SecondActivity
													   .class);
                                               MainActivity.this.startActivity(intent);
                                           }
                                       }
        );

        dynasty.setOnClickListener(new OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           // TODO Auto-generated method stub
                                           System.out.println("朝代版");
                                           Config.LINES = 4;
                                           Config.type = 4;
                                           //生成一个intent对象
                                           Intent intent = new Intent();
                                           //前一个到后一个
                                           intent.setClass(MainActivity.this, SecondActivity.class);
                                           MainActivity.this.startActivity(intent);
                                       }
                                   }
        );

        themeetingpoint.setOnClickListener(new OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   // TODO Auto-generated method stub
                                                   System.out.println("甲乙版");
                                                   Config.LINES = 4;
                                                   Config.type = 5;
                                                   //生成一个intent对象
                                                   Intent intent = new Intent();
                                                   //前一个到后一个
                                                   intent.setClass(MainActivity.this, SecondActivity.class);
                                                   MainActivity.this.startActivity(intent);
                                               }
                                           }
        );

        about_author.setOnClickListener(new OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                // TODO Auto-generated method stub
                                                System.out.println("关于作者");
                                                //生成一个intent对象
                                                Intent intent = new Intent();
                                                //前一个到后一个
                                                intent.setClass(MainActivity.this, About.class);
                                                MainActivity.this.startActivity(intent);
                                            }
                                        }
        );
    }

    //按2次返回键退出
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //计算2次按下返回键之间的时间间隔,间隔<2000ms则退出
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();

            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
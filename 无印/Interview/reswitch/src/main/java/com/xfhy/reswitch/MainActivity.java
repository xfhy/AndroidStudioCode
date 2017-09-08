package com.xfhy.reswitch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.xfhy.reswitch.view.MDSwitch;

public class MainActivity extends AppCompatActivity {

    private MDSwitch mSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mSwitch = (MDSwitch) this.findViewById(R.id.switch_demo);
        mSwitch.setOnStateChangedListener(new MDSwitch.OnStateChangedListener() {
            @Override
            public void onStateChanged(boolean state) {
                if (true == state) {
                    Toast.makeText(MainActivity.this, "开关已打开", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "开关已关闭", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}

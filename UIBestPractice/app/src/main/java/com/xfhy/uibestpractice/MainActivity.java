package com.xfhy.uibestpractice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xfhy.uibestpractice.adapter.MsgAdapter;
import com.xfhy.uibestpractice.bean.Msg;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Msg> msgList = new ArrayList<>();
    private Button bt_send;
    private EditText et_input_text;
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_input_text = (EditText) findViewById(R.id.et_input_text);
        bt_send = (Button)findViewById(R.id.bt_send);
        msgRecyclerView = (RecyclerView)findViewById(R.id.rv_msg);

        initMsgs();   //1. 初始化消息数据

        //2. 创建LinearLayoutManager   并设置
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);

        //3. 初始化adapter   并设置
        adapter = new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);

        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String inputText = et_input_text.getText().toString();
                if(!TextUtils.isEmpty(inputText)){
                    Msg msg = new Msg(inputText, Msg.TYPE_SENT);
                    msgList.add(msg);
                    //当有新消息时  刷新RecyclerView中的显示
                    adapter.notifyItemInserted(msgList.size()-1);   //这个的效果比下面那个效果好(有动画)
//                    adapter.notifyDataSetChanged();
                    //将RecyclerView定位到最后一行
                    msgRecyclerView.scrollToPosition(msgList.size()-1);
                    et_input_text.setText("");  //清空用户输入
                }

            }
        });

    }

    /**
     * 初始化消息数据
     */
    private void initMsgs() {
        Msg msg1 = new Msg("Hello guy.", Msg.TYPE_RECEIVED);
        msgList.add(msg1);
        Msg msg2 = new Msg("Hello. Who is that?", Msg.TYPE_SENT);
        msgList.add(msg2);
        Msg msg3 = new Msg("This is Tom. Nice talking to you. ", Msg.TYPE_RECEIVED);
        msgList.add(msg3);
        Msg msg4 = new Msg("Let's go to see a movie", Msg.TYPE_RECEIVED);
        msgList.add(msg4);
    }
}

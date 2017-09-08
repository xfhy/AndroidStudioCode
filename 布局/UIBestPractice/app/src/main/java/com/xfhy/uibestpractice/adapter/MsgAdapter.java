package com.xfhy.uibestpractice.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xfhy.uibestpractice.R;
import com.xfhy.uibestpractice.bean.Msg;

import java.util.List;

/**
 * Created by xfhy on 2017/2/22.
 * RecyclerView适配器
 */

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder>{

    private List<Msg> mMsgList;

    static class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout leftLayout;
        LinearLayout rightLayout;
        TextView leftMsg;
        TextView rightMsg;

        public ViewHolder(View itemView) {
            super(itemView);
            leftLayout = (LinearLayout)itemView.findViewById(R.id.ll_left_layout);
            rightLayout = (LinearLayout)itemView.findViewById(R.id.ll_right_layout);
            leftMsg = (TextView)itemView.findViewById(R.id.tv_left_msg);
            rightMsg = (TextView)itemView.findViewById(R.id.tv_right_msg);
        }
    }

    //构造方法
    public MsgAdapter(List<Msg> msgList){
        mMsgList = msgList;
    }

    //创建缓存对象
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //加载布局
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_msg_layout, parent, false);
        return new ViewHolder(view);
    }

    //加载数据
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Msg msg = mMsgList.get(position);

        //判断是接收到的消息   还是   发送的消息
        if(msg.getType() == Msg.TYPE_RECEIVED){
            //如果是接收到的消息    则左边的布局可见    右边不可见
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            holder.leftMsg.setText(msg.getContent());
        } else if(msg.getType() == Msg.TYPE_SENT){
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightMsg.setText(msg.getContent());
        }
    }

    //子项的数目
    @Override
    public int getItemCount() {
        return mMsgList.size();
    }
}

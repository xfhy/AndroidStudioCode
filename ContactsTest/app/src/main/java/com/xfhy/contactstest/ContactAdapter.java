package com.xfhy.contactstest;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xfhy.contactstest.bean.MContact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xfhy on 2017/2/25.
 * RecyclerView的适配器
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder>{

    //全部的子项数据
    List<MContact> contactList;
    private Context mContext;

    //缓存
    class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout ll_contact;
        TextView tv_name;
        TextView tv_phone;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_phone = (TextView) itemView.findViewById(R.id.tv_phone);
            ll_contact = (LinearLayout)itemView.findViewById(R.id.ll_contact);
        }
    }

    //构造方法  将数据传入
    public ContactAdapter(List<MContact> contactList,Context mContext) {
        this.contactList = contactList;
        this.mContext = mContext;
    }

    //加载布局   创建ViewHolder
    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact, parent, false);
        return new ViewHolder(view);
    }

    //绑定数据到子项上
    @Override
    public void onBindViewHolder(final ContactAdapter.ViewHolder holder, int position) {
        final MContact contact = contactList.get(position);
        holder.tv_name.setText(contact.getName());
        holder.tv_phone.setText(contact.getPhone());

        //点击子项的LinearLayout时播打这个人的电话
        holder.ll_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+contact.getPhone()));
                try{
                    mContext.startActivity(intent);
                } catch (SecurityException e){
                    e.printStackTrace();
                }
            }
        });
    }

    //返回子项的行数
    @Override
    public int getItemCount() {
        return contactList.size();
    }
}

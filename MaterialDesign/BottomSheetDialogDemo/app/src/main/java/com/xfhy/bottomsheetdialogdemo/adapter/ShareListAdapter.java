package com.xfhy.bottomsheetdialogdemo.adapter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xfhy.bottomsheetdialogdemo.R;
import com.xfhy.bottomsheetdialogdemo.entity.AppInfoEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * author feiyang
 * create at 2017/10/9 17:57
 * description：
 */
public class ShareListAdapter extends RecyclerView.Adapter<ShareListAdapter.ViewHolder> {

    private List<AppInfoEntity> appInfoEntities;
    private Context mContext;
    private final LayoutInflater mLayoutInflater;

    public ShareListAdapter(List<AppInfoEntity> appInfoEntities, Context context) {
        this.mContext = context;
        this.appInfoEntities = appInfoEntities;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView appIcon;
        TextView appName;

        public ViewHolder(View itemView) {
            super(itemView);
            appIcon = itemView.findViewById(R.id.iv_share_app_icon);
            appName = itemView.findViewById(R.id.tv_share_app_name);
        }
    }

    @Override
    public ShareListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_share, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShareListAdapter.ViewHolder holder, int position) {
        final AppInfoEntity appInfoEntity = appInfoEntities.get(position);
        holder.appIcon.setImageDrawable(appInfoEntity.getAppIcon());
        holder.appName.setText(appInfoEntity.getAppName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //分享
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setComponent(new ComponentName(appInfoEntity.getPkgName(), appInfoEntity
                        .getLaunchClassName()));
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "https://github.com/xfhy");
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return appInfoEntities == null ? 0 : appInfoEntities.size();
    }
}

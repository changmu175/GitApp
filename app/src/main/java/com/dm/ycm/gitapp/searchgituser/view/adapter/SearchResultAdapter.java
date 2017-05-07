package com.dm.ycm.gitapp.searchgituser.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dm.ycm.gitapp.R;
import com.dm.ycm.gitapp.searchgituser.data.bean.GitUser;

import java.util.List;

/**
 * Created by ycm on 2017/5/7.
 * Description:搜索结果适配器
 */

public class SearchResultAdapter extends BaseAdapter {
    private List<GitUser> dataSource;
    private Context context;

    public SearchResultAdapter(Context context) {
        this.context = context;
    }

    public void setDataSource(List<GitUser> dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int getCount() {
        return dataSource == null ? 0 : dataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return dataSource == null ? null : dataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return dataSource == null ? 0 : position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HolderView holderView;
        if (convertView == null) {
            convertView = LinearLayout.inflate(context, R.layout.user_item, null);
            holderView = new HolderView(convertView);
            convertView.setTag(holderView);
        } else {
            holderView = (HolderView) convertView.getTag();
        }
        loadImage(holderView.avatar_iv, dataSource.get(position).getAvatarUrl());
        holderView.name_tv.setText(dataSource.get(position).getUserName());
        holderView.language_tv.setText(dataSource.get(position).getLanguages());
        return convertView;
    }

    public void clear() {
        if (dataSource != null && !dataSource.isEmpty()) {
            dataSource.clear();
            notifyDataSetChanged();
        }
    }

    private class HolderView {
        ImageView avatar_iv;
        TextView name_tv;
        TextView language_tv;
        HolderView(View view) {
            if (view != null) {
                avatar_iv = (ImageView) view.findViewById(R.id.avatar_iv);
                name_tv = (TextView) view.findViewById(R.id.name_tv);
                language_tv = (TextView) view.findViewById(R.id.language_tv);
            }
        }
    }

    private void loadImage(ImageView target, String avatarUrl) {
        Glide.with(context)
                .load(avatarUrl)
                .asBitmap()
                .placeholder(R.drawable.default_avatar)
                .error(R.drawable.default_avatar)
                .dontAnimate()
                .into(target);
    }
}

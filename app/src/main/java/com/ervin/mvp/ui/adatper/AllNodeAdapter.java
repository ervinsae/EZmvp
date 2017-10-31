package com.ervin.mvp.ui.adatper;

import android.ervin.mvp.R;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ervin.mvp.model.Actors;
import com.ervin.mvp.ui.widget.CircleImageView;
import com.veinhorn.tagview.TagView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ervin on 2017/10/31.
 */

public class AllNodeAdapter extends RecyclerView.Adapter<AllNodeAdapter.ViewHolder> {

    private List<Actors> data;

    public void setData(List<Actors> data){
        this.data = data;
    }
    @Override
    public AllNodeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_actor_items, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AllNodeAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_avatar)
        CircleImageView ivAvatar;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tagView)
        TagView tagView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

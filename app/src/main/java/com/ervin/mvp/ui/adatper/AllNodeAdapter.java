package com.ervin.mvp.ui.adatper;

import android.content.Context;
import android.ervin.mvp.R;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ervin.mvp.model.Actors;
import com.ervin.mvp.ui.widget.CircleImageView;
import com.ervin.mvp.utils.DateUtil;
import com.veinhorn.tagview.TagView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ervin on 2017/10/31.
 */

public class AllNodeAdapter extends RecyclerView.Adapter<AllNodeAdapter.ViewHolder> {

    private List<Actors> data;
    private Context context;

    private OnItemClickedListener mListener;

    public AllNodeAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Actors> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public List<Actors>  getData(){
        return data;
    }
    public void setOnItemClickListener(OnItemClickedListener listener){
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_actor_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Actors actors = data.get(position);
        Glide.with(context).load("http:" + actors.member.avatar_normal).into(holder.ivAvatar);

        holder.tvName.setText(actors.member.username);
        holder.tvTime.setText(actors.created == 0 ? actors.time : DateUtil.formatTime2String(actors.created));
        holder.tvReply.setText(context.getString(R.string.replies,actors.replies));
        holder.tvContent.setText(actors.title);
        holder.tagView.setText(actors.node.title);

        holder.tagView.setOnClickListener(v -> {
            if(mListener != null){
                mListener.onClick(v,position);
            }
        });

        holder.itemView.setOnClickListener(v -> {
            if(mListener != null){
                mListener.onClick(v,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_avatar)
        CircleImageView ivAvatar;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tagView)
        TagView tagView;
        @BindView(R.id.tv_reply)
        TextView tvReply;
        @BindView(R.id.tv_content)
        TextView tvContent;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

package com.ervin.mvp.ui.adatper;

import android.content.Context;
import android.ervin.mvp.R;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ervin.mvp.model.Actors;
import com.ervin.mvp.model.Reply;
import com.ervin.mvp.ui.widget.CircleImageView;
import com.ervin.mvp.utils.DateUtil;
import com.veinhorn.tagview.TagView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ervin on 2017/11/3.
 */

public class TopicRepliesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_NORMAL = 1;
    private static final int ITEM_TOP = 2;
    List<Object> data;
    private Context context;

    public TopicRepliesAdapter(Context context){
        this.context = context;
    }
    public void setData(List<Object> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == ITEM_TOP) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_topic_reply_top_item, parent, false);
            return new ViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_topic_reply_item, parent, false);
            return new ViewHolder2(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Object o = data.get(position);

        if (o instanceof Actors) {
            ViewHolder viewHolder = (ViewHolder)holder;
            Actors actor = (Actors)o;
            Glide.with(context).load("http:" + actor.member.avatar_normal).into(viewHolder.ivAvatar);
            viewHolder.tvName.setText(actor.member.username);
            viewHolder.tvTime.setText(DateUtil.formatTime2String(actor.created));
            viewHolder.tvContent.setText(actor.title);
            viewHolder.tagView.setText(actor.node.title);
            viewHolder.tvReply.setText(context.getString(R.string.replies, actor.replies));

            viewHolder.tvContentInfo.setText(Html.fromHtml(actor.content_rendered));
        }else if(o instanceof Reply){
            ViewHolder2 viewHolder2 = (ViewHolder2) holder;
            Reply reply = (Reply)o;
            Glide.with(context).load("http:" + reply.member.avatar_normal).into(viewHolder2.ivAvatar);
            viewHolder2.tvName.setText(reply.member.username);
            viewHolder2.tvTime.setText(DateUtil.formatTime2String(reply.created));
            viewHolder2.tvContent.setText(reply.content);
            viewHolder2.tvPosition.setText(context.getString(R.string.shafa,position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM_TOP;
        } else {
            return ITEM_NORMAL;
        }
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
        @BindView(R.id.tv_reply)
        TextView tvReply;
        @BindView(R.id.tagView)
        TagView tagView;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_content_info)
        TextView tvContentInfo;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolder2 extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_avatar)
        CircleImageView ivAvatar;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_position)
        TextView tvPosition;

        ViewHolder2(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

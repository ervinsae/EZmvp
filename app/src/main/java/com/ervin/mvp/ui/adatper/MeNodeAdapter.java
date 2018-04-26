package com.ervin.mvp.ui.adatper;

import android.ervin.mvp.R;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ervin.mvp.model.Actors;

import java.util.List;

public class MeNodeAdapter extends BaseQuickAdapter<Actors,BaseViewHolder> {
    public MeNodeAdapter(@Nullable List<Actors> data) {
        super(R.layout.adapter_me_node_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Actors item) {

        helper.setText(R.id.tv_title,item.title)
                .setText(R.id.tv_tag,item.node.title)
                .setText(R.id.tv_user,item.member.username)
                .setText(R.id.tv_time,item.time)
                .setText(R.id.tagView,item.replies + "次")
                .addOnClickListener(R.id.rl_card);
                //.setText(R.id.tv_reply,mContext.getString(R.string.last_reply,item.))

    }
}

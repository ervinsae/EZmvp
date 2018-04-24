package com.ervin.mvp.ui.adatper;

import android.ervin.mvp.R;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ervin.mvp.model.Node;

import java.util.List;

public class MeNodeAdapter extends BaseQuickAdapter<Node,BaseViewHolder> {
    public MeNodeAdapter(@Nullable List<Node> data) {
        super(R.layout.adapter_actor_items, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Node item) {


    }
}

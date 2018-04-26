package com.ervin.mvp.ui.activity;

import android.content.Intent;
import android.ervin.mvp.R;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.ervin.mvp.model.Actors;
import com.ervin.mvp.presenter.MeNodePresenter;
import com.ervin.mvp.ui.adatper.MeNodeAdapter;
import com.ervin.mvp.ui.iview.IMeNodeView;
import com.ervin.mvp.ui.widget.RecycleViewDivider;
import com.ervin.mvp.ui.widget.TitleBar;
import com.ervin.mvp.utils.DensityHelper;

import java.util.List;

import butterknife.BindView;

public class MeNodeActivity extends BaseActivity<MeNodePresenter> implements IMeNodeView {
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.rv_data)
    RecyclerView rvData;

    MeNodeAdapter mAdapter;
    @Override
    protected void initPresenter() {
        presenter = new MeNodePresenter(this, this);
        presenter.attachView();
    }

    @Override
    protected int setLayoutRsID() {
        return R.layout.activity_me_node;
    }

    @Override
    public void initView() {
        titleBar.setTitle("创建的主题");
        titleBar.setLeftImageResource(R.mipmap.ic_back);
        titleBar.setLeftClickListener(l -> onBackPressed());

        presenter.getMeNodeInfo("Ervin");

        mAdapter = new MeNodeAdapter(null);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        RecycleViewDivider divider = new RecycleViewDivider(this, LinearLayoutManager.VERTICAL,
                DensityHelper.dip2px(this, 15),
                ContextCompat.getColor(this, R.color.transparent), false);
        rvData.addItemDecoration(divider);
        rvData.setLayoutManager(manager);
        rvData.setAdapter(mAdapter);

        rvData.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(MeNodeActivity.this, TopicInfoActivity.class);
                intent.putExtra("topic", mAdapter.getData().get(position));
                startActivity(intent);
            }
        });

    }

    @Override
    public void showData(List<Actors> data) {
        mAdapter.setNewData(data);
    }
}

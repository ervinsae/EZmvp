package com.ervin.mvp.ui.activity;

import android.content.Intent;
import android.ervin.mvp.R;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ervin.mvp.model.Actors;
import com.ervin.mvp.presenter.AllNodePresenter;
import com.ervin.mvp.ui.adatper.AllNodeAdapter;
import com.ervin.mvp.ui.iview.IAllNodeView;
import com.ervin.mvp.ui.widget.RecycleViewDivider;
import com.ervin.mvp.ui.widget.TitleBar;
import com.ervin.mvp.utils.DensityHelper;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Ervin on 2017/11/20.
 */

public class TagNodeActivity extends BaseActivity<AllNodePresenter> implements IAllNodeView,SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.rv_data)
    RecyclerView rvData;
    @BindView(R.id.sr_refresh)
    SwipeRefreshLayout refreshLayout;

    AllNodeAdapter mAdapter;
    String title;
    String type;
    @Override
    public void initView() {
        type = getIntent().getStringExtra("name");
        title = getIntent().getStringExtra("title");
        titleBar.setTitle(title);
        titleBar.setLeftImageResource(R.mipmap.ic_back);
        titleBar.setLeftClickListener(l -> onBackPressed());

        mAdapter = new AllNodeAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecycleViewDivider divider = new RecycleViewDivider(this, LinearLayoutManager.VERTICAL,
                DensityHelper.dip2px(this, 15),
                ContextCompat.getColor(this, R.color.transparent), false);
        rvData.addItemDecoration(divider);
        rvData.setLayoutManager(manager);
        rvData.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((view, position) -> {
            Intent intent = new Intent(this, TopicInfoActivity.class);
            intent.putExtra("topic", mAdapter.getData().get(position));
            startActivity(intent);
        });

        refreshLayout.setOnRefreshListener(this);
        refreshLayout.postDelayed(() -> onRefresh(), 200);
    }

    @Override
    public void showData(List<Actors> data) {
        refreshLayout.setRefreshing(false);
        mAdapter.setData(data);
    }

    @Override
    protected void initPresenter() {
        presenter = new AllNodePresenter(this,this);
        presenter.attachView();
    }

    @Override
    protected int setLayoutRsID() {
        return R.layout.activity_tag_node;
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        presenter.getDataByTopicName(type);
    }
}

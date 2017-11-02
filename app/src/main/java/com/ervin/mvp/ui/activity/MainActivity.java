package com.ervin.mvp.ui.activity;


import android.content.Intent;
import android.ervin.mvp.R;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.ervin.mvp.model.Actors;
import com.ervin.mvp.presenter.MainPresenter;
import com.ervin.mvp.ui.adatper.AllNodeAdapter;
import com.ervin.mvp.ui.iview.IMainView;
import com.ervin.mvp.ui.widget.RecycleViewDivider;
import com.ervin.mvp.ui.widget.TitleBar;
import com.ervin.mvp.utils.DensityHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity<MainPresenter> implements IMainView,SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.rv_data)
    RecyclerView rvData;
    @BindView(R.id.sr_refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.title_bar)
    TitleBar titleBar;

    AllNodeAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Log.d("main", "------------");

        initPresenter();
    }

    @Override
    protected void initPresenter() {
        //在这里实现了P和V的关联
        presenter = new MainPresenter(this, this);
        presenter.attachView();//调用了initView（）;
    }

    @Override
    public void initView() {
        //做一些初始化view的操作
        titleBar.setLeftVisible(false);
        titleBar.setTitleColor(R.color.colorPrimaryDark);
        titleBar.setTitle("全部");

        mAdapter = new AllNodeAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        RecycleViewDivider divider = new RecycleViewDivider(this, LinearLayoutManager.VERTICAL,
                DensityHelper.dip2px(this,15),
                ContextCompat.getColor(this,R.color.transparent),false);
        rvData.addItemDecoration(divider);
        rvData.setLayoutManager(manager);
        rvData.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((view, position) -> {
            Intent intent = new Intent(this,TopicInfoActivity.class);
            intent.putExtra("topic",mAdapter.getData().get(position));
            startActivity(intent);
        });

        refreshLayout.setOnRefreshListener(this);
        refreshLayout.postDelayed(() -> onRefresh(),200);
    }

    @Override
    public void showData(List<Actors> data) {
        refreshLayout.setRefreshing(false);
        mAdapter.setData(data);
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        presenter.getData();
    }
}

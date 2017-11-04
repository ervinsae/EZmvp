package com.ervin.mvp.ui.activity;


import android.content.Intent;
import android.ervin.mvp.R;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;

import com.ervin.mvp.model.Actors;
import com.ervin.mvp.presenter.MainPresenter;
import com.ervin.mvp.ui.adatper.AllNodeAdapter;
import com.ervin.mvp.ui.iview.IMainView;
import com.ervin.mvp.ui.widget.RecycleViewDivider;
import com.ervin.mvp.ui.widget.TitleBar;
import com.ervin.mvp.utils.DensityHelper;

import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainPresenter> implements IMainView,SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.rv_data)
    RecyclerView rvData;
    @BindView(R.id.sr_refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.drawer)
    DrawerLayout drawerLayout;

    AllNodeAdapter mAdapter;

    @Override
    protected int setLayoutRsID() {
        return R.layout.activity_main;
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
        titleBar.setTitle("最新");
        //titleBar.setLeftImageResource(R.mipmap.icon_menu);
        titleBar.setLeftText("菜单");
        titleBar.setLeftClickListener(v -> {
            if(drawerLayout.isDrawerOpen(Gravity.LEFT)){
                drawerLayout.closeDrawer(Gravity.LEFT);
            }else{
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

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

package com.ervin.mvp.ui.activity;


import android.content.Intent;
import android.ervin.mvp.R;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;

import com.bumptech.glide.Glide;
import com.ervin.mvp.model.Actors;
import com.ervin.mvp.model.Member;
import com.ervin.mvp.presenter.MainPresenter;
import com.ervin.mvp.ui.adatper.AllNodeAdapter;
import com.ervin.mvp.ui.iview.IMainView;
import com.ervin.mvp.ui.widget.CircleImageView;
import com.ervin.mvp.ui.widget.RecycleViewDivider;
import com.ervin.mvp.ui.widget.TitleBar;
import com.ervin.mvp.utils.DensityHelper;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity<MainPresenter> implements IMainView, SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.rv_data)
    RecyclerView rvData;
    @BindView(R.id.sr_refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.drawer)
    DrawerLayout drawerLayout;

    CircleImageView ivAvatar;

    @BindView(R.id.nv_menu_left)
    NavigationView nvMenuLeft;
    AllNodeAdapter mAdapter;

    int node = 0;//最新
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
        titleBar.addAction(new TitleBar.Action() {
            @Override
            public String getText() {
                return "搜索";
            }

            @Override
            public int getDrawable() {
                return R.mipmap.ic_search;
            }

            @Override
            public void performAction(View view) {
                //todo 搜索逻辑
            }
        });
        titleBar.setLeftText("菜单");
        titleBar.setLeftClickListener(v -> {
            if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                drawerLayout.closeDrawer(Gravity.LEFT);
            } else {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        ivAvatar = nvMenuLeft.getHeaderView(0).findViewById(R.id.iv_avatar);
        ivAvatar.setOnClickListener(v -> {
            Intent intent = new Intent(this,MeNodeActivity.class);
            startActivity(intent);
        });

        mAdapter = new AllNodeAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecycleViewDivider divider = new RecycleViewDivider(this, LinearLayoutManager.VERTICAL,
                DensityHelper.dip2px(this, 15),
                ContextCompat.getColor(this, R.color.transparent), false);
        rvData.addItemDecoration(divider);
        rvData.setLayoutManager(manager);
        rvData.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((view, position) -> {
            if(view.getId() == R.id.tagView){
                //跳转到相应的Tag
                Intent intent = new Intent(this,TagNodeActivity.class);
                String name = mAdapter.getData().get(position).node.name;
                List<String> data = Arrays.asList(AllNodeActivity.type);
                if(data.contains(name)){
                    intent.putExtra("root",true);
                }else{
                    intent.putExtra("root",false);
                }
                intent.putExtra("name", name);
                intent.putExtra("title",mAdapter.getData().get(position).node.title);
                startActivity(intent);
            }else {
                Intent intent = new Intent(this, TopicInfoActivity.class);
                intent.putExtra("topic", mAdapter.getData().get(position));
                startActivity(intent);
            }
        });
        nvMenuLeft.setNavigationItemSelectedListener(item -> {
            drawerLayout.closeDrawer(GravityCompat.START);
            switch (item.getItemId()){
                case R.id.nav_all_node:
                    Intent intent = new Intent(this,AllNodeActivity.class);
                    startActivity(intent);
                    break;
                case R.id.nav_hot_node:
                    node = 1;
                    titleBar.setTitle("最热");
                    presenter.getNodeHotData();
                    break;
                case R.id.nav_new_node:
                    node = 0;
                    titleBar.setTitle("最新");
                    presenter.getNodeAllData();
                    break;
            }
            return true;
        });

        refreshLayout.setOnRefreshListener(this);
        refreshLayout.postDelayed(() -> onRefresh(), 200);
        presenter.getUserProfile();
    }


    @Override
    public void showData(List<Actors> data) {
        refreshLayout.setRefreshing(false);
        mAdapter.setData(data);
    }

    @Override
    public void showUserProfile(Member member) {
        if(member != null) {
            Glide.with(this).load("http:" + member.avatar_normal).into(ivAvatar);
        }
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        if(node == 0) {
            presenter.getNodeAllData();
        }else{
            presenter.getNodeHotData();
        }
    }
}

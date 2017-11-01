package com.ervin.mvp.ui.activity;


import android.ervin.mvp.R;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.ervin.mvp.model.Actors;
import com.ervin.mvp.presenter.MainPresenter;
import com.ervin.mvp.ui.adatper.AllNodeAdapter;
import com.ervin.mvp.ui.iview.IMainView;
import com.ervin.mvp.ui.widget.RecycleViewDivider;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity<MainPresenter> implements IMainView {


    @BindView(R.id.rv_data)
    RecyclerView rvData;

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
        mAdapter = new AllNodeAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        RecycleViewDivider divider = new RecycleViewDivider(this, LinearLayoutManager.VERTICAL);
        rvData.addItemDecoration(divider);
        rvData.setLayoutManager(manager);
        rvData.setAdapter(mAdapter);

        presenter.getData();

    }

    @Override
    public void showData(List<Actors> data) {
        mAdapter.setData(data);
    }
}

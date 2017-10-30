package com.ervin.mvp.ui.activity;


import android.ervin.mvp.R;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.ervin.mvp.model.Actors;
import com.ervin.mvp.presenter.MainPresenter;
import com.ervin.mvp.ui.iView.IMainView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity<MainPresenter> implements IMainView {


    @BindView(R.id.rv_data)
    RecyclerView rvData;

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
        presenter.getData();
    }

    @Override
    public void showData(List<Actors> data) {

    }
}

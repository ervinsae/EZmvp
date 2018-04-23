package com.ervin.mvp.ui.activity;

import android.ervin.mvp.R;
import android.support.v7.widget.RecyclerView;

import com.ervin.mvp.presenter.MeNodePresenter;
import com.ervin.mvp.ui.iview.IMeNodeView;
import com.ervin.mvp.ui.widget.TitleBar;

import butterknife.BindView;

public class MeNodeActivity extends BaseActivity<MeNodePresenter> implements IMeNodeView {
    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.rv_data)
    RecyclerView rvData;

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
    }

}

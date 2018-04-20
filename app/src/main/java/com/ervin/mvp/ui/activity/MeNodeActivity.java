package com.ervin.mvp.ui.activity;

import com.ervin.mvp.presenter.MeNodePresenter;
import com.ervin.mvp.ui.iview.IMeNodeView;

public class MeNodeActivity extends BaseActivity<MeNodePresenter> implements IMeNodeView{
    @Override
    protected void initPresenter() {
        presenter = new MeNodePresenter(this,this);
        presenter.attachView();
    }

    @Override
    protected int setLayoutRsID() {
        return 0;
    }

    @Override
    public void initView() {

    }
}

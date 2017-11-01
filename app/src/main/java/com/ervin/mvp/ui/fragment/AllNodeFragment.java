package com.ervin.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ervin.mvp.model.Actors;
import com.ervin.mvp.presenter.MainPresenter;
import com.ervin.mvp.ui.iview.IMainView;

import java.util.List;

/**
 * Created by Ervin on 2017/10/31.
 */

public class AllNodeFragment extends BaseFragment<MainPresenter> implements IMainView{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new MainPresenter(getActivity(),this);
        presenter.attachView();
    }

    @Override
    public void initView() {

    }

    @Override
    public void showData(List<Actors> data) {

    }
}

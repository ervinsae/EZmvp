package com.ervin.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ervin.mvp.presenter.BasePresenter;
import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Ervin on 2017/10/31.
 */

public abstract class BaseFragment<T extends BasePresenter> extends RxFragment {

    protected T presenter;
    Unbinder unbinder;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(setLayoutRes(),container,false);
        unbinder = ButterKnife.bind(this, view);
        initPresenter();
        return view;
    }

    protected abstract void initPresenter();
    public abstract int setLayoutRes();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if(presenter!=null){
            presenter.detachView();
            presenter = null;
        }
    }
}

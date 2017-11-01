package com.ervin.mvp.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ervin.mvp.presenter.BasePresenter;

/**
 * Created by Ervin on 2017/10/31.
 */

public class BaseFragment<T extends BasePresenter> extends Fragment{

    protected T presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if(presenter!=null){
            presenter.detachView();
            presenter = null;
        }
    }
}

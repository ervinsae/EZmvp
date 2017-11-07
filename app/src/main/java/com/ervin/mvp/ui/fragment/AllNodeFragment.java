package com.ervin.mvp.ui.fragment;

import android.ervin.mvp.R;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ervin.mvp.model.Actors;
import com.ervin.mvp.presenter.MainPresenter;
import com.ervin.mvp.ui.iview.IAllNodeView;

import java.util.List;

/**
 * Created by Ervin on 2017/10/31.
 */

public class AllNodeFragment extends BaseFragment<MainPresenter> implements IAllNodeView{

    String type;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new MainPresenter(getActivity(),this);
        presenter.attachView();
    }

    @Override
    public int setLayoutRes() {
        return R.layout.fragment_topic_info;
    }

    @Override
    public void initView() {
        type = getArguments().getString("type");
    }


    @Override
    public void getContent(String topic) {

    }

    @Override
    public void showData(List<Actors> data) {

    }
}

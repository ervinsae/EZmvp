package com.ervin.mvp.ui.fragment;

import android.content.Intent;
import android.ervin.mvp.R;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ervin.mvp.model.Actors;
import com.ervin.mvp.model.Member;
import com.ervin.mvp.presenter.MainPresenter;
import com.ervin.mvp.ui.activity.TopicInfoActivity;
import com.ervin.mvp.ui.adatper.AllNodeAdapter;
import com.ervin.mvp.ui.iview.IAllNodeView;
import com.ervin.mvp.ui.widget.RecycleViewDivider;
import com.ervin.mvp.utils.DensityHelper;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Ervin on 2017/10/31.
 */
//todo 不能公用presenter 会导致一些生命周期的问题
public class AllNodeFragment extends BaseFragment<MainPresenter> implements IAllNodeView,SwipeRefreshLayout.OnRefreshListener {

    String type;
    @BindView(R.id.rv_data)
    RecyclerView rvData;
    @BindView(R.id.sr_refresh)
    SwipeRefreshLayout refreshLayout;

    AllNodeAdapter mAdapter;

    @Override
    protected void initPresenter() {
        presenter = new MainPresenter(getActivity(), this);
        presenter.attachView();
    }

    @Override
    public int setLayoutRes() {
        return R.layout.fragment_topic_info;
    }

    @Override
    public void initView() {
        type = getArguments().getString("type");

        mAdapter = new AllNodeAdapter(getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        RecycleViewDivider divider = new RecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL,
                DensityHelper.dip2px(getActivity(), 15),
                ContextCompat.getColor(getActivity(), R.color.transparent), false);
        rvData.addItemDecoration(divider);
        rvData.setLayoutManager(manager);
        rvData.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((view, position) -> {
            Intent intent = new Intent(getActivity(), TopicInfoActivity.class);
            intent.putExtra("topic", mAdapter.getData().get(position));
            startActivity(intent);
        });

        refreshLayout.setOnRefreshListener(this);
        refreshLayout.postDelayed(() -> onRefresh(), 200);
    }


    @Override
    public void showData(List<Actors> data) {
        refreshLayout.setRefreshing(false);
        mAdapter.setData(data);
    }

    @Override
    public void showUserProfile(Member member) {

    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        presenter.getDataByTopicName(type);
    }
}

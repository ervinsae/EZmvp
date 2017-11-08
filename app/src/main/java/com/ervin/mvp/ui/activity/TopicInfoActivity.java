package com.ervin.mvp.ui.activity;

import android.ervin.mvp.R;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ervin.mvp.model.Actors;
import com.ervin.mvp.model.Reply;
import com.ervin.mvp.model.Topic;
import com.ervin.mvp.presenter.TopicInfoPresenter;
import com.ervin.mvp.ui.adatper.TopicRepliesAdapter;
import com.ervin.mvp.ui.iview.ITopicInfoView;
import com.ervin.mvp.ui.widget.RecycleViewDivider;
import com.ervin.mvp.ui.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Ervin on 2017/11/2.
 *
 * 包含话题详情，以及回复信息
 */

public class TopicInfoActivity extends BaseActivity<TopicInfoPresenter> implements ITopicInfoView , SwipeRefreshLayout.OnRefreshListener{


    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.rv_data)
    RecyclerView rvData;
    @BindView(R.id.sr_refresh)
    SwipeRefreshLayout refreshLayout;

    private Actors actor;
    private TopicRepliesAdapter mAdapter;

    @Override
    protected void initPresenter() {
        presenter = new TopicInfoPresenter(this, this);
        presenter.attachView();
    }

    @Override
    protected int setLayoutRsID() {
        return R.layout.activity_topic_info;
    }

    @Override
    public void initView() {

        /*titleBar.setImmersive(true);*/
        titleBar.setLeftImageResource(R.mipmap.ic_back);
        titleBar.setTitle("话题");
        titleBar.setLeftClickListener(v -> onBackPressed());
        //deprecated ,get from http
        actor = getIntent().getParcelableExtra("topic");

        //todo 如果actor中不存在某些信息，则要请求网络
        mAdapter = new TopicRepliesAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        RecycleViewDivider divider = new RecycleViewDivider(this,LinearLayoutManager.VERTICAL);
        rvData.addItemDecoration(divider);
        rvData.setLayoutManager(manager);
        rvData.setAdapter(mAdapter);

        refreshLayout.setOnRefreshListener(this);
        refreshLayout.postDelayed(() -> onRefresh(),200);
    }

    @Override
    public void showData(List<Reply> reply) {
       /* refreshLayout.setRefreshing(false);
        data.add(actor);
        data.addAll(reply);
        mAdapter.setData(data);*/
    }

    @Override
    public void showTopic(Topic topic) {
        List<Object> data = new ArrayList<>();
        refreshLayout.setRefreshing(false);
        data.add(topic.getActors());
        data.addAll(topic.getReplyList());
        mAdapter.setData(data);

    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        presenter.getReplyByID(actor.id);
    }
}

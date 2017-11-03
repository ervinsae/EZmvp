package com.ervin.mvp.ui.activity;

import android.ervin.mvp.R;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ervin.mvp.model.Actors;
import com.ervin.mvp.model.Reply;
import com.ervin.mvp.presenter.TopicInfoPresenter;
import com.ervin.mvp.ui.adatper.TopicRepliesAdapter;
import com.ervin.mvp.ui.iview.ITopicInfoView;
import com.ervin.mvp.ui.widget.RecycleViewDivider;
import com.ervin.mvp.ui.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Ervin on 2017/11/2.
 *
 * 包含话题详情，以及回复信息
 */

public class TopicInfoActivity extends BaseActivity<TopicInfoPresenter> implements ITopicInfoView {


    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.rv_data)
    RecyclerView rvData;

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

        titleBar.setLeftImageResource(R.mipmap.ic_back);
        titleBar.setTitle("话题");
        actor = getIntent().getParcelableExtra("topic");

        mAdapter = new TopicRepliesAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        RecycleViewDivider divider = new RecycleViewDivider(this,LinearLayoutManager.VERTICAL);
        rvData.addItemDecoration(divider);
        rvData.setLayoutManager(manager);
        rvData.setAdapter(mAdapter);

        presenter.getReplyByID(actor.id);

    }

    @OnClick(R.id.title_bar) void onBack(){
        onBackPressed();
    }

    @Override
    public void showData(List<Reply> reply) {
        List<Object> data = new ArrayList<>();
        data.add(actor);
        data.addAll(reply);

        mAdapter.setData(data);
    }
}

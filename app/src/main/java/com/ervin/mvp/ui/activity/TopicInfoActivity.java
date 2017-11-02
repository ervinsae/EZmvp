package com.ervin.mvp.ui.activity;

import android.ervin.mvp.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ervin.mvp.model.Actors;
import com.ervin.mvp.presenter.TopicInfoPresenter;
import com.ervin.mvp.ui.iview.ITopicInfoView;
import com.ervin.mvp.ui.widget.CircleImageView;
import com.ervin.mvp.ui.widget.TitleBar;
import com.ervin.mvp.utils.DateUtil;
import com.veinhorn.tagview.TagView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ervin on 2017/11/2.
 *
 * 包含话题详情，以及回复信息
 */

public class TopicInfoActivity extends BaseActivity<TopicInfoPresenter> implements ITopicInfoView {


    @BindView(R.id.title_bar)
    TitleBar titleBar;
    @BindView(R.id.iv_avatar)
    CircleImageView ivAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_reply)
    TextView tvReply;
    @BindView(R.id.tagView)
    TagView tagView;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_content_info)
    TextView tvContentInfo;

    private Actors actor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_info);
        ButterKnife.bind(this);
        initPresenter();
    }

    @Override
    protected void initPresenter() {
        presenter = new TopicInfoPresenter(this, this);
        presenter.attachView();
    }

    @Override
    public void initView() {

        titleBar.setLeftText("话题");
        actor = getIntent().getParcelableExtra("topic");

        Glide.with(this).load("http:" + actor.member.avatar_normal).into(ivAvatar);
        tvName.setText(actor.member.username);
        tvTime.setText(DateUtil.formatTime2String(actor.created));
        tvContent.setText(actor.title);
        tagView.setText(actor.node.title);
        tvReply.setText(getString(R.string.replies,actor.replies));

        tvContentInfo.setText(Html.fromHtml(actor.content));
    }

    @Override
    public void showData(Actors actors) {

    }
}

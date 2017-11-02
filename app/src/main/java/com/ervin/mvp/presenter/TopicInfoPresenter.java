package com.ervin.mvp.presenter;

import android.content.Context;

import com.ervin.mvp.ui.iview.ITopicInfoView;

/**
 * Created by Ervin on 2017/11/2.
 */

public class TopicInfoPresenter extends BasePresenter<ITopicInfoView> {


    public TopicInfoPresenter(Context context, ITopicInfoView iView) {
        super(context, iView);
    }
}

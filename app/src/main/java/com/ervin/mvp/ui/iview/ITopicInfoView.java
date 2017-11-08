package com.ervin.mvp.ui.iview;

import com.ervin.mvp.model.Reply;
import com.ervin.mvp.model.Topic;

import java.util.List;

/**
 * Created by Ervin on 2017/11/2.
 */

public interface ITopicInfoView extends IBaseView {


    void showData(List<Reply> reply);

    void showTopic(Topic topic);
}

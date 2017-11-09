package com.ervin.mvp.ui.iview;

import com.ervin.mvp.model.Actors;
import com.ervin.mvp.model.Member;

import java.util.List;

/**
 * Created by Ervin on 2017/10/28.
 */

public interface IMainView extends IBaseView {

    void showData(List<Actors> data);
    //在这里可以做很多view中的操作

    void showUserProfile(Member member);
}

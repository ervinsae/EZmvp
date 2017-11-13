package com.ervin.mvp.ui.iview;

import com.ervin.mvp.model.Actors;

import java.util.List;

/**
 * Created by Ervin on 2017/10/31.
 */

public interface IAllNodeView extends IBaseView{

    void showData(List<Actors> data);

}

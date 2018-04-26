package com.ervin.mvp.ui.iview;

import com.ervin.mvp.model.Actors;

import java.util.List;

public interface IMeNodeView extends IBaseView {

    void showData(List<Actors> data);
}

package com.ervin.mvp.ui.iview;

/**
 * Created by Ervin on 2017/10/31.
 */

public interface IAllNodeView extends IMainView{

    //根据node名称获取结点信息列表
    void getContent(String topic);
}

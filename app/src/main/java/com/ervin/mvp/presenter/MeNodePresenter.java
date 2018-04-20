package com.ervin.mvp.presenter;

import android.content.Context;

import com.ervin.mvp.ui.iview.IMeNodeView;

public class MeNodePresenter extends BasePresenter<IMeNodeView>{
    public MeNodePresenter(Context context, IMeNodeView iView) {
        super(context, iView);
    }
}

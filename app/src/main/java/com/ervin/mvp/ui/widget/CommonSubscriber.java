package com.ervin.mvp.ui.widget;

import com.ervin.mvp.ui.iview.IBaseView;

import io.reactivex.subscribers.ResourceSubscriber;


//支持Flowable不支持Observerable
//如果支持Obsverable需要继承Subscriber<>
public abstract class CommonSubscriber<T> extends ResourceSubscriber<T> {
    private IBaseView mView;
    private String mErrorMsg;
    private boolean isShowErrorState = true;

    protected CommonSubscriber(){}

    protected CommonSubscriber(IBaseView view){
        this.mView = view;
    }

    protected CommonSubscriber(IBaseView view, String errorMsg){
        this.mView = view;
        this.mErrorMsg = errorMsg;
    }

    protected CommonSubscriber(IBaseView view, boolean isShowErrorState){
        this.mView = view;
        this.isShowErrorState = isShowErrorState;
    }

    protected CommonSubscriber(IBaseView view, String errorMsg, boolean isShowErrorState){
        this.mView = view;
        this.mErrorMsg = errorMsg;
        this.isShowErrorState = isShowErrorState;
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
        if (mView == null) {
            return;
        }
        //报错处理(界面统一做报错处理)
    }
}

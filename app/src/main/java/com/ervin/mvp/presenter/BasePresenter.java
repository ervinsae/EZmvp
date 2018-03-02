package com.ervin.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.ervin.mvp.ui.iview.IBaseView;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Ervin on 2017/10/28.
 */

public class BasePresenter<T extends IBaseView> {

    protected Context mContext;
    protected T iView;
    protected CompositeDisposable compositeDisposable;

    //构造函数中建立和view的对应关系，使P中持有view的对象
    public BasePresenter(Context context,T iView){
        this.mContext = context;
        this.iView = iView;

        compositeDisposable = new CompositeDisposable();
    }

    public void attachView(){
        if(iView != null){
            Log.d("TAG------","view created:" + iView.toString());
            iView.initView();
        }
    }

    public void detachView() {
        if(iView != null) {
            Log.d("TAG------","view destroyed" + iView.toString());
            compositeDisposable.clear();
            iView = null;
        }
    }

    /**
     * RxLifecycle支持
     * @param event
     * @param <T>
     * @return
     */
    protected <T> LifecycleTransformer<T> bindUntilEvent(ActivityEvent event){
        if(iView instanceof RxAppCompatActivity){
            RxAppCompatActivity activity = (RxAppCompatActivity) iView;
            return activity.bindUntilEvent(event);
        }

        throw new RuntimeException("Confirm mView is instance of RxAppCompatActivity");
    }

    /**
     * RxLifecycle支持
     * @param event
     * @param <T>
     * @return
     */
    protected <T> LifecycleTransformer<T> bindUntilEvent(FragmentEvent event){
        if(iView instanceof RxFragment){
            RxFragment fragment = (RxFragment) iView;
            return fragment.bindUntilEvent(event);
        }

        throw new RuntimeException("Confirm mView is instance of RxFragment");
    }
}

package com.ervin.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.ervin.mvp.ui.iview.IBaseView;

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
}

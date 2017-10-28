package android.ervin.mvp.ui.activity;

import android.ervin.mvp.presenter.BasePresenter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Ervin on 2017/10/28.
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity{

    protected T presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //由于该基类中不能获取presenter中具体的iView对应的实现类，所以将他抽象给上层去做；
    protected abstract void initPresenter();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(presenter!=null){
            presenter.detachView();
            presenter=null;
        }
    }
}

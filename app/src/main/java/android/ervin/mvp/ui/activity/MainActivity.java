package android.ervin.mvp.ui.activity;

import android.ervin.mvp.R;
import android.ervin.mvp.presenter.MainPresenter;
import android.ervin.mvp.ui.iView.IMainView;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends BaseActivity<MainPresenter> implements IMainView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("main","------------");

       initPresenter();
    }

    @Override
    protected void initPresenter() {
        //在这里实现了P和V的关联
        presenter = new MainPresenter(this,this);
        presenter.attachView();//调用了initView（）;
    }

    @Override
    public void initView() {
        //做一些初始化view的操作
        presenter.getData();
    }

    @Override
    public void showData() {

    }
}

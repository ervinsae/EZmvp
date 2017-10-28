package android.ervin.mvp.presenter;

import android.content.Context;
import android.ervin.mvp.ui.iView.IMainView;

/**
 * Created by Ervin on 2017/10/28.
 */

public class MainPresenter extends BasePresenter<IMainView> {


    public MainPresenter(Context context, IMainView iView) {
        super(context, iView);
    }

    public void getData(){
        //调用网络请求

        //更新view
        iView.showData();
    }
}

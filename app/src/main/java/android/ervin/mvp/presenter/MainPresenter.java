package android.ervin.mvp.presenter;

import android.content.Context;
import android.ervin.mvp.api.ApiClient;
import android.ervin.mvp.model.Actors;
import android.ervin.mvp.ui.iView.IMainView;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ervin on 2017/10/28.
 */

public class MainPresenter extends BasePresenter<IMainView> {


    public MainPresenter(Context context, IMainView iView) {
        super(context, iView);
    }

    public void getData(){
        //调用网络请求
        ApiClient.getmApiService().getV2ExTag4All()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Actors>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<Actors> actors) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        //更新view
        iView.showData();
    }
}

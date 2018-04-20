package com.ervin.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.ervin.mvp.api.ApiClient;
import com.ervin.mvp.model.Actors;
import com.ervin.mvp.ui.iview.IMainView;

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

    public void getNodeAllData(){
        //调用网络请求
        compositeDisposable.add(ApiClient.getApiService().getV2ExTag4All()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new CommonSubscriber<List<Actors>>() {
                    @Override
                    public void onNext(List<Actors> actors) {
                        iView.showData(actors);
                    }
                }));
    }

    public void getNodeHotData(){
        ApiClient.getApiService().getV2ExHotTag()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Actors>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<Actors> actors) {
                        //更新
                        iView.showData(actors);
                        Log.d("data",actors.toString());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getUserProfile(){
        compositeDisposable.add(ApiClient.getApiService().getUser("Ervin")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(member -> iView.showUserProfile(member)));
    }

    public void search(String keyword){
        ApiClient.getApiService().getTopicList(keyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(actors -> iView.showData(actors));
    }
}

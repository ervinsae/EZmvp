package com.ervin.mvp.presenter;

import android.content.Context;

import com.ervin.mvp.api.ApiClient;
import com.ervin.mvp.model.Actors;
import com.ervin.mvp.ui.iview.IMeNodeView;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MeNodePresenter extends BasePresenter<IMeNodeView>{
    public MeNodePresenter(Context context, IMeNodeView iView) {
        super(context, iView);
    }

    public void getMeNodeInfo(String name){
        ApiClient.getApiService().getUserTopic(name)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<Actors>>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(1);
                    }

                    @Override
                    public void onNext(List<Actors> actors) {

                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}

package com.ervin.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.ervin.mvp.api.ApiClient;
import com.ervin.mvp.model.Topic;
import com.ervin.mvp.ui.iview.ITopicInfoView;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ervin on 2017/11/2.
 */

public class TopicInfoPresenter extends BasePresenter<ITopicInfoView> {


    public TopicInfoPresenter(Context context, ITopicInfoView iView) {
        super(context, iView);
    }

    public void getReplyByID(int id){
        Observable.zip(ApiClient.getApiService().getRepliesByTopicId(id),
                ApiClient.getApiService().getTopicInfo(id), (replies, actors) -> {
                    Topic topics = new Topic();
                    topics.setActors(actors.get(0));
                    topics.setReplyList(replies);
                    return topics;
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Topic>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Topic topics) {
                        iView.showTopic(topics);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TAG",e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}

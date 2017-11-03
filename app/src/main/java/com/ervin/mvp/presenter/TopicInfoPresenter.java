package com.ervin.mvp.presenter;

import android.content.Context;

import com.ervin.mvp.api.ApiClient;
import com.ervin.mvp.model.Reply;
import com.ervin.mvp.ui.iview.ITopicInfoView;

import java.util.List;

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
        ApiClient.getApiService().getRepliesByTopicId(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Reply>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<Reply> replies) {
                        iView.showData(replies);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}

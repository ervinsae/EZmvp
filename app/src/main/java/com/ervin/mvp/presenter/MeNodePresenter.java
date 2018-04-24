package com.ervin.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.ervin.mvp.api.ApiClient;
import com.ervin.mvp.model.Actors;
import com.ervin.mvp.ui.iview.IMeNodeView;
import com.ervin.mvp.ui.widget.progress.ProgressSubscriber;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MeNodePresenter extends BasePresenter<IMeNodeView>{
    public MeNodePresenter(Context context, IMeNodeView iView) {
        super(context, iView);
    }

    public void getMeNodeInfo(String name){

        Flowable.just(ApiClient.HOST + "member/Ervin")
                .subscribeOn(Schedulers.io())
                .map(s -> {
                    Log.d("Tag",s);
                    return Jsoup.connect(s).timeout(10000).get();
                })
                .filter(document -> document != null)
                .map(document -> {

                    List<Actors> mList = new ArrayList<>();
                    Elements itemElements = document.select("div.cell.item");    //item根节点
                    for(Element element : itemElements){
                        Elements titleElement = element.select("div.cell.item table tr td span.item_title > a");
                        Elements time = element.select("div.cell.item table tr td span.topic_info");
                        Elements reply = element.select("div.cell.item.table tr td a.count_livid");
                    }

                    return mList;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProgressSubscriber<List<Actors>>(mContext) {
                    @Override
                    public void onNext(List<Actors> actors) {

                    }
                });
    }
}

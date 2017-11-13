package com.ervin.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.ervin.mvp.api.ApiClient;
import com.ervin.mvp.model.Actors;
import com.ervin.mvp.model.Member;
import com.ervin.mvp.model.Node;
import com.ervin.mvp.ui.iview.IMainView;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
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
        ApiClient.getApiService().getV2ExTag4All()
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

    //根据网页HTML抓取actors
    public void getDataByTopicName(String name){
        Flowable.just(ApiClient.TAB_HOST + name)
                .subscribeOn(Schedulers.io())
                .map(s -> Jsoup.connect(s).timeout(10000).get())
                .filter(document -> document != null)
                .map(document -> {

                    List<Actors> mList = new ArrayList<>();
                    Elements itemElements = document.select("div.cell.item");    //item根节点
                    int count = itemElements.size();
                    for (int i = 0; i < count; i++) {
                        Elements titleElements = itemElements.get(i).select("div.cell.item table tr td span.item_title > a");   //标题
                        Elements imgElements = itemElements.get(i).select("div.cell.item table tr td img.avatar");              //头像
                        Elements nodeElements = itemElements.get(i).select("div.cell.item table tr span.small.fade a.node");    //节点
                        Elements commentElements = itemElements.get(i).select("div.cell.item table tr a.count_livid");          //评论数
                        Elements nameElements = itemElements.get(i).select("div.cell.item table tr span.small.fade strong a");  //作者 & 最后回复
                        Elements timeElements = itemElements.get(i).select("div.cell.item table tr span.small.fade");           //更新时间

                        Actors actors = new Actors();

                        Member member = new Member();
                        Node node = new Node();

                        if(titleElements.size() > 0){
                            actors.title = titleElements.get(0).text();
                            actors.id = Integer.parseInt(parseId(titleElements.get(0).attr("href")));
                        }
                        if (imgElements.size() > 0) {
                            member.avatar_normal = imgElements.get(0).attr("src");
                        }
                        if(nodeElements.size() > 0){
                            node.name = nodeElements.get(0).text();
                        }
                        if(nameElements.size() > 0){
                            member.username = nameElements.get(0).text();
                        }

                        if (commentElements.size() > 0) {
                            actors.replies = Integer.valueOf(commentElements.get(0).text());
                        }
                        if (timeElements.size() > 1) {
                            actors.time = parseTime(timeElements.get(1).text());
                        }
                        actors.member = member;
                        actors.node = node;
                        mList.add(actors);
                    }
                    return mList;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(actors -> iView.showData(actors));
    }

    private String parseId(String str) {
        int idEnd = str.indexOf("#");
        return str.substring(3, idEnd);
    }

    private String parseTime(String str) {
        int timeEnd = str.indexOf("  •");
        if (timeEnd == -1) {
            return str;
        }
        return str.substring(0, timeEnd);
    }

    public void getUserProfile(){
        ApiClient.getApiService().getUser("Ervin")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(member -> iView.showUserProfile(member));
    }
}

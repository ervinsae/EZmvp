package com.ervin.mvp.presenter;

import android.content.Context;
import android.util.Log;

import com.ervin.mvp.api.ApiClient;
import com.ervin.mvp.model.Actors;
import com.ervin.mvp.model.Member;
import com.ervin.mvp.model.Node;
import com.ervin.mvp.ui.iview.IAllNodeView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ervin on 2017/11/7.
 */

public class AllNodePresenter  extends BasePresenter<IAllNodeView>{


    public AllNodePresenter(Context context, IAllNodeView iView) {
        super(context, iView);
    }


    //根据网页HTML抓取actors
    public void getDataByTopicName(String name){
        compositeDisposable.add(Flowable.just(ApiClient.TAB_HOST + name)
                .subscribeOn(Schedulers.io())
                .map(s -> {
                    Log.d("Tag",s);
                    return Jsoup.connect(s).timeout(10000).get();
                })
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
                        if (timeElements.size() > 0) {
                            for(org.jsoup.nodes.Node childNode : timeElements.get(0).childNodes()){
                                if(childNode != null){
                                    if(childNode.toString().contains("•")){

                                        actors.time = parseTime(childNode.toString());
                                    }
                                }
                            }
                        }
                        actors.member = member;
                        actors.node = node;
                        mList.add(actors);
                    }
                    return mList;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(actors -> iView.showData(actors)));
    }

    public void getChildNodeData(String name){
        //ApiClient.getApiService().parseHtml(name,1)
        Flowable.just(ApiClient.TAB_HOST_GO + name)
                .subscribeOn(Schedulers.io())
                .map(s -> {
                    //Log.d("Tag",s.string());
                    //return Jsoup.parse(s.string());
                    return Jsoup.connect(s).timeout(10000).get();
                })
                .filter(document -> document != null)
                .map(document -> {
                    List<Actors> mList = new ArrayList<>();
                    Element itemElement = document.getElementById("TopicsNode");
                    //Elements elements = document.select("div#TopicsNode");
                    Elements cell = itemElement.children();
                    int count = cell.size();
                    for (int i = 0; i < count; i++) {
                        Elements titleElements = cell.get(i).select("span.item_title > a");
                        Elements imgElements = cell.get(i).select("img.avatar");
                        Elements commentElements = cell.get(i).select("a.count_livid");
                        Elements nameElements = cell.get(i).select("span.small.fade");

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

                        node.name = name;

                        if(nameElements.size() > 0){
                            String[] data = nameElements.get(0).text().split("•");
                            member.username = data[0];
                            if(data.length > 1) {
                                actors.time = data[1];
                            }
                        }

                        if (commentElements.size() > 0) {
                            actors.replies = Integer.valueOf(commentElements.get(0).text());
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
        String[] time = str.split(";");
        return time[2].replace("&nbsp","");
        /*int timeEnd = str.indexOf("  •");
        if (timeEnd == -1) {
            return str;
        }
        return str.substring(0, timeEnd);*/
    }

}

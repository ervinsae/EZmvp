package com.ervin.mvp.presenter;

import android.content.Context;

import com.ervin.mvp.api.ApiClient;
import com.ervin.mvp.model.Actors;
import com.ervin.mvp.model.Member;
import com.ervin.mvp.model.Node;
import com.ervin.mvp.ui.iview.IMeNodeView;
import com.ervin.mvp.ui.widget.progress.ProgressSubscriber;
import com.ervin.mvp.utils.V2exPraser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MeNodePresenter extends BasePresenter<IMeNodeView>{
    public MeNodePresenter(Context context, IMeNodeView iView) {
        super(context, iView);
    }

    public void getMeNodeInfo(String name){

        ApiClient.getApiService().getUserTopic(name)
                .subscribeOn(Schedulers.io())
                .map(responseBody -> Jsoup.parse(responseBody.string()))
                .filter(document -> document != null)
                .map(document -> {

                    List<Actors> mList = new ArrayList<>();
                    Elements itemElements = document.select("div.cell.item");    //item根节点
                    for(Element element : itemElements){
                        Elements titleElement = element.select("div.cell.item table tr td span.item_title > a");
                        Elements timeElement = element.select("span.topic_info");
                        Elements replyElement = element.select("a.count_livid");

                        Actors actors = new Actors();

                        Member member = new Member();
                        Node node = new Node();

                        if(titleElement.size() > 0){
                            actors.title = titleElement.get(0).text();
                            actors.id = Integer.parseInt(V2exPraser.parseId(titleElement.get(0).attr("href")));
                        }

                        if (timeElement.size() > 0) {
                            String timeData = timeElement.text();

                            String childData[] = timeData.split("•");
                            node.title = childData[0];
                            member.username = childData[1];
                            if(childData.length > 2){
                                actors.time = childData[2];
                            }
                        }

                        if(replyElement.size() > 0){
                            actors.replies = Integer.parseInt(replyElement.text());
                        }

                        actors.member = member;
                        actors.node = node;
                        mList.add(actors);
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

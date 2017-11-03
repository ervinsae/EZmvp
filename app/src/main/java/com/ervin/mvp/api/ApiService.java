package com.ervin.mvp.api;

import com.ervin.mvp.model.Actors;
import com.ervin.mvp.model.Reply;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Ervin on 2017/10/30.
 */

public interface ApiService {

    //获取最新主题
    @GET("topics/latest.json")
    Observable<List<Actors>> getV2ExTag4All();

    /**
     * 获取主题列表
     * @return
     */
    @GET("topics/show.json")
    Flowable<List<Actors>> getTopicList(@Query("node_name") String name);

    /**
     * 获取主题信息
     * @return
     */
    @GET("topics/show.json")
    Flowable<Actors> getTopicInfo(@Query("id") String id);

    /**
     * 获取主题回复
     * @param id
     * @return
     */
    @GET("replies/show.json")
    Observable<List<Reply>> getRepliesByTopicId(@Query("topic_id") int id);
}

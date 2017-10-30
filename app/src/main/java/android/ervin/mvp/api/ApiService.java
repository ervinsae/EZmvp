package android.ervin.mvp.api;

import android.ervin.mvp.model.Actors;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Ervin on 2017/10/30.
 */

public interface ApiService {

    //获取最新主题
    @GET("topics/latest.json")
    Observable<List<Actors>> getV2ExTag4All();
}

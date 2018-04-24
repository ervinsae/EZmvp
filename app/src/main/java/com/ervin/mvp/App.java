package com.ervin.mvp;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class App extends Application{

    private static App mInstance;

    public static App getInstance() {
        return mInstance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        Logger.addLogAdapter(new AndroidLogAdapter());
    }
}

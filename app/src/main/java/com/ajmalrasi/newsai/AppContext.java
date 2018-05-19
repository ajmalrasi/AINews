package com.ajmalrasi.newsai;

import android.app.Application;
import android.content.Context;

/**
 * Created by Rasi on 19-05-2018.
 */
public class AppContext extends Application {

    private static AppContext sInstance;

    public static AppContext getsInstance() {
        return sInstance;
    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

}

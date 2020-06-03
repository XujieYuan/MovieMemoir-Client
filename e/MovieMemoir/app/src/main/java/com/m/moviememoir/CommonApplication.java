package com.m.moviememoir;

import android.app.Application;
import android.content.Context;

public class CommonApplication extends Application {
    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}

package com.example.a17374.adressbooks;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

public class putContext extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
    public static Context getContext(){
        return context;
    }
}

package com.shofiq.tvshowapp.controller;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;


import java.util.HashMap;
import java.util.Map;


public class AppController extends Application {

    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }
    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public static boolean hasNetwork(){
        return mInstance.isNetworkConnected();
    }

    private boolean isNetworkConnected(){
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        NetworkCapabilities nc = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            nc = cm.getNetworkCapabilities(cm.getActiveNetwork());
            if (nc!=null){
                int downSpeed = nc.getLinkDownstreamBandwidthKbps();
                int upSpeed = nc.getLinkUpstreamBandwidthKbps();
            }
        }
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
}
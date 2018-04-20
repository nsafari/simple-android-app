package com.elisa.simple_android_app;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

/**
 * Created by nSafari on 12/24/2016.
 */

public class SimpleApp extends MultiDexApplication {
    public static final String LOG_TAG = SimpleApp.class.getSimpleName();

    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            mContext = this;
        } catch (Exception e) {
            Log.e(LOG_TAG, "Could not set context", e);
        }
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}

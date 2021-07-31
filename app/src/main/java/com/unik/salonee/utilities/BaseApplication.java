package com.unik.salonee.utilities;

import android.app.Application;
import android.content.Context;


public class BaseApplication extends Application {
    private static BaseApplication mInstance;

    public BaseApplication() {
    }

    public static synchronized BaseApplication getApp() {
        return mInstance;
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        //  MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        /*if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);*/

        mInstance = this;
        //Stetho.initializeWithDefaults(this);
    }
}

package com.hamsofts.startips;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.google.firebase.FirebaseApp;

public class Startip extends MultiDexApplication {

    public static final String TAG = Startip.class
            .getSimpleName();


    private static Startip mInstance;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        // FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        FirebaseApp.initializeApp(this);
        mInstance = this;
        Startip.context = getApplicationContext();
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }



}


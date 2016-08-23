package com.xueyan.personal.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Administrator on 2016/8/23.
 */
public class FloatService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        CustomViewManager.getInstance(this).showFloatViewOnWindow();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
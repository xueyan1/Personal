package com.xueyan.personal.service;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by Administrator on 2016/11/3 0003.
 */

public class MyService extends IntentService {
    public MyService(){
      this("MyService");
    }
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MyService(String name) {
        super(name);
    }
//执行在  子线程
    @Override
    protected void onHandleIntent(Intent intent) {

        String url = intent.getStringExtra("url");
        downLoad(url);
    }

    private void downLoad(String url) {
        //这里是下载的方法
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

package com.xueyan.personal.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.xueyan.personal.R;
import com.xueyan.personal.java.OnClickInject;
import com.xueyan.personal.java.ViewInject;
import com.xueyan.personal.java.ViewUtils;
import com.xueyan.personal.receiver.SMSReceiver;
import com.xueyan.personal.service.MyService;

public class GuideActivity extends AppCompatActivity {
    @ViewInject(R.id.btnStar)
    private Button mButton;
    @ViewInject(R.id.viewPage)
    private ViewPager mViewPager;
    private Handler mHandler;
    private Looper mMyLooper;
    private SMSReceiver mSMSReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gulide);
        ViewUtils.inject(this);
        sendMsg();
        //匿名内部类对象对外部一个隐式的强引用
        SubThreadHandler();
    }

    private void SubThreadHandler() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();

                mHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        Toast.makeText(GuideActivity.this, "主线程发来的", Toast.LENGTH_SHORT).show();

                    }
                };
                mMyLooper = Looper.myLooper();
                Looper.loop();
            }
        }).start();
        //开启下载服务
        Intent intent = new Intent(this, MyService.class);
        intent.putExtra("url", "www.baidu.com");
        startService(intent);
        Intent intent1 =new Intent();
        intent1.setAction(Intent.ACTION_EDIT);
        this.sendBroadcast(intent1);
        //注册广播
        registerReceiverD();


    }

    private void unregisterReceiverD() {
        this.unregisterReceiver(mSMSReceiver);
    }

    private void registerReceiverD() {
        mSMSReceiver = new SMSReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.provider.Telphone.SMS_RECEIVER");
        this.registerReceiver(mSMSReceiver,intentFilter);
    }


    @OnClickInject(R.id.btnStar)
    private void click(View view) {
        Toast.makeText(this, "OnClick", Toast.LENGTH_SHORT).show();
    }

    private void sendMsg() {
        Message message = mHandler.obtainMessage(1, "我是主线程发来的消息");
        message.sendToTarget();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMyLooper != null) {
            mMyLooper.quit();
            mMyLooper = null;
        }
        //解除服务的绑定
        unregisterReceiverD();
    }

}

package com.xueyan.personal.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.xueyan.personal.R;

public class WelcomeActivity extends BaseActivity {
    private Handler mHandler= new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0x1);
        }

        mHandler .postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sp = getPreferences(MODE_PRIVATE);
                boolean isFirst = sp.getBoolean("isFirst", true);
                Intent intent = new Intent();
                if (isFirst){
                    //如果是第一次进入，就显示引导页，将isfirst重置为false
                    sp.edit().putBoolean("isFirst",false).commit();
                    intent.setClass(WelcomeActivity.this,GuideActivity.class);
                }else {
                    intent.setClass(WelcomeActivity.this,PersonalActivity.class);
                }
                startActivity(intent);
                //自定义动画
                overridePendingTransition(R.anim.zoomin,R.anim.zoomout);
                //实现淡入浅出的效果
                //overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                //由左向右滑入的效果
                //overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
                finish();
            }
        },3000);
    }
}

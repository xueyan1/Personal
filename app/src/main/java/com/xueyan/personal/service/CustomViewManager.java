package com.xueyan.personal.service;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.xueyan.personal.floatview.FloatView;

/**
 * Created by Administrator on 2016/8/23.
 */
public class CustomViewManager {
    //上下文
    private Context mContext;
    //本类实例
    private static CustomViewManager instance;
    //自定义的FloatView
    private FloatView mFloatView;
    //窗口管理类
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams params;
    private float x;
    private float y;

    private CustomViewManager(Context context) {
        this.mContext = context;
        mFloatView = new FloatView(mContext);
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mFloatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "点击了", Toast.LENGTH_SHORT).show();

            }
        });
//        mFloatView.setOnTouchListener((new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                float mTouchStartX = 0;
//                float mTouchStartY = 0;
//                params = new WindowManager.LayoutParams();
//                params.gravity = Gravity.CENTER;
//
//                // 获取相对屏幕的坐标，即以屏幕左上角为原点
//                x = event.getRawX();
//                y = event.getRawY() - 25; // 25是系统状态栏的高度
//
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        // 获取相对View的坐标，即以此View左上角为原点
//                        mTouchStartX = event.getX();
//                        mTouchStartY = event.getY();
//
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        //更新浮动窗口位置参数
//                        params.x = (int) (x - mTouchStartX);
//                        params.y = (int) (y - mTouchStartY);
//                        mWindowManager.updateViewLayout(v, params);
//                        break;
//
//                    case MotionEvent.ACTION_UP:
//
//                        //更新浮动窗口位置参数
//                        params.x = (int) (x - mTouchStartX);
//                        params.y = (int) (y - mTouchStartY);
//                        mWindowManager.updateViewLayout(v, params);
//                        //可以在此记录最后一次的位置
//                        mTouchStartX = mTouchStartY = 0;
//                        break;
//                }
//                return true;
//            }
//
//        }));
    }

    public static CustomViewManager getInstance(Context mContext) {
        if (null == instance) {
            synchronized (CustomViewManager.class) {
                if (null == instance) {
                    instance = new CustomViewManager(mContext);
                }
            }
        }
        return instance;
    }


    public void showFloatViewOnWindow() {
        WindowManager.LayoutParams parmas = new WindowManager.LayoutParams();
        parmas.width = mFloatView.getFloatWidth();
        parmas.height = mFloatView.getFloatHeight();
        //窗口图案放置位置
        parmas.gravity = Gravity.LEFT | Gravity.CENTER;
        // 如果忽略gravity属性，那么它表示窗口的绝对X位置。
        parmas.x = 0;
        //如果忽略gravity属性，那么它表示窗口的绝对Y位置。
        parmas.y = 0;
        ////电话窗口。它用于电话交互（特别是呼入）。它置于所有应用程序之上，状态栏之下。
        parmas.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        //FLAG_NOT_FOCUSABLE让window不能获得焦点，这样用户快就不能向该window发送按键事件及按钮事件
        //FLAG_NOT_TOUCH_MODAL即使在该window在可获得焦点情况下，仍然把该window之外的任何event发送到该window之后的其他window.
        parmas.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // 期望的位图格式。默认为不透明。参考android.graphics.PixelFormat。
        parmas.format = PixelFormat.RGBA_8888;

        mWindowManager.addView(mFloatView, parmas);
    }
}
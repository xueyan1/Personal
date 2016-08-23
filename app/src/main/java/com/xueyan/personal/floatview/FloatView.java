package com.xueyan.personal.floatview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.xueyan.personal.MainActivity;
import com.xueyan.personal.util.Util;

/**
 * Created by Administrator on 2016/8/23.
 */

public class FloatView  extends View{
    //悬浮球宽度
    private int floatWidth = 150;
    //悬浮球高度
    private int floatHeight = 150;
    //悬浮球画笔
    private Paint mPaint;
    //绘制文字画笔
    private Paint mTextPaint;
    private String text = "鎏嫣宫守护";
    private  Context mContext;
    public FloatView(Context context) {
        super(context);
      this. mContext = context;
        initPaint();
    }


    public int getFloatWidth() {
        return floatWidth;
    }


    public int getFloatHeight() {
        return floatHeight;
    }

    public FloatView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public FloatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }


    private void initPaint() {
        //设置悬浮球画笔
        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        //设置文字画笔
        mTextPaint = new Paint();
        mTextPaint.setTextSize(25);
        mPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setFakeBoldText(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //设置大小
        setMeasuredDimension(floatWidth, floatHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制悬浮球
        canvas.drawCircle(floatWidth / 2, floatHeight / 2, floatWidth / 2, mPaint);
        //绘制文字
        Paint.FontMetrics metrics = mTextPaint.getFontMetrics();
        //文字大小计算可以参考：http://mikewang.blog.51cto.com/3826268/871765/
        float textWidth = mTextPaint.measureText(text);
        float x = floatWidth / 2 - textWidth / 2;
        float dy = -(metrics.descent + metrics.ascent) / 2;
        float y = floatHeight / 2 + dy;
        /*增加图片*/
//        Bitmap mBitmap=  Util.getBitmap(mContext,"photo");
//        canvas.drawBitmap(mBitmap,0.f,0.f,mTextPaint);

        canvas.drawText(text, x, y, mTextPaint);

    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l);

    }

    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener l) {
        super.setOnFocusChangeListener(l);
    }

    @Override
    public void setOnLongClickListener(OnLongClickListener l) {
        super.setOnLongClickListener(l);
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        super.setOnTouchListener(l);
    }

    @Override
    public void setOnScrollChangeListener(OnScrollChangeListener l) {
        super.setOnScrollChangeListener(l);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        return super.onKeyLongPress(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

}

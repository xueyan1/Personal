package com.xueyan.personal.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/10/28 0028.
 */

public class MyView extends View {

    private Paint mPaint;

    public MyView(Context context) {
        super(context);
        initPaint();
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    //测量View控件
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    //画控件
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        // 画布
        //绘制一个圆
        canvas.drawCircle(60, 60, 60, mPaint);
        //三角形
        Path path = new Path();
        path.moveTo(60, 60);
        path.lineTo(60, 60);
        path.lineTo(120, 0);
        path.moveTo(120, 0);
        path.lineTo(180, 120);
        path.moveTo(180, 120);
        //绘制点
        Point point = new Point();
        canvas.drawPoint(200, 200, mPaint);
        path.close();
        canvas.drawPath(path, mPaint);

        //绘制椭圆。环形、扇形、

        //绘制bitmap
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), android.R.mipmap.sym_def_app_icon);
        canvas.drawBitmap(bitmap, 200, 250, mPaint);

    }

    @NonNull
    private void initPaint() {
        // 画笔
        mPaint = new Paint();
        //抗锯齿
        mPaint.setAntiAlias(true);
        //画笔宽度
        mPaint.setStrokeWidth(5);
        //画笔颜色
        mPaint.setColor(Color.BLUE);
        //画笔样式
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }
}

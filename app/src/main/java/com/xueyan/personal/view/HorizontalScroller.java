package com.xueyan.personal.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by Administrator on 2016/8/25.
 */
/*水平滑动的view*/
public class HorizontalScroller extends ViewGroup {
    private Scroller mScroller;
    public HorizontalScroller(Context context) {
        super(context);
        init(context);
    }

    public HorizontalScroller(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HorizontalScroller(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public HorizontalScroller(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private int mTouchSlop;


    /*获取触摸事件的最小值*/
    private void init(Context context) {
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
        mScroller = new Scroller(context);
    }


    private float mLastXIntercept = 0;
    private float mLastYIntercept = 0;
    private float mLastX = 0;
    private float mLastY = 0;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        float xIntercept = ev.getX();
        float yIntercept = ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaX = xIntercept - mLastXIntercept;
                float deltaY = yIntercept - mLastYIntercept;
                if (Math.abs(deltaX) > Math.abs(deltaY) && Math.abs(deltaX) > mTouchSlop) {
                    intercept = true;
                } else {
                    intercept = false;
                }

                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                break;
            default:
                break;
        }
        mLastXIntercept = xIntercept;
        mLastYIntercept = yIntercept;
        mLastX = xIntercept;
        mLastY = yIntercept;

        return intercept;

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float xTouch = event.getX();
        float yTouch = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished())
                    mScroller.abortAnimation();

                break;
            case MotionEvent.ACTION_MOVE:
                float deltaX = xTouch - mLastX;
                float deltaY = yTouch - mLastY;
                float scrollByStart = deltaX;
                if (getScaleX() - deltaX < leftBorder) {
                    scrollByStart = deltaX/3;
                } else if (getScaleX() + getWidth() - deltaX > rightBorder) {
                    scrollByStart =deltaX/3;
                }

                scrollBy((int) -scrollByStart, 0);

                break;
            case MotionEvent.ACTION_UP:
                int targetIndex = (getScrollX() + getWidth() / 2) / getWidth();

                if (targetIndex>getChildCount()-1){
                    targetIndex =getChildCount()-1;
                }else if (targetIndex<0){
                    targetIndex = 0;
                }

                int dx = (int) (targetIndex * getWidth() - getScaleX());

                mScroller.startScroll(getScrollX(),0,dx,0);
                invalidate();
              //  scrollTo(getScrollX() + dx, 0);

                break;
            default:
                break;
        }
        mLastX = xTouch;
        mLastY = yTouch;
        return super.onTouchEvent(event);
    }
    @Override
    public void computeScroll() {
            if (mScroller.computeScrollOffset()){
                scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
                postInvalidate();
            }
    }




/*测量每个子view的大小*/
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childCount = getChildCount();//获取子控件的数量
        for (int i = 0; i <childCount ; i++) {
            View childView = getChildAt(i);
            measureChild(childView,widthMeasureSpec,heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private int leftBorder;
    private int rightBorder;



    /*因为是水平的滑动，所以在水方向对子控件进行布局*/
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed){
            int childCount = getChildCount();
            for (int i = 0; i <childCount ; i++) {
                View childView = getChildAt(i);
                childView.layout(i*getMeasuredWidth(),0,i*getMeasuredWidth()+childView.getMeasuredWidth()+getPaddingLeft(),childView.getMeasuredHeight());
            }

            leftBorder = 0;
            rightBorder = getChildCount()*getMeasuredWidth();
        }

    }
}

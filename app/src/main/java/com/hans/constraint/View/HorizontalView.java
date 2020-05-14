package com.hans.constraint.View;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @创建者 xu
 * @创建时间 2020/5/9
 * @描述
 */
public class HorizontalView extends ViewGroup {

    private int lastX;
    private int lastY;

    private Scroller scroller;
    private VelocityTracker tracker;
    private int childWidth = 0;
    private int currentIndex = 0;

    public HorizontalView(Context context) {
        super(context);
        init(context);
    }

    public HorizontalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HorizontalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        scroller = new Scroller(context);
        tracker = VelocityTracker.obtain();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //测量模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //测量宽高
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //测量所有子View
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        if (childCount == 0) {
            setMeasuredDimension(0, 0);
        } else if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            View childAt = getChildAt(0);
            int childWidth = childAt.getMeasuredWidth();
            int childHeight = childAt.getMeasuredHeight();
            //View的宽度=单个子View宽度*子View个数，View的高度=子View高度
            setMeasuredDimension(childWidth * childCount, childHeight);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            View childAt = getChildAt(0);
            int childWidth = childAt.getMeasuredWidth();
            //View的宽度=单个子View宽度*子View个数，View的高度=xml当中设置的高度
            setMeasuredDimension(childWidth * childCount, heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            View childAt = getChildAt(0);
            int childHeight = childAt.getMeasuredHeight();
            //View的宽度=xml当中设置的宽度，View的高度=子View高度
            setMeasuredDimension(widthSize, childHeight);
        }
    }

    @Override
    protected void onLayout(boolean c, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int left = 0;
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() != GONE) {
                childWidth = childAt.getMeasuredWidth();
                childAt.layout(left, 0, left + childWidth, childAt.getMeasuredHeight());
                left += childWidth;
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercept = false;
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - lastX;
                int deltaY = y - lastY;
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    intercept = true;
                }
                break;
        }
        lastX = x;
        lastY = y;
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        tracker.addMovement(event);
        //获取事件坐标（x，y）
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - lastX;
                int deltaY = y - lastY;
                scrollBy(-deltaX, 0);
                break;
            case MotionEvent.ACTION_UP:
                int distance = getScrollX() - childWidth * currentIndex;
                if (Math.abs(distance) > childWidth / 2) {
                    if (distance > 0) {
                        currentIndex++;
                    } else {
                        currentIndex--;
                    }
                } else {
                    //获取X轴加速度，units为单位，默认为像素，这里为每秒1000个像素点
                    tracker.computeCurrentVelocity(1000);
                    float xV = tracker.getXVelocity();
                    //当X轴加速度>50时，也就是产生了快速滑动，也会切换View
                    if (Math.abs(xV) > 50) {
                        if (xV < 0) {
                            currentIndex++;
                        } else {
                            currentIndex--;
                        }
                    }
                }
                //对currentIndex做出限制其范围为【0，getChildCount() - 1】
                currentIndex = currentIndex < 0 ? 0 : Math.min(currentIndex, getChildCount() - 1);
                //滑动到下一个View
                smoothScrollTo(currentIndex * childWidth, 0);
                tracker.clear();
                break;
        }
        lastX = x;
        lastY = y;
        return true;
    }

    private void smoothScrollTo(int destX, int destY) {
        //startScroll方法将产生一系列偏移量，从（getScrollX(), getScrollY()），destX - getScrollX()和destY - getScrollY()为移动的距离
        scroller.startScroll(getScrollX(), getScrollY(), destX - getScrollX(), destY - getScrollY(), 1000);
        //invalidate方法会重绘View，也就是调用View的onDraw方法，而onDraw又会调用computeScroll()方法
        invalidate();
    }

    //重写computeScroll方法
    @Override
    public void computeScroll() {
        super.computeScroll();
        //当scroller.computeScrollOffset()=true时表示滑动没有结束
        if (scroller.computeScrollOffset()) {
            //调用scrollTo方法进行滑动，滑动到scroller当中计算到的滑动位置
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            //没有滑动结束，继续刷新View
            postInvalidate();
        }
    }
}

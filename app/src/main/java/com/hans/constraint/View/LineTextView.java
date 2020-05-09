package com.hans.constraint.View;

import android.content.Context;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

/**
 * @创建者 xu
 * @创建时间 2020/4/29
 * @描述
 */
public class LineTextView extends androidx.appcompat.widget.AppCompatTextView {

    private Paint paint;
    private RectF mRect;

    /**
     * 在java代码里new的时候会用到
     *
     * @param context
     */
    public LineTextView(Context context) {
        this(context, null);
    }

    /**
     * 在xml布局文件中使用时自动调用
     *
     * @param context
     */
    public LineTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 不会自动调用，如果有默认style时，在第二个构造函数中调用
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public LineTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        paint = new Paint();
        Typeface typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
        paint.setTypeface(typeface);

        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);

        mRect = new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获取测量模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        //获取测量模式
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
      //  Log.d("onMeasure", widthSize + "==" + heightSize);


        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();

        setMeasuredDimension(400,300);
       // Log.d("onMeasure", measuredWidth + "==" + measuredHeight);
    }

    //重写draw方法，绘制我们需要的中间线以及背景
    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();

       // Log.d("onMeasure", getLeft() + "==" + getTop() + "==" + getRight() + "==" + getBottom());

        //绘制方形背景
        mRect.set(0, 0, width, height);
        canvas.drawRect(mRect, paint);

        paint.setColor(Color.GREEN);
        mRect.set(10f, 10f, width - 10, height - 10);
        canvas.drawRect(mRect, paint);

        super.onDraw(canvas);
        /*paint.setColor(Color.RED);
        //设置空心的边框宽度
        paint.setStrokeWidth(10f);
        canvas.drawLine(0, height / 2f, width, height / 2f, paint);
        canvas.drawLine(width / 2f, 0, width / 2f, height, paint);*/

        /*canvas.drawLine(0, 0, width, 0, paint);
        canvas.drawLine(0, 0, 0, height, paint);
        canvas.drawLine(0, height, width, height, paint);
        canvas.drawLine(width, 0, width, height, paint);*/

        /*canvas.drawLine(0, 0, width, 0, paint);
        canvas.drawLine(0, 0, 0, height, paint);
        canvas.drawLine(0, height, width, height, paint);
        canvas.drawLine(width, 0, width, height, paint);

        canvas.drawLine(0, 0, width, height, paint);
        canvas.drawLine(0, height, width, 0, paint);*/
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float x = getX();
                float y = getY();
                Log.d("onMeasure", getX() + "==" + getY());
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                Log.d("onMeasure", getX() + "==" + getY());
                break;
        }
        return super.onTouchEvent(event);
    }
}

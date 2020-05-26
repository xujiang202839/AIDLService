package com.hans.constraint.activity;

import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.hans.constraint.R;
import com.hans.constraint.base.BaseActivity;
import com.hans.constraint.bean.Penguin;
import com.hans.constraint.bean.father.SubClass;
import com.hans.constraint.bean.father.SubClass2;
import com.tbruyelle.rxpermissions2.RxPermissions;


import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AnimationActivity extends BaseActivity {

    @BindView(R.id.bt_ani)
    Button btAni;
    @BindView(R.id.iv_value)
    ImageView ivValue;
    @BindView(R.id.iv_bottom)
    ImageView ivBottom;

    private Animation animation;
    private ValueAnimator animator;

    @Override
    protected int getResId() {
        return R.layout.activity_animation;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {


       /* animator = ValueAnimator.ofFloat(0, 360);
        animator.setDuration(5000);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setRepeatCount(1);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float)valueAnimator.getAnimatedValue();
                ivValue.setRotationY(value);
            }
        });*/


        /*Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.d("接收数据",String.valueOf(aLong));

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });*/




    }

    @OnClick(R.id.bt_ani)
    public void onViewClicked() {
        //btAni.setBackgroundResource(R.drawable.frame_animation);//把Drawable设置为button的背景
        //拿到这个我们定义的Drawable，实际也就是AnimationDrawable

        // AnimationDrawable animationDrawable = (AnimationDrawable) btAni.getBackground();
        //   animationDrawable.start();//开启动画
        if (animation == null) {
            animation = AnimationUtils.loadAnimation(this, R.anim.tween_animation);
            btAni.startAnimation(animation);
        } else {
            btAni.startAnimation(animation);
        }

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                btAni.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                btAni.setEnabled(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    @OnClick(R.id.iv_value)
    public void onViewClick() {
        //  animator.start();

        int right = ivBottom.getRight();

        int x = (int) ivValue.getX();
        int y = (int) ivValue.getY();
        Log.d("initView", x + "==" + y + "==" + right);
       // path2();

       new  Penguin("企鹅",1).eat();

        System.out.println("------SubClass 类继承------");
        SubClass sc1 = new SubClass();

        SubClass sc2 = new SubClass(100);

        System.out.println("------SubClass2 类继承------");
        SubClass2 sc3 = new SubClass2();
        SubClass2 sc4 = new SubClass2(200);

    }

    public void path2() {
        final float[] position = new float[2];
        final Path path = new Path();
        path.moveTo(432, 789);
        //path.lineTo(0, 960);
        path.quadTo(432, 789, 432, 0);

        path.quadTo(432, 789, 0, 1291.5f);
        path.quadTo(0, 1291.5f, 432, 1578);


        path.quadTo(432, 1578, 864, 1291.5f);

        path.quadTo(864, 1291.5f, 432, 1291.5f);
        // path.quadTo(864, 960, 0, 960);
        path.quadTo(864, 1291.5f, 432, 789);

        RectF oval = new RectF(432, 0, 432, 0);

        path.arcTo(oval, 0, 270);

        final PathMeasure pathMeasure = new PathMeasure();
        pathMeasure.setPath(path, false);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, pathMeasure.getLength());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float lenth = (float) animation.getAnimatedValue();
                pathMeasure.getPosTan(lenth, position, null);
                ivValue.setX(position[0]);
                ivValue.setY(position[1]);
            }
        });
        valueAnimator.setDuration(2000);
        valueAnimator.start();
    }

    @OnClick(R.id.iv_bottom)
    public void onViewBottom() {
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
       // finish();
        overridePendingTransition(0,R.anim.rotate_down);
    }
}

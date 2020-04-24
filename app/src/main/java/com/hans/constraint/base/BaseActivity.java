package com.hans.constraint.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ColorUtils;
import com.hans.constraint.R;

import butterknife.ButterKnife;

/**
 * @创建者 xu
 * @创建时间 2020/4/24
 * @描述
 */
public abstract class BaseActivity extends AppCompatActivity {

    public final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResId());
        ButterKnife.bind(this);
        initStatus(R.color.transparent, true);
        initView(savedInstanceState);
        doWork();
    }

    protected void initStatus(int transparent, boolean isLight) {
        //状态栏背景颜色
        BarUtils.setStatusBarColor(this, ColorUtils.getColor(transparent));
        //状态字体颜色
        BarUtils.setStatusBarLightMode(this, isLight);
    }

    /**
     * 加载数据
     */
    protected void doWork() {
    }

    //初始化view
    protected void initView(Bundle savedInstanceState) {

    }

    /**
     * 布局Id
     *
     * @return
     */
    protected abstract int getResId();

}

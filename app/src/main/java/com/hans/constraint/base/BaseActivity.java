package com.hans.constraint.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
        initView();
        initData();
    }

    /**
     * 加载数据
     */
    private void initData() {
    }

    //初始化view
    protected void initView() {

    }

    /**
     * 布局Id
     * @return
     */
    protected abstract int getResId();

}

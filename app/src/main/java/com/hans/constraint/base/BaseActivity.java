package com.hans.constraint.base;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ColorUtils;
import com.hans.constraint.R;
import com.hans.constraint.View.GrayFrameLayout;

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

    /**
     * 全局灰色背景
     * @param transparent
     * @param isLight
     */
    /*@Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        try {
            if ("FrameLayout".equals(name)) {
                int count = attrs.getAttributeCount();
                for (int i = 0; i < count; i++) {
                    String attributeName = attrs.getAttributeName(i);
                    String attributeValue = attrs.getAttributeValue(i);
                    if (attributeName.equals("id")) {
                        int id = Integer.parseInt(attributeValue.substring(1));
                        String idVal = getResources().getResourceName(id);
                        if ("android:id/content".equals(idVal)) {
                            GrayFrameLayout grayFrameLayout = new GrayFrameLayout(context, attrs);
                           //grayFrameLayout.setWindow(getWindow());
                            return grayFrameLayout;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onCreateView(name, context, attrs);
    }*/

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

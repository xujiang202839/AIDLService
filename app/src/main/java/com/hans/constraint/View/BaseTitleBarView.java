package com.hans.constraint.View;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.hans.constraint.R;

/**
 * @创建者 xu
 * @创建时间 2020/4/26
 * @描述 自定义状态栏
 */
public class BaseTitleBarView extends LinearLayout {

    private TextView tv_title, tv_button;
    private ImageView iv_back, iv_button;

    public BaseTitleBarView(Context context) {
        this(context, null);
    }

    public BaseTitleBarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseTitleBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //加载布局
        LayoutInflater.from(context).inflate(R.layout.activity_base_title_tar, this);
        tv_title = findViewById(R.id.base_title);
        iv_button = findViewById(R.id.base_button);
        tv_button = findViewById(R.id.tv_button);
        iv_back = findViewById(R.id.base_back);

        /**
         * 返回键
         */
        iv_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity) getContext()).finish();
            }
        });

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BaseTitleBarView);
        String title = typedArray.getString(R.styleable.BaseTitleBarView_barTitle);
        //文字按钮
        String textBar = typedArray.getString(R.styleable.BaseTitleBarView_barText);
        int textColor = typedArray.getColor(R.styleable.BaseTitleBarView_barTextColor, context.getResources().getColor(R.color.black_33));

        //图片按钮
        Integer button = typedArray.getResourceId(R.styleable.BaseTitleBarView_barButton, 0);

        //返回键是否隐藏
        boolean back = typedArray.getBoolean(R.styleable.BaseTitleBarView_barBack, true);
        initView(title, button, textBar, textColor, back);
        typedArray.recycle();
    }

    private void initView(String title, Integer button, String textBar, int textColor, boolean back) {
        if (!TextUtils.isEmpty(title)) tv_title.setText(title);
        if (button != null && button != 0) {
            iv_button.setVisibility(VISIBLE);
            iv_button.setImageResource(button);
        }
        if (!TextUtils.isEmpty(textBar)) {
            tv_button.setText(textBar);
            tv_button.setTextColor(textColor);
            tv_button.setVisibility(VISIBLE);
        }
        if (!back) iv_back.setVisibility(GONE);
    }
}

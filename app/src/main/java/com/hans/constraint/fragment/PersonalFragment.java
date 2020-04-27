package com.hans.constraint.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.palette.graphics.Palette;

import com.hans.constraint.R;
import com.hans.constraint.base.BaseFragment;

import butterknife.BindView;

/**
 * @创建者 xu
 * @创建时间 2020/4/26
 * @描述 个人中心
 */
public class PersonalFragment extends BaseFragment {


    @BindView(R.id.tv_content)
    TextView tvContent;
    private String title;

    public PersonalFragment(String par) {
        Bundle args = new Bundle();
        args.putString("title", par);
        setArguments(args);
    }

    @Override
    protected int getResId() {
        return R.layout.fragment_personal;
    }

    @Override
    protected void initExtra(Bundle arguments) {
        if (arguments != null) {
            title = arguments.getString("title");
        }
    }

    @Override
    protected void doWork() {
        tvContent.setText(title);
    }
}

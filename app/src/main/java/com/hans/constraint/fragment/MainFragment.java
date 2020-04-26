package com.hans.constraint.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.hans.constraint.R;
import com.hans.constraint.base.BaseFragment;

import butterknife.BindView;

/**
 * @创建者 xu
 * @创建时间 2020/4/26
 * @描述
 */
public class MainFragment extends BaseFragment {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    private String title;

    public MainFragment(String par) {
        Bundle args = new Bundle();
        args.putString("title", par);
        setArguments(args);
    }

    @Override
    protected int getResId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initExtra(Bundle arguments) {
        if (arguments != null) {
            title = arguments.getString("title");
        }
    }

    @Override
    protected void doWork() {
        tvTitle.setText(title);
    }
}

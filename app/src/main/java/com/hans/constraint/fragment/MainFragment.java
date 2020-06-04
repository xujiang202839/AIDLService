package com.hans.constraint.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.blankj.utilcode.util.ToastUtils;
import com.hans.constraint.R;
import com.hans.constraint.activity.AnimationActivity;
import com.hans.constraint.activity.FileInputActivity;
import com.hans.constraint.activity.HandlerActivity;
import com.hans.constraint.activity.ThreadActivity;
import com.hans.constraint.activity.WindowStatusActivity;
import com.hans.constraint.adapter.baselistadapter.HelperAdapter;
import com.hans.constraint.adapter.baselistadapter.HelperViewHolder;
import com.hans.constraint.base.BaseFragment;
import com.hans.constraint.bean.MainItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @创建者 xu
 * @创建时间 2020/4/26
 * @描述
 */
public class MainFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    @BindView(R.id.grid_view)
    GridView gridView;

    private String title;
    private List<MainItem> itemList = new ArrayList<>();

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
        //开发测试分支
        //分支
        if (arguments != null) {
            title = arguments.getString("title");
        }
    }

    @Override
    protected void doWork() {
        itemList.add(new MainItem(getContext().getResources().getString(R.string.fragment_two), WindowStatusActivity.class));
        itemList.add(new MainItem("动画", AnimationActivity.class));
        itemList.add(new MainItem("Handler", HandlerActivity.class));
        itemList.add(new MainItem("thread", ThreadActivity.class));
        itemList.add(new MainItem("文件输入输出", FileInputActivity.class));
        gridView.setAdapter(new HelperAdapter<MainItem>(getContext(), itemList, R.layout.item_mian_grid) {
            @Override
            public void HelpConvert(HelperViewHolder viewHolder, int position, MainItem mainItem) {
                viewHolder.setText(R.id.tv_title, mainItem.getName());
            }
        });
        gridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Class c = itemList.get(position).getAClass();
        if (c != null) {
            Intent intent = new Intent(getContext(), c);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.rotate_up,R.anim.rotate_down);
        }
    }
}

package com.hans.constraint;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.FragmentUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hans.constraint.base.BaseActivity;
import com.hans.constraint.fragment.MainFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @创建者 xu
 * @创建时间 2020/4/24
 * @描述 首页底部导航栏
 */
public class BottomNavActivity extends BaseActivity {

    @BindView(R.id.fragmentNav)
    BottomNavigationView fragmentNav;

    //当前显示
    private int currIndex = 0;

    //fragment集合
    private List<Fragment> mFragments = new ArrayList<>();

    @Override
    protected int getResId() {
        return R.layout.activity_bottom_nav;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            currIndex = savedInstanceState.getInt("currIndex");
        }
        //清空，防止重复添加
        mFragments.clear();
        //底部点击监听
        fragmentNav.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        //添加
        mFragments.add(new MainFragment("首页"));
        mFragments.add(new MainFragment("View"));
        mFragments.add(new MainFragment("自定义"));
        mFragments.add(new MainFragment("我的"));
        FragmentUtils.add(getSupportFragmentManager(), mFragments, R.id.fragmentContainer, currIndex);
        switch (currIndex) {
            case 1:
                fragmentNav.setSelectedItemId(R.id.fragmentNavigation1);
                break;
            case 2:
                fragmentNav.setSelectedItemId(R.id.fragmentNavigation2);
                break;
            case 3:
                fragmentNav.setSelectedItemId(R.id.fragmentNavigation3);
                break;
            default:
                fragmentNav.setSelectedItemId(R.id.fragmentNavigation0);
                break;
        }
    }

    @Override
    protected void initStatus(int transparent, boolean isLight) {
        super.initStatus(R.color.grey_1, true);
    }

    BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            int itemId = menuItem.getItemId();
            if (itemId == R.id.fragmentNavigation0) {
                showCurrentFragment(0);
                return true;
            }
            if (itemId == R.id.fragmentNavigation1) {
                showCurrentFragment(1);
                return true;
            }
            if (itemId == R.id.fragmentNavigation2) {
                showCurrentFragment(2);
                return true;
            }
            if (itemId == R.id.fragmentNavigation3) {
                showCurrentFragment(3);
                return true;
            }
            return false;
        }
    };

    private void showCurrentFragment(int index) {
        currIndex = index;
        FragmentUtils.showHide(index, mFragments);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt("currIndex", currIndex);
    }

    @Override
    public void onBackPressed() {
        if (!FragmentUtils.dispatchBackPress(mFragments.get(currIndex))) {
            super.onBackPressed();
        }
    }
}

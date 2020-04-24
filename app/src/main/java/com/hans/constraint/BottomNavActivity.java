package com.hans.constraint;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hans.constraint.base.BaseActivity;

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

    private int currIndex = 0;
    private List<Fragment> mFragments;

    @Override
    protected int getResId() {
        return R.layout.activity_bottom_nav;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            currIndex = savedInstanceState.getInt("currIndex");
        }
        fragmentNav.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

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
    }
}

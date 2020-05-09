package com.hans.constraint.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.palette.graphics.Palette;

import com.hans.constraint.R;
import com.hans.constraint.View.FlowViewGroup;
import com.hans.constraint.adapter.MyTagAdapter;
import com.hans.constraint.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class WindowStatusActivity extends BaseActivity {


    @BindView(R.id.bt_color)
    Button btColor;
    @BindView(R.id.group)
    FlowViewGroup group;

    @Override
    protected int getResId() {
        return R.layout.activity_window_status;
    }

    @Override
    protected void doWork() {
        MyTagAdapter myTagAdapter = new MyTagAdapter(this);
        group.setAdapter(myTagAdapter);
        List<String> list = new ArrayList<>();
        list.add("上班族");
        list.add("程序员");
        list.add("喜欢美食");
        list.add("懒得健身");
        list.add("没事就喜欢LOL");
        list.add("宅");
        list.add("美女");
        list.add("帅哥");
        list.add("尸兄");
        list.add("不修");
        list.add("德玛西亚");
        myTagAdapter.setData(list);
        myTagAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.bt_color)
    public void onViewClicked() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.open_record_finger);
        Palette.Builder builder = Palette.from(bitmap);
        builder.generate(new Palette.PaletteAsyncListener() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onGenerated(@Nullable Palette palette) {
                Palette.Swatch vibrant = palette.getVibrantSwatch();
                if (vibrant != null) {
                    for (Palette.Swatch swatch : palette.getSwatches()) {
                        vibrant = swatch;
                        break;
                    }

                    int rgb = vibrant.getRgb();
                    int color = changeColor(rgb);
                    // 将颜色设置给相应的组件
                    //getActivity().getActionBar().setBackgroundDrawable(new ColorDrawable(rgb));
                    btColor.setText(color + "");
                    getWindow().setStatusBarColor(getResources().getColor(R.color.blue_00A4BF));
                }
            }
        });
    }


    // 对获取到的RGB颜色进行修改。
    private int changeColor(int rgb) {
        int red = rgb >> 16 & 0xFF;
        int green = rgb >> 8 & 0xFF;
        int blue = rgb & 0xFF;
        red = (int) Math.floor(red * (1 - 0.2));
        green = (int) Math.floor(green * (1 - 0.2));
        blue = (int) Math.floor(blue * (1 - 0.2));
        return Color.rgb(red, green, blue);
    }
}

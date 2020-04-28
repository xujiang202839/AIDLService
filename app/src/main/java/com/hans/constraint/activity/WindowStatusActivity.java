package com.hans.constraint.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.palette.graphics.Palette;

import com.hans.constraint.R;
import com.hans.constraint.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WindowStatusActivity extends BaseActivity {


    @BindView(R.id.bt_color)
    Button btColor;

    @Override
    protected int getResId() {
        return R.layout.activity_window_status;
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

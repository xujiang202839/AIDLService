package com.hans.constraint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private View line_six;
    private View line_one;
    private int sixTop;
    private int oneTop;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (sixTop != 0 && oneTop != 0) {
                        handler.removeCallbacksAndMessages(null);
                        ViewGroup.LayoutParams lp = view_one.getLayoutParams();
                        lp.height = (int) ((oneTop - sixTop) * (563f / 1000f));
                        view_one.setLayoutParams(lp);
                    } else {
                        handler.sendEmptyMessageDelayed(0, 500);
                    }
                    break;
            }
        }
    };
    private View view_one;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraint);
        //  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        line_one = findViewById(R.id.line_one);
        line_six = findViewById(R.id.line_six);
        measureOne();
        view_one = findViewById(R.id.view_one);
        handler.sendEmptyMessageDelayed(0, 500);
    }

    private void measureOne() {
        ViewTreeObserver vto = line_six.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                //防止二次调用
                line_six.getViewTreeObserver().removeOnPreDrawListener(this);
                sixTop = line_six.getTop();
                Log.e("Top11", "=" + sixTop);
                return true;
            }
        });

        ViewTreeObserver vt = line_one.getViewTreeObserver();
        vt.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                //防止二次调用
                line_one.getViewTreeObserver().removeOnPreDrawListener(this);
                oneTop = line_one.getTop();
                Log.e("Top", "=" + oneTop);
                return true;
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
        super.onDestroy();
    }
}

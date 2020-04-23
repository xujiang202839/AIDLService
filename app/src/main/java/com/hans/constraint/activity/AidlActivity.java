package com.hans.constraint.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hans.constraint.R;
import com.hans.constraint.server.AdditionService;
import com.hans.constraint.server.IAdditionService;

/**
*@创建者 xu
*@创建时间 2020/4/23
*@描述 开启本地程序一个service
*/
public class AidlActivity extends AppCompatActivity {

    private Button bt_num;
    private TextView tv_content;

    private IAdditionService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);

        tv_content = findViewById(R.id.tv_content);
        bt_num = findViewById(R.id.bt_num);
        bt_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int add = mService.add(108, 113);
                    tv_content.setText(add + "");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        //注册、绑定service
        Intent intentService = new Intent(this, AdditionService.class);
        bindService(intentService, mConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     * 获取AIDL的service
     */
    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = IAdditionService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    /**
     * 解绑
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mConnection != null) {
            unbindService(mConnection);
        }
    }
}

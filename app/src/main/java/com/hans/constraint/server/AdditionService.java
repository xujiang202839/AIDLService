package com.hans.constraint.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

/**
 * @创建者 xu
 * @创建时间 2020/4/3
 * @描述
 */
public class AdditionService extends Service {

    public AdditionService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    IAdditionService.Stub mBinder = new IAdditionService.Stub() {
        @Override
        public int add(int x, int y) throws RemoteException {
            return x + y;
        }

        @Override
        public int remove(int x, int y) throws RemoteException {
            return x - y;
        }
    };
}

package com.hans.constraint.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hans.constraint.R;
import com.hans.constraint.base.BaseActivity;

import butterknife.BindView;

import static java.lang.Thread.sleep;

public class HandlerActivity extends BaseActivity {

    @BindView(R.id.tv_content)
    TextView tvContent;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 123:
                    tvContent.setText(msg.obj.toString());
                    break;

                case 1:

                    break;
            }
            return false;
        }
    });


    private ProgressDialog mDialog;

    @Override
    protected int getResId() {
        return R.layout.activity_handler;
    }


    @Override
    protected void doWork() {
        // four();
        mDialog = new ProgressDialog(this);
        mDialog.setMax(100);
        mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mDialog.setCancelable(false);

        new MyAsyncTask().execute();

        getResources().getXml(R.xml.backup_descriptor);
    }


    private void four() {
        // new MyAsyncTask().execute("通过AsyncTask方法");
    }


    private class MyAsyncTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            mDialog.show();
            Log.e(TAG, Thread.currentThread().getName() + " onPreExecute ");
        }

        @Override
        protected Void doInBackground(Void... params) {

            // 模拟数据的加载,耗时的任务
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(80);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(i);
            }

            Log.e(TAG, Thread.currentThread().getName() + " doInBackground ");
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mDialog.setProgress(values[0]);
            Log.e(TAG, Thread.currentThread().getName() + " onProgressUpdate ");
        }

        @Override
        protected void onPostExecute(Void result) {
            // 进行数据加载完成后的UI操作
            mDialog.dismiss();
        }
    }

   /* private class MyAsyncTask extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            return objects[0].toString();
        }
        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            tvContent.setText(o.toString());
        }
    }*/

    private void three() {
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 3; i++) {
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                tvContent.post(new Runnable() {
                    @Override
                    public void run() {
                        tvContent.setText("通过View.post(Runnabler) 方法");
                    }
                });
            }
        }.start();
    }

    private void two() {
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 3; i++) {
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvContent.setText("通过runOnUiThread方法");
                    }
                });
            }
        }.start();
    }

    private void one() {
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 3; i++) {
                    try {
                        sleep(3000);

                        Message message = new Message();
                        message.what = 123;
                        message.obj = "通过Handler机制" + i;
                        handler.sendMessage(message);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
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

package com.hans.constraint.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hans.constraint.R;
import com.hans.constraint.base.BaseActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FileInputActivity extends BaseActivity {

    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.btn_write)
    Button btnWrite;
    @BindView(R.id.bth_read)
    Button bthRead;
    @BindView(R.id.btn_clear)
    Button btnClear;

    @Override
    protected int getResId() {
        return R.layout.activity_file_input;
    }

    @OnClick(R.id.btn_write)
    public void onBtnWriteClicked() {

        String strWrite = editText.getText().toString();

        File cacheDir = getApplicationContext().getFilesDir();
        File file = new File(cacheDir, "test.txt");
        Log.e(TAG, "File为" + file);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            Log.e(TAG, "Fos为" + fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fos.write(strWrite.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.bth_read)
    public void onBthReadClicked() {

        String strRead = "";
        StringBuilder sb = new StringBuilder();
        String filePath = getApplicationContext().getFilesDir().toString() + File.separator + "test.txt";
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] buffer = new byte[1024];
        Log.e(TAG, "Buffer为" + buffer);
        try {
            while (fis.read(buffer) != -1) {
                sb.append(new String(buffer));
            }
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        strRead = new String(sb);
        Log.e(TAG, "strRead为" + strRead);
        Toast.makeText(getApplicationContext(), strRead, Toast.LENGTH_LONG).show();

        editText.setText(strRead);
    }

    @OnClick(R.id.btn_clear)
    public void onBtnClearClicked() {
        List list = Arrays.asList("one Two three Four five six one three Four".split(" "));
        System.out.println("List :"+list);
        List sublist = Arrays.asList("three Four".split(" "));
        System.out.println("子列表 :"+sublist);
        System.out.println("indexOfSubList: "
                + Collections.indexOfSubList(list, sublist));
        System.out.println("lastIndexOfSubList: "
                + Collections.lastIndexOfSubList(list, sublist));



        editText.setText("");
    }
}

package com.hans.constraint.bean;

import android.util.Log;

import lombok.Data;

/**
 * @创建者 xu
 * @创建时间 2020/5/25
 * @描述
 */
public class Animal extends  Animals{

    private static final String TAG = Animal.class.getSimpleName();

    private String name;

    private int id;

    Animal(String name, int age) {
        super(name, age);

        this.name = name;
        this.id = age;
    }

   /* Animal(String name, int id) {
        this.name = name;
        this.id = id;
    }*/

    public void eat() {
        Log.d(TAG, name + "正在吃");
    }

    public void sleep() {
        Log.d(TAG, name + "正在睡");
    }

    public void introduction() {
        Log.d(TAG, name + "大家好！我是" + id + "号" + name);
    }
}

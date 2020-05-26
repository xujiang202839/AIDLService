package com.hans.constraint.bean;

import android.util.Log;

/**
 * @创建者 xu
 * @创建时间 2020/5/25
 * @描述
 */
public class Animals {

    private String name;

    private int age;

    Animals(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void show() {
        Log.d("Animals", name + "今年" + age);
    }

}

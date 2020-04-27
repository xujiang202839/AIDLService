package com.hans.constraint.bean;

import lombok.Data;

/**
 * @创建者 xu
 * @创建时间 2020/4/26
 * @描述
 */
@Data
public class MainItem {

    private String name;

    private Class aClass;

    public MainItem(String name, Class aClass) {
        this.name = name;
        this.aClass = aClass;
    }
}

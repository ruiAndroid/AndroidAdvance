package com.rui.advancedemo.model;

/**
 * Time: 2020/6/4
 * Author: jianrui
 * Description:
 */
public class DBTestBean {
    private int age;
    private String name;

    public DBTestBean() {
    }

    public DBTestBean(int age, String name) {
        this.age = age;
        this.name = name;
    }

    @Override
    public String toString() {
        return "DBTestBean{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

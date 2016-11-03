package com.xueyan.personal.java;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/1 0001.
 */

public class User implements Serializable {
    private transient String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

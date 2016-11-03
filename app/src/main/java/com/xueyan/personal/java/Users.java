package com.xueyan.personal.java;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/11/1 0001.
 */

public class Users implements Parcelable {
    private transient String name;
    private int age;

    protected Users(Parcel in) {
        age = in.readInt();
    }

    public static final Creator<Users> CREATOR = new Creator<Users>() {
        @Override
        public Users createFromParcel(Parcel in) {
            return new Users(in);
        }

        @Override
        public Users[] newArray(int size) {
            return new Users[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(age);
    }
}

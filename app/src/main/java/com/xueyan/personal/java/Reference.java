package com.xueyan.personal.java;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2016/11/1 0001.
 */

public class Reference {
    public static void main (String []args){
        String str = "abc";//常量池中的数据
        //1.在堆内存中创建了String 类型，2，在常量池中创建abc
        String string = new String("abc");
        //软引用
        SoftReference<String> softReference = new SoftReference<String>(str);
        //弱引用
        WeakReference<String> weakReference = new WeakReference(str);
        str = null;
        //获取值，通过get();
        String s = softReference.get();
        String s1 = weakReference.get();
        //清除数据
        softReference.clear();
        weakReference.clear();
        System.gc();


    }
}

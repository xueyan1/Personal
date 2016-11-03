package com.xueyan.personal.java;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2016/11/2 0002.
 */

public class ViewUtils {
    public static void inject(Activity activity) {
        try {
            bindView(activity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        bindOnClick(activity);
    }

    private static void bindOnClick(final Activity activity) {
        Class activityClass = activity.getClass();
        Method []declaredMethod = activityClass.getDeclaredMethods();
        for (final Method method:declaredMethod){
            OnClickInject annotation = method.getAnnotation(OnClickInject.class);
            if (annotation!=null){
                int value = annotation.value();
                final View view = activity.findViewById(value);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //通过反射 调用实际的点击方法
                        method.setAccessible(true);
                        try {
                            method.invoke(activity,view);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }

    }

    private static void bindView(Activity activity) throws IllegalAccessException {
        //1.获取activity的字节码
        Class aClass = activity.getClass();
        //获取字节码所有的field
        Field[] fields = aClass.getDeclaredFields();
        //遍历 所有的 fields
        for (Field field :fields){
            ViewInject annotation = field.getAnnotation(ViewInject.class);
            if (annotation!=null){
                int value = annotation.value();
                View viewById = activity.findViewById(value);
                field.setAccessible(true);
                field.set(activity,viewById);

            }else {

            }
        }
    }
}

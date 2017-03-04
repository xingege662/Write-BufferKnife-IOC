package com.ioc.xinchang.inject;

import android.app.Activity;

/**
 * Created by xinchang on 2017/3/4.
 */

public class InjectView {
    public static void bind(Activity activity){
        String className = activity.getClass().getName();
        try {
            Class<?> viewBinderClass = Class.forName(className + "$$ViewBinder");
            ViewBinder viewBinder = (ViewBinder) viewBinderClass.newInstance();
            viewBinder.bind(activity);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

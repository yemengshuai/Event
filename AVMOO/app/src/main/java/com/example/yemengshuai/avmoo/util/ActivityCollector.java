package com.example.yemengshuai.avmoo.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yemengshuai on 2016/9/18.
 */
public class ActivityCollector  {
    private static List<Activity> activities=new ArrayList<>();

    public static  boolean addActivity(Activity act){
        return activities.add(act);
    }
    public static boolean removeActivity(Activity act){
        return activities.remove(act);
    }

    public static void finishAll(){
        if (activities.isEmpty()){
            return;
        }
        for (Activity activity:activities){
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}

package com.example.yemengshuai.avmoo.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by yemengshuai on 2016/8/31.
 */
public class NetUtil {

    public static boolean isOnline(Context context){
        boolean wifiConnected=isWifiConnected(context);
        boolean mobileConnected=isMobileConnected(context);
        if (wifiConnected==false&&mobileConnected==false){
            return false;
        }
        return true;

    }

    public static boolean isWifiConnected(Context context){
        ConnectivityManager cm=((ConnectivityManager) context.getApplicationContext().
            getSystemService(Context.CONNECTIVITY_SERVICE));
        NetworkInfo info=cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (null!=info&&info.isConnected()){
            return true;
        }return false;
    }
    public static boolean isMobileConnected(Context context){
        ConnectivityManager cm=((ConnectivityManager) context.getApplicationContext().
                getSystemService(Context.CONNECTIVITY_SERVICE));
        NetworkInfo info=cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (null !=info&&info.isConnected()){
            return true;

        }
        return false;
    }
}

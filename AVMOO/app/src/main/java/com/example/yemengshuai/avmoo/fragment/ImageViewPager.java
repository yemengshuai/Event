package com.example.yemengshuai.avmoo.fragment;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by yemengshuai on 2016/9/23.
 */
public class ImageViewPager extends ViewPager{
    private static final String TAG="ImageViewPage";
    public ImageViewPager (Context context){
        super(context);
    }
    public ImageViewPager(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev){
        try {
            return super.onInterceptTouchEvent(ev);
        }catch (IllegalArgumentException e){
            Log.e(TAG,"Image viewpager error1");
            return false;
        }catch (ArrayIndexOutOfBoundsException e){
            Log.e(TAG,"Image viewpager error2");
            return false;
        }
    }

}

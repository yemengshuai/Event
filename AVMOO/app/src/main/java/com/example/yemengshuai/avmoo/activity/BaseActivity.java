package com.example.yemengshuai.avmoo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.yemengshuai.avmoo.util.ActivityCollector;

/**
 * Created by yemengshuai on 2016/9/18.
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);

    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}

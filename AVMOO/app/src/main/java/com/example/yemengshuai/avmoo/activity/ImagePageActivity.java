package com.example.yemengshuai.avmoo.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.example.yemengshuai.avmoo.R;
import com.example.yemengshuai.avmoo.fragment.ImageDetailFragment;

import java.util.List;

/**
 * Created by yemengshuai on 2016/9/22.
 */
public class ImagePageActivity extends FragmentActivity{
    private static final String STATE_POSITION = "STATE_POSITION";
    public static final String EXTRA_IMAGE_INDEX = "image_index";
    public static final String EXTRA_IMAGE_URLS = "image_urls";

    private int pagerPosition;
    private TextView indicator;
    private ViewPager mPage;



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_detail);
        pagerPosition=getIntent().getIntExtra(EXTRA_IMAGE_INDEX,0);
        final List<String> urls=(List<String>)getIntent().getSerializableExtra(EXTRA_IMAGE_URLS);
        mPage=(ViewPager)findViewById(R.id.page);
        ImagePageAdapter mAdapter=new ImagePageAdapter(getSupportFragmentManager(),urls);
        indicator=(TextView)findViewById(R.id.indicator);
        CharSequence text=getString(R.string.viewpager_indicator,1,urls.size());
        indicator.setText(text);
        mPage.setAdapter(mAdapter);


        mPage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                CharSequence text=getString(R.string.viewpager_indicator,position+1,urls.size());
                indicator.setText(text);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        if (savedInstanceState!=null){
            pagerPosition=savedInstanceState.getInt(STATE_POSITION);
        }
        mPage.setCurrentItem(pagerPosition);
    }
    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putInt(STATE_POSITION,mPage.getCurrentItem());
    }

    private class ImagePageAdapter extends FragmentStatePagerAdapter{
        public List<String> filesList;
        public ImagePageAdapter(FragmentManager fm,List<String> filesList){
            super(fm);
            this.filesList=filesList;
        }

        @Override
        public int getCount(){
            return filesList.size();
        }

        @Override
        public Fragment getItem(int position){
            String url=filesList.get(position);
            return ImageDetailFragment.newInstance(url);

        }
    }
}

package com.example.yemengshuai.avmoo.activity;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;


import com.example.yemengshuai.avmoo.R;

import com.example.yemengshuai.avmoo.fragment.MainFragment;






/**
 * Created by yemengshuai on 2016/8/18.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener{

    private TabLayout mTab;
    private ViewPager mViewpage;
    private FragmentPagerAdapter mAdapter;
    private String[] titles;
    private Toolbar mToolbar;
    PopupWindow mPopupWindow;
    private String link;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTab=(TabLayout)findViewById(R.id.id_tablayout);
        mViewpage=(ViewPager)findViewById(R.id.id_viewpager);
        mToolbar=(Toolbar)findViewById(R.id.toolbar);
        mToolbar.setLogo(R.mipmap.title);
        mContext=this;
        setSupportActionBar(mToolbar);
        initData();
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.Search:
                        onSearchRequested();
                        break;
                    case R.id.action_overflow:
                        //弹出自定义overflow
                        popUpMyOverflow();
                        return true;
                }
                return true;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.mian_menu,menu);
        SearchManager searchManager=(SearchManager)getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView=(SearchView)menu.findItem(R.id.Search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String a="https://avmo.pw/cn/search/";
                link=a+query;
                Start_Product.actionStart(mContext,link);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }


    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.Starts:
                Intent intent=new Intent(this,StartActivity.class);
                this.startActivity(intent);
                break;
            case R.id.Product_style:
                break;
        }
    }

    public void popUpMyOverflow() {
        //获取状态栏高度
        Rect frame = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        //状态栏高度+toolbar的高度
        int yOffset = frame.top + mToolbar.getHeight();
        if (null == mPopupWindow) {
            //初始化PopupWindow的布局
            View popView = getLayoutInflater().inflate(R.layout.action_overflow_popwindow, null);
            //popView即popupWindow的布局，ture设置focusAble.
            mPopupWindow = new PopupWindow(popView,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, true);
            //必须设置BackgroundDrawable后setOutsideTouchable(true)才会有效
            mPopupWindow.setBackgroundDrawable(new ColorDrawable());
            //点击外部关闭。
            mPopupWindow.setOutsideTouchable(true);
            //设置一个动画。
            mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
            //设置Gravity，让它显示在右上角。
            mPopupWindow.showAtLocation(mToolbar, Gravity.RIGHT | Gravity.TOP, 0, yOffset);
            //设置item的点击监听
            popView.findViewById(R.id.Starts).setOnClickListener(this);
            popView.findViewById(R.id.Product_style).setOnClickListener(this);
        } else {
            mPopupWindow.showAtLocation(mToolbar, Gravity.RIGHT | Gravity.TOP, 0, yOffset);
        }
    }


    private void initData(){
        titles=new String[]{"已发布","全部","热门"
        };
        mAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return MainFragment.newInstance(position);
            }

            @Override
            public int getCount() {
                return titles.length;
            }
            @Override
            public CharSequence getPageTitle(int position){
                return titles[position%titles.length];
            }

        };
        mViewpage.setAdapter(mAdapter);
        mTab.setupWithViewPager(mViewpage);
    }
    protected void onDestroy(){
        super.onDestroy();

    }


}

package com.example.yemengshuai.avmoo.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.example.yemengshuai.avmoo.R;
import com.example.yemengshuai.avmoo.adapter.StartsAdapter;

import com.example.yemengshuai.avmoo.bean.CommonException;

import com.example.yemengshuai.avmoo.bean.StartsItem;
import com.example.yemengshuai.avmoo.biz.StartItemBiz;
import com.example.yemengshuai.avmoo.util.NetUtil;

import java.util.List;

/**
 * Created by yemengshuai on 2016/9/24.
 */
public class StartActivity extends BaseActivity {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private Context mContext;
    private StaggeredGridLayoutManager mManger;

    public static final int LOAD_REFRESH = 0x01;
    public static final int LOAD_MORE = 0x02;

    public static final String TIP_ERROR_NO_NETWORK = "没有网络连接";
    public static final String TIP_ERROR_NO_SERVICE = "服务器错误";


    private int curpage=1;

    private StartItemBiz mStartItemBiz;

    private StartsAdapter mAdapter;

    private boolean isLoadFromService;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starts_list);
        mContext=this;
        mStartItemBiz=new StartItemBiz();
        initView();
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
        initData();
        initEvent();
        new StartDataTask().execute(LOAD_REFRESH);

    }

    private void initView(){
        mRecyclerView=(RecyclerView)findViewById(R.id.start_recycleview);
        mSwipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.start_swiperefreshlayout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,R.color.colorPrimary,
                R.color.colorPrimary,R.color.colorPrimary);
        mManger=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mManger);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }
    private void initEvent(){
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new StartDataTask().execute(LOAD_REFRESH);
            }

        });
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int[] lastItem=mManger.findLastVisibleItemPositions(null);
                int last=Math.max(lastItem[1],lastItem[0]);
                last=Math.max(last,lastItem[2]);
                if(newState==RecyclerView.SCROLL_STATE_IDLE&&last+1==mAdapter.getItemCount()&&mAdapter.getItemCount()>1){
                    new StartDataTask().execute(LOAD_MORE);
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView,int dx,int dy){
                super.onScrolled(recyclerView,dx,dy);
            }
        });
        mAdapter.setOnItemClickLitener(new StartsAdapter.OnItemClickLitener(){
            @Override
            public void onItemClick(View view, int position){
                StartsItem startItem=mAdapter.getDatas().get(position);
                Start_Product.actionStart(mContext,startItem.getStart_detail());
            }

            @Override
            public void onItemLongClick(View view,int position){

            }
        });


    }
    class StartDataTask extends AsyncTask<Integer,Void,String> {

        @Override
        protected String doInBackground(Integer... params) {
            switch (params[0]) {
                case LOAD_REFRESH:
                    return refreshData();
                case LOAD_MORE:
                    return loadMoreData();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            if (null == result) {
                mAdapter.setIsLoading(false);
                mAdapter.setmError(null);
            } else {
                mAdapter.setIsLoading(true);
                mAdapter.setmError(result);
                Snackbar.make(mSwipeRefreshLayout, result, Snackbar.LENGTH_LONG).show();
            }
            mSwipeRefreshLayout.setRefreshing(false);
            mAdapter.notifyDataSetChanged();
        }

    }
    private String refreshData(){
        if (NetUtil.isOnline(mContext)){
            curpage=1;
            try {
                List<StartsItem> items=mStartItemBiz.getStartsItem(curpage);
                if (!items.isEmpty()){
                    mAdapter.setDatas(items);
                    //建立数据库
                }
                isLoadFromService=true;
            }catch (CommonException e){
                e.printStackTrace();
                isLoadFromService=false;
                return TIP_ERROR_NO_SERVICE;
            }
        }else {
            //提取数据库里的信息
            return TIP_ERROR_NO_NETWORK;
        }
        return null;
    }
    private String loadMoreData(){
        mAdapter.setIsLoading(true);
        if (isLoadFromService){
            curpage++;
            try{
                List<StartsItem> items=mStartItemBiz.getStartsItem(curpage);
                mAdapter.addData(items);
                //数据库更新
            }catch (CommonException e){
                e.printStackTrace();
                return e.getMessage();
            }
        }else {
            curpage++;
            //提取数据库资源
            return TIP_ERROR_NO_NETWORK;
        }
        return null;
    }

    private void initData(){

        mAdapter=new StartsAdapter(mContext);
        mRecyclerView.setAdapter(mAdapter);
    }

}

package com.example.yemengshuai.avmoo.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yemengshuai.avmoo.R;
import com.example.yemengshuai.avmoo.activity.Product_activity;
import com.example.yemengshuai.avmoo.adapter.startAdapter;
import com.example.yemengshuai.avmoo.bean.CommonException;
import com.example.yemengshuai.avmoo.bean.StartItem;
import com.example.yemengshuai.avmoo.biz.StartItemBiz;
import com.example.yemengshuai.avmoo.util.Constant;
import com.example.yemengshuai.avmoo.util.NetUtil;

import java.util.List;

/**
 * Created by yemengshuai on 2016/9/19.
 */
public class MainFragment extends Fragment{
    public static final int LOAD_REFRESH = 0x01;
    public static final int LOAD_MORE = 0x02;

    public static final String TIP_ERROR_NO_NETWORK = "没有网络连接";
    public static final String TIP_ERROR_NO_SERVICE = "服务器错误";

    public static final String NEW_TYPE="NEW_TYPE";

    private Context mContext;

    private int newsType=Constant.AVMOO_TYPE_NEW;

    private int curpage=1;

    private StartItemBiz mStartItemBiz;

    private SwipeRefreshLayout mSwipeRefresh;
    private startAdapter mAdapter;
    private StaggeredGridLayoutManager mManager;
    private RecyclerView mRecyclerView;



    private boolean isLoadFromService;

    public static MainFragment newInstance(int pos){
        Bundle args=new Bundle();
        args.putInt(NEW_TYPE,pos);
        MainFragment fragment=new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_main,container,false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        mContext=getActivity();
        mStartItemBiz=new StartItemBiz();
        initView();
        mSwipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefresh.setRefreshing(true);
            }
        });
        initData();
        initEvent();
        new DownLoadTask().execute(LOAD_REFRESH);
    }



    private void initView(){
        mSwipeRefresh=(SwipeRefreshLayout)getView().findViewById(R.id.id_swiperefresh);
        mSwipeRefresh.setColorSchemeResources(R.color.colorPrimary,R.color.colorPrimary,
                R.color.colorPrimary,R.color.colorPrimary);
        mRecyclerView=(RecyclerView)getView().findViewById(R.id.id_recycleview);
        mManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    private void initEvent(){
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new DownLoadTask().execute(LOAD_REFRESH);
            }
        });
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int[] lastItem=mManager.findLastVisibleItemPositions(null);
                int last=Math.max(lastItem[1],lastItem[0]);
                if(newState==RecyclerView.SCROLL_STATE_IDLE&&last+1==mAdapter.getItemCount()&&mAdapter.getItemCount()>1){
                    new DownLoadTask().execute(LOAD_MORE);
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView,int dx,int dy){
                super.onScrolled(recyclerView,dx,dy);
            }
        });
        mAdapter.setOnItemClickLitener(new startAdapter.OnItemClickLitener(){
            @Override
            public void onItemClick(View view,int position){
                StartItem startItem=mAdapter.getDatas().get(position);
                Product_activity.actionStart(mContext,startItem.getLink());
            }

            @Override
            public void onItemLongClick(View view,int position){

            }
        });
    }
    private void initData(){
        Bundle bundle=getArguments();
        newsType=bundle.getInt(NEW_TYPE,Constant.AVMOO_TYPE_NEW);
        mAdapter=new startAdapter(mContext);
        mRecyclerView.setAdapter(mAdapter);
    }


    class DownLoadTask extends AsyncTask<Integer,Void,String> {

        @Override
        protected String doInBackground(Integer... params){
            switch (params[0]){
                case LOAD_REFRESH:
                    return refreshData();
                case LOAD_MORE:
                    return loadMoreData();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result){
            if (null==result){
                mAdapter.setIsLoading(false);
                mAdapter.setmError(null);
            }else {
                mAdapter.setIsLoading(true);
                mAdapter.setmError(result);
                Snackbar.make(mSwipeRefresh,result,Snackbar.LENGTH_LONG).show();
            }
            mSwipeRefresh.setRefreshing(false);
            mAdapter.notifyDataSetChanged();
        }


    }

    private String refreshData(){
        if (NetUtil.isOnline(mContext)){
            curpage=1;
            try {
                List<StartItem> items=mStartItemBiz.getStartItem(newsType,curpage);
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
                List<StartItem> items=mStartItemBiz.getStartItem(newsType,curpage);
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
}

package com.example.yemengshuai.avmoo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import android.widget.ImageButton;
import android.widget.TextView;

import com.example.yemengshuai.avmoo.R;
import com.example.yemengshuai.avmoo.adapter.startAdapter;
import com.example.yemengshuai.avmoo.bean.CommonException;
import com.example.yemengshuai.avmoo.bean.StartItem;
import com.example.yemengshuai.avmoo.biz.StartItemBiz;
import com.example.yemengshuai.avmoo.util.NetUtil;

import java.util.List;

/**
 * Created by yemengshuai on 2016/9/26.
 */
public class Start_Product extends BaseActivity{

    public static final int LOAD_REFRESH = 0x01;
    public static final int LOAD_MORE = 0x02;

    public static final String TIP_ERROR_NO_NETWORK = "没有网络连接";
    public static final String TIP_ERROR_NO_SERVICE = "服务器错误";

    public static final String NEW_TYPE="NEW_TYPE";
    private TextView Startname;
    private SwipeRefreshLayout swipeRefreshLayout;
    private StartItemBiz startItemBiz;
    private String link;
    private Context mContext;
    private ImageButton mBack;
    private RecyclerView recyclerView;
    private startAdapter mAdapter;
    private StaggeredGridLayoutManager mManager;
    int curpage=1;

    private boolean isLoadFromService;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_protect);
        mContext=this;
        startItemBiz=new StartItemBiz();
        initView();
        link=getIntent().getStringExtra("link");
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        initData();
        initEvent();
        new Start_ProductTask().execute(LOAD_REFRESH);
    }

    private void initView(){
        Startname=(TextView)findViewById(R.id.start_product_name);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.id_product_refresh);
        mBack=(ImageButton)findViewById(R.id.id_start_prdoct_back);
        recyclerView=(RecyclerView) findViewById(R.id.Recycleview_Start_Product);
        mManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,R.color.colorPrimary,
                R.color.colorPrimary,R.color.colorPrimary);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initEvent() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Start_ProductTask().execute(LOAD_REFRESH);
            }
        });
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int[] lastItem = mManager.findLastVisibleItemPositions(null);
                int last = Math.max(lastItem[1], lastItem[0]);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && last + 1 == mAdapter.getItemCount() && mAdapter.getItemCount() > 1) {
                    new Start_ProductTask().execute(LOAD_MORE);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
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
        mAdapter=new startAdapter(mContext);
        recyclerView.setAdapter(mAdapter);

    }

    class Start_ProductTask extends AsyncTask<Integer,Void,String>{
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
                Snackbar.make(swipeRefreshLayout,result,Snackbar.LENGTH_LONG).show();
            }
            swipeRefreshLayout.setRefreshing(false);
            mAdapter.notifyDataSetChanged();
        }
    }


    private String refreshData(){
        if (NetUtil.isOnline(mContext)){
            curpage=1;
            try {
                List<StartItem> items=startItemBiz.getStartItemS(link,curpage);
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
                List<StartItem> items=startItemBiz.getStartItemS(link,curpage);
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




    public static void actionStart(Context context, String url){
        Intent intent=new Intent(context,Start_Product.class);
        intent.putExtra("link",url);
        context.startActivity(intent);
    }
}

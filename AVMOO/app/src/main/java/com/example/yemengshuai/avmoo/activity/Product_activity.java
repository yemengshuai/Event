package com.example.yemengshuai.avmoo.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yemengshuai.avmoo.R;
import com.example.yemengshuai.avmoo.adapter.ImageAdapter;
import com.example.yemengshuai.avmoo.bean.CommonException;
import com.example.yemengshuai.avmoo.bean.ProductDetail;
import com.example.yemengshuai.avmoo.biz.StartItemBiz;
import com.example.yemengshuai.avmoo.util.DataUtil;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yemengshuai on 2016/8/22.
 */
public class Product_activity extends BaseActivity {

    private ImageView Product_Cover;
    private SwipeRefreshLayout mRefresh;
    private String link;
    private StartItemBiz startItemBiz;
    private TextView product_id;
    private TextView product_time;
    private TextView product_long;
    private TextView product_company1;
    private TextView product_company2;
    private TextView product_series;
    private ImageButton mBack;
    private Context mContext;
    private List<String> Image_URL;
    private GridView gridView;
    private ImageAdapter adapter;
    private TextView product_name;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details);
        mContext=this;
        initView();
        link=getIntent().getStringExtra("link");
        startItemBiz=new StartItemBiz();
        mRefresh.post(new Runnable() {
            @Override
            public void run() {
                mRefresh.setRefreshing(true);
            }
        });
        new LoadDataTask().execute();



    }
    public static void actionStart(Context context,String url){
        Intent intent=new Intent(context,Product_activity.class);
        intent.putExtra("link",url);
        context.startActivity(intent);
    }
    private void initView(){
        mRefresh=(SwipeRefreshLayout)findViewById(R.id.id_productinfo_refresh);
        mBack=(ImageButton)findViewById(R.id.id_imb_back);
        Product_Cover=(ImageView)findViewById(R.id.product_Cover);
        product_id=(TextView)findViewById(R.id.product_Id);
        product_time=(TextView)findViewById(R.id.product_Time);
        product_long=(TextView)findViewById(R.id.product_Long);
        product_company1=(TextView)findViewById(R.id.product_Company1);
        product_company2=(TextView)findViewById(R.id.product_Company2);
        product_series=(TextView)findViewById(R.id.product_Series);
        product_name=(TextView)findViewById(R.id.product_name);
        gridView=(GridView)findViewById(R.id.Grid_Image);   //Image

        mRefresh.setColorSchemeResources(R.color.colorPrimary,R.color.colorPrimary,
                R.color.colorPrimary,R.color.colorPrimary);

        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new LoadDataTask().execute();
            }
        });
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
    class LoadDataTask extends AsyncTask<Void,Void,String>{
        @Override
        protected String doInBackground(Void... params){
            String html=null;
            try {
                html= DataUtil.doGet(link);
            }catch (CommonException e){
                e.printStackTrace();
            }
            return html;
        }
        @Override
        protected void onPostExecute(String s){
            if (!TextUtils.isEmpty(s)){
                ProductDetail mProduct=startItemBiz.getProductDetial(s);
                final List<String> Image_URL=startItemBiz.getImageUrl(s);//Image
                product_id.setText(mProduct.getProduct_id());
                product_time.setText(mProduct.getProduct_time());
                product_long.setText(mProduct.getProduct_long());
                product_company1.setText(mProduct.getProduct_company1());
                product_company2.setText(mProduct.getProduct_company2());
                product_series.setText(mProduct.getProduct_series());
                product_name.setText(mProduct.getTitle());
                Picasso.with(mContext).load(mProduct.getProductcover_url()).into(Product_Cover);
                adapter=new ImageAdapter(mContext,Image_URL);
                gridView.setAdapter(adapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent=new Intent(mContext,ImagePageActivity.class);
                        intent.putExtra(ImagePageActivity.EXTRA_IMAGE_URLS,(Serializable)Image_URL);
                        intent.putExtra(ImagePageActivity.EXTRA_IMAGE_INDEX,position);
                        mContext.startActivity(intent);
                    }
                });
            }
        }
    }





}

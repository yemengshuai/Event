package com.example.yemengshuai.avmoo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yemengshuai.avmoo.R;
import com.example.yemengshuai.avmoo.bean.StartItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yemengshuai on 2016/8/19.
 */
public class startAdapter extends RecyclerView.Adapter{

    private final int TYPE_NORMAL=0;
    private final int TYPE_FOOT=1;
    private Context mContext;
    private List<StartItem> mStart=new ArrayList<>();
    private String mError=null;
    private boolean isLoading;
    private OnItemClickLitener mOnItemClickLitener;




    public startAdapter(Context context){
        this.mContext=context;

    }



    public void setIsLoading(boolean isLoading){this.isLoading=isLoading;}

    public void addData(List<StartItem> datas){mStart.addAll(datas);}

    public void setDatas(List<StartItem> datas){
        mStart.clear();
        mStart.addAll(datas);
    }

    public List<StartItem> getDatas(){
        return mStart;
    }

    public String getmError() {
        return mError;
    }

    public void setmError(String mError) {
        this.mError = mError;
    }

    @Override
    public int getItemViewType(int position){
        if (position+1==getItemCount()){
            return TYPE_FOOT;
        }
        return TYPE_NORMAL;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        if (viewType==TYPE_NORMAL){
            View view =LayoutInflater.from(mContext).inflate(R.layout.productitem,parent,false);
            return new StartViewHolder(view);
        }
        if (viewType==TYPE_FOOT){
            View view=LayoutInflater.from(mContext).inflate(R.layout.item_foot,parent,false);
            return new FootHolder(view);
        }
        return null;
    }

     class StartViewHolder extends RecyclerView.ViewHolder{
        ImageView productImage;
        TextView productTitle;
         TextView productId;
         TextView productData;
        public StartViewHolder(View startview){
            super(startview);
            productImage=(ImageView)startview.findViewById(R.id.product_image);
            productTitle=(TextView)startview.findViewById(R.id.product_title);
            productId=(TextView)startview.findViewById(R.id.product_id);
            productData=(TextView)startview.findViewById(R.id.product_data);
        }

    }

    class FootHolder extends RecyclerView.ViewHolder{
        LinearLayout foot;
        TextView message;
        public FootHolder(View itemView){
            super(itemView);
            foot=(LinearLayout)itemView.findViewById(R.id.item_news_foot);
            message=(TextView)itemView.findViewById(R.id.item_news_message);
        }
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position){

        if (holder instanceof StartViewHolder){
            ((StartViewHolder)holder).productTitle.setText(mStart.get(position).getTitle());
            ((StartViewHolder)holder).productData.setText(mStart.get(position).getProduct_data());
            ((StartViewHolder)holder).productId.setText(mStart.get(position).getProduct_id());
            Picasso.with(mContext).load(mStart.get(position).getImgLink()).into(((StartViewHolder)holder).productImage);
            if (mOnItemClickLitener!=null){
                holder.itemView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        int pos=holder.getLayoutPosition();
                        mOnItemClickLitener.onItemClick(holder.itemView,pos);
                    }
                });
            }
        }
        if (holder instanceof FootHolder){
            ((FootHolder)holder).foot.setVisibility(isLoading?View.VISIBLE:View.GONE);
            if (mError!=null){
                ((FootHolder)holder).message.setText(mError);
            }else {
                ((FootHolder)holder).message.setText("加载中....");
            }

        }
    }

    @Override
    public int getItemCount(){
        return mStart.size()+1;

    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener){
        this.mOnItemClickLitener=mOnItemClickLitener;
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);

    }








}

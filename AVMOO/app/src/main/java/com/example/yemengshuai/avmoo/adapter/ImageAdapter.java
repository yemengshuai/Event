package com.example.yemengshuai.avmoo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.yemengshuai.avmoo.R;
import com.example.yemengshuai.avmoo.activity.ImagePageActivity;

import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by yemengshuai on 2016/9/22.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> url_item;

    public ImageAdapter(Context context,List<String> url){
        this.mContext=context;
        this.url_item=url;
    }

    @Override
    public int getCount(){
        return url_item==null?0:url_item.size();
    }

    @Override
    public Object getItem(int position){
        return url_item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ViewHolder holder;
        if (convertView==null){
            holder=new ViewHolder();
            convertView=View.inflate(mContext, R.layout.image_item,null);
            holder.imgv=(ImageView)convertView.findViewById(R.id.Image_item);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder)convertView.getTag();
        }
        Picasso.with(mContext).load(url_item.get(position)).into(holder.imgv);

        return convertView;
    }

    class ViewHolder{
        private ImageView imgv;

    }


}

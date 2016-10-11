package com.example.yemengshuai.avmoo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.yemengshuai.avmoo.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by yemengshuai on 2016/9/23.
 */
public class ImageDetailFragment extends Fragment {
    private String mImageUrl;
    private ImageView mImageView;
    private PhotoViewAttacher mAttacher;
    private ProgressBar progressBar;

    public static ImageDetailFragment newInstance(String imageUrl){
        final ImageDetailFragment f=new ImageDetailFragment();
        final Bundle args=new Bundle();
        args.putString("url",imageUrl);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mImageUrl=getArguments()!=null?getArguments().getString("url"):null;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        final View view=inflater.inflate(R.layout.image_detail_fragement,container,false);
        mImageView=(ImageView)view.findViewById(R.id.image_detail_view);
        mAttacher=new PhotoViewAttacher(mImageView);
        mAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                getActivity().finish();
            }
        });
        progressBar=(ProgressBar)view.findViewById(R.id.loading);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        Picasso.with(getActivity()).load(mImageUrl).into(mImageView);
    }

}

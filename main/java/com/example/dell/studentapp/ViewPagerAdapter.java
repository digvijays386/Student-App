package com.example.dell.studentapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 1/24/2018.
 */

public class ViewPagerAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Student> imgList;
    //private Integer images[] = {R.drawable.vsh, R.drawable.lg, R.drawable.abc, R.drawable.sketch};


    public ViewPagerAdapter(Context context, ArrayList<Student> imgList) {
        this.context = context;
        this.imgList = imgList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return imgList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = null;
        if (layoutInflater != null) {
            view = layoutInflater.inflate(R.layout.card_item, null);
        }
        final Student student = imgList.get(position);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageViewPager);
        byte[] stuImage= student.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(stuImage,0,stuImage.length);
        imageView.setImageBitmap(bitmap);
        //imageView.setImageResource(images[position]);
        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;

        vp.removeView(view);
    }
}
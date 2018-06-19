package com.example.liranyehudar.emojimemorygame;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private int height;
    private int width;
    private int row;
    private int col;
    private Integer[] images;
    private ArrayList<ImageView> imageViews;

    public ImageAdapter(Context c,int height, int width, int row, int col,Integer[] images) {
        mContext = c;
        this.height = height;
        this.width = width;
        this.row = row;
        this.col = col;
        this.images = images;
        this.imageViews = new ArrayList<>();
    }

    public int getCount() {
        return images.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return images[position];
    }

    public ArrayList<ImageView> getImageViews() {
        return imageViews;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(width/row,height/col));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(7, 7, 7, 7);
            imageViews.add(imageView);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(images[position]);
        return imageView;
    }

}

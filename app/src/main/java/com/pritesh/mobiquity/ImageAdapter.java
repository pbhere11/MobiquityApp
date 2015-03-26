package com.pritesh.mobiquity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.pritesh.mobiquity.Models.MediaDetails;

import java.util.ArrayList;

/**
 * Created by admin on 25/03/2015.
 */
public class ImageAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<MediaDetails> mediaDetailsList;

    public ImageAdapter(Context context, ArrayList<MediaDetails> mediaDetailsList) {
        this.context = context;
        this.mediaDetailsList = mediaDetailsList;
    }

    @Override
    public int getCount() {
        return mediaDetailsList.size();
    }

    @Override
    public Object getItem(int position) {
        return mediaDetailsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mediaDetailsList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageBitmap(mediaDetailsList.get(position).getImage());
        return imageView;
    }
}

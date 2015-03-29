package com.pritesh.mobiquity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.pritesh.mobiquity.Models.MediaDetails;
import com.pritesh.mobiquity.R;

import java.util.ArrayList;

/**
 * Created by admin on 29/03/2015.
 */
public class ImageAdapter extends ArrayAdapter<MediaDetails> {
    private Context context;
    private ArrayList<MediaDetails> mediaList;

    public ImageAdapter(Context context, int resource, ArrayList<MediaDetails> mediaList) {
        super(context, R.layout.grid_content_image,mediaList);
        this.context = context;
        this.mediaList = mediaList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("tkt","coming here position = "+position);
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.grid_content_image, parent, false);
        }
        ImageView image = (ImageView) view.findViewById(R.id.imageView);
        MediaDetails mediaDetails = mediaList.get(position);
        image.setImageBitmap(mediaDetails.getImage());
        return view;
    }
}

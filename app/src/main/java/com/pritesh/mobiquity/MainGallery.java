package com.pritesh.mobiquity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ImageView;

import com.pritesh.mobiquity.Models.MediaDetails;

import java.util.ArrayList;


public class MainGallery extends ActionBarActivity {

    private GridView gallery;
    private ImageView cameraButton;
    private ImageView audioButton;
    private ImageView shareButton;
    private ImageView textButton;
    private ArrayList<MediaDetails> mediaDetailsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_gallery);
        mediaDetailsList = new ArrayList<MediaDetails>();
        ImageAdapter imageAdapter = new ImageAdapter(MainGallery.this,mediaDetailsList);
        gallery = (GridView) findViewById(R.id.gridView);
        gallery.setAdapter(imageAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_gallery, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

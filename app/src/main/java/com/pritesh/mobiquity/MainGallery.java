package com.pritesh.mobiquity;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.dropbox.sync.android.DbxAccountManager;
import com.dropbox.sync.android.DbxFile;
import com.dropbox.sync.android.DbxFileSystem;
import com.dropbox.sync.android.DbxPath;
import com.pritesh.mobiquity.Models.MediaDetails;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MainGallery extends Activity implements LoaderManager.LoaderCallbacks<ArrayList<MediaDetails>> {

    private GridView gallery;
    private ImageView cameraButton;
    private ImageView audioButton;
    private ImageView shareButton;
    private ImageView textButton;
    private ArrayList<MediaDetails> mediaDetailsList;
    private DbxAccountManager dbxAccountManager;
    private ImageAdapter imageAdapter;
    private String mCurrentPhotoPath;
    private static final int REQUEST_LINK_TO_DBX = 0;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private File photoFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_gallery);
        gallery = (GridView) findViewById(R.id.gridView);
        //setting up dropbox account
        dbxAccountManager = DbxAccountManager.getInstance(getApplicationContext(), getResources().getString(R.string.dropbox_api_key), getResources().getString(R.string.dropbox_client_secret));
        if (!dbxAccountManager.hasLinkedAccount()) {
            dbxAccountManager.startLink((Activity) this, REQUEST_LINK_TO_DBX);
        } else {
            getLoaderManager().initLoader(0, null, this);
        }

        cameraButton = (ImageView) findViewById(R.id.cameraImageView);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getPackageManager()) != null) {

                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        Log.d("exception", ex.toString());
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(photoFile));
                        startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_LINK_TO_DBX) {
            if (resultCode == Activity.RESULT_OK) {
                getLoaderManager().initLoader(0, null, this);
            } else {
                Log.d("exception", "Link failed or was cancelled by the user.");
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            UploadFileAsync uploadFileAsync = new UploadFileAsync(getApplicationContext(), dbxAccountManager, imageAdapter);
            uploadFileAsync.execute(photoFile);
            MediaDetails mediaDetails = new MediaDetails();
            mediaDetails.setImage(BitmapFactory.decodeFile(photoFile.getPath()));
            imageAdapter.add(mediaDetails);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "My_Image" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        return image;
    }


    @Override
    public Loader<ArrayList<MediaDetails>> onCreateLoader(int id, Bundle args) {

        MediaLoader mediaLoader = new MediaLoader(MainGallery.this, dbxAccountManager, imageAdapter);
        return mediaLoader;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MediaDetails>> loader, ArrayList<MediaDetails> data) {
        Log.d("tkt", "size = " + data.size());
        imageAdapter = new ImageAdapter(getApplicationContext(), 0, data);
        gallery.setAdapter(imageAdapter);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MediaDetails>> loader) {

    }
}

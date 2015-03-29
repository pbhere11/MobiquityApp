package com.pritesh.mobiquity;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.dropbox.sync.android.DbxAccountManager;
import com.dropbox.sync.android.DbxFile;
import com.dropbox.sync.android.DbxFileInfo;
import com.dropbox.sync.android.DbxFileSystem;
import com.dropbox.sync.android.DbxPath;
import com.pritesh.mobiquity.Models.MediaDetails;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 29/03/2015.
 */
public class MediaLoader extends AsyncTaskLoader<ArrayList<MediaDetails>> {
    private Context context;
    private DbxAccountManager dbxAccountManager;
    private ImageAdapter imageAdapter;

    public MediaLoader(Context context, DbxAccountManager dbxAccountManager, ImageAdapter imageAdapter) {
        super(context);
        this.context = context;
        this.dbxAccountManager = dbxAccountManager;
        this.imageAdapter = imageAdapter;
        forceLoad();
    }

    @Override
    public ArrayList<MediaDetails> loadInBackground() {
        ArrayList<MediaDetails> mediaList = new ArrayList<MediaDetails>();
        try {
            DbxFileSystem dbxFs = DbxFileSystem.forAccount(dbxAccountManager.getLinkedAccount());
            List<DbxFileInfo> fileInfoList = dbxFs.listFolder(DbxPath.ROOT);
            for (int i = 0; i < fileInfoList.size(); i++) {
                DbxFile dbxFile = dbxFs.open(fileInfoList.get(i).path);
                Bitmap bitmap = decodeSampledBitmapFromResource(dbxFile.getReadStream(), 300, 300);
                MediaDetails mediaDetails = new MediaDetails();
                mediaDetails.setImage(bitmap);
                mediaList.add(mediaDetails);
                //imageAdapter.add(mediaDetails);
                dbxFile.close();
            }
        } catch (Exception e) {
            Log.d("exception", e.toString());
        }
        return mediaList;
    }

    public static Bitmap decodeSampledBitmapFromResource(FileInputStream fileInputStream,
                                                         int reqWidth, int reqHeight) {
        return Bitmap.createScaledBitmap(BitmapFactory.decodeStream(fileInputStream), reqWidth, reqHeight, true);
    }

}

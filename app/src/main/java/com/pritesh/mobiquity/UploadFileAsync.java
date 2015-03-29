package com.pritesh.mobiquity;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.dropbox.sync.android.DbxAccountManager;
import com.dropbox.sync.android.DbxFile;
import com.dropbox.sync.android.DbxFileSystem;
import com.dropbox.sync.android.DbxPath;
import com.pritesh.mobiquity.Models.MediaDetails;

import java.io.File;

/**
 * Created by admin on 29/03/2015.
 */
public class UploadFileAsync extends AsyncTask<File, Void, Void> {

    private Context context;
    private DbxAccountManager dbxAccountManager;
    private ImageAdapter imageAdapter;

    public UploadFileAsync(Context context, DbxAccountManager dbxAccountManager, ImageAdapter imageAdapter) {
        this.context = context;
        this.dbxAccountManager = dbxAccountManager;
        this.imageAdapter = imageAdapter;
    }

    @Override
    protected Void doInBackground(File... params) {
        DbxFile dbxFile;
        File file = params[0];
        try {
            DbxFileSystem dbxFs = DbxFileSystem.forAccount(dbxAccountManager.getLinkedAccount());
            DbxPath path = new DbxPath(DbxPath.ROOT, file.getName());
            if (!dbxFs.exists(path)) {
                dbxFile = dbxFs.create(path);
                try {
                    dbxFile.writeFromExistingFile(file, false);
                } catch (Exception e) {
                    Log.d("exception", e.toString());
                }
                dbxFile.close();
            }
        } catch (Exception ex) {
            Log.d("exception", ex.toString());
        }
        return null;
    }
}

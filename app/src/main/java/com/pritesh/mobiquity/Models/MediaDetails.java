package com.pritesh.mobiquity.Models;

import android.graphics.Bitmap;
import android.location.Location;

import java.io.File;

/**
 * Created by admin on 25/03/2015.
 */
public class MediaDetails {
    private int id;
    private String title;
    private Bitmap image;
    private File audio;
    private String textNotes;
    private Location location;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public File getAudio() {
        return audio;
    }

    public void setAudio(File audio) {
        this.audio = audio;
    }

    public String getTextNotes() {
        return textNotes;
    }

    public void setTextNotes(String textNotes) {
        this.textNotes = textNotes;
    }
}

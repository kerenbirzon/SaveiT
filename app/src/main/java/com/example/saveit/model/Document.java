package com.example.saveit.model;
//import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * the class that represents a document item
 */
@Entity
public class Document {
    @PrimaryKey
    @NonNull

    private String title;
    private String comment;
    private String expirationDate;
    private String reminderTime;
    //private Bitmap bitmap;
    private String bitmapUri;
    private String fileDownloadUri;
    private boolean hasImage;
    private boolean hasFile;
    private boolean hasAlarm;
    private boolean isAddEventToPhoneCalender;


    public Document() {
    }

    public Document(String title, String comment, String expirationDate, String bitmapUri, boolean hasImage) {
        this.title = title;
        this.comment = comment;
        this.expirationDate = expirationDate;
        this.bitmapUri = bitmapUri;
        this.hasImage = hasImage;
    }

    public Document(String title, String comment, String expirationDate, String bitmapUri, boolean hasImage, boolean hasAlarm, boolean hasFile, String fileDownloadUri, String reminderTime, boolean isAddEventToPhoneCalender) {
        this.title = title;
        this.comment = comment;
        this.expirationDate = expirationDate;
        this.bitmapUri = bitmapUri;
        this.hasImage = hasImage;
        this.hasFile = hasFile;
        this.hasAlarm = hasAlarm;
        this.fileDownloadUri = fileDownloadUri;
        this.reminderTime = reminderTime;
        this.isAddEventToPhoneCalender = isAddEventToPhoneCalender;
    }


    //getters and setters
    public String getTitle() {
        return title;
    }

    public String getComment() {
        return comment;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setHasAlarm(boolean hasAlarm) {
        this.hasAlarm = hasAlarm;
    }

    public void setBitmapUri(String Uri) {
        bitmapUri = Uri;
    }

    public String getBitmapUri() {
        return bitmapUri;
    }

    public boolean getHasImage() {
        return hasImage;
    }

    public boolean getHasAlarm() {
        return hasAlarm;
    }

    public void setHasImage(Boolean hasImage) {
        this.hasImage = hasImage;
    }

    public boolean isHasFile() {
        return hasFile;
    }

    public void setHasFile(boolean hasFile) {
        this.hasFile = hasFile;
    }

    public String getFileDownloadUri() {
        return fileDownloadUri;
    }

    public void setFileDownloadUri(String fileDownloadUri) {
        this.fileDownloadUri = fileDownloadUri;
    }

    public String getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(String reminderTime) {
        this.reminderTime = reminderTime;
    }

    public boolean getIsAddEventToPhoneCalender() {
        return isAddEventToPhoneCalender;
    }

    public void setIsAddEventToPhoneCalender(boolean addEventToPhoneCalender) {
        isAddEventToPhoneCalender = addEventToPhoneCalender;
    }
}
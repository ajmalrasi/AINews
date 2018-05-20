package com.ajmalrasi.newsai;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Rasi on 20-05-2018.
 */
public class News implements Parcelable {


    public static final Parcelable.Creator<News> CREATOR = new Parcelable.Creator<News>() {
        public News createFromParcel(Parcel in) {
            try {
                return new News(in);
            } catch (RuntimeException e) {
                e.printStackTrace();
                return new News();
            }
        }

        public News[] newArray(int size) {
            return new News[size];
        }
    };

    private String topic;
    private String title;
    private String link;
    private String thumbnails;
    private String description;
    private String category;
    private String publishedDate;

    public News() {
    }

    public News(Parcel input) {
        topic = input.readString();
        title = input.readString();
        link = input.readString();
        thumbnails = input.readString();
        description = input.readString();
        category = input.readString();
        publishedDate = input.readString();
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getThumbnails() {
        return thumbnails;
    }

    //setter

    public void setThumbnails(String thumbnails) {
        this.thumbnails = thumbnails;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(topic);
        dest.writeString(title);
        dest.writeString(link);
        dest.writeString(thumbnails);
        dest.writeString(description);
        dest.writeString(category);
        dest.writeString(publishedDate);

    }
}

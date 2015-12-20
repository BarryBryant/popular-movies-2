package com.b3sk.popularmovies.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Joopk on 12/15/2015.
 */
public class ReviewResult implements Parcelable {


    public static final Parcelable.Creator<ReviewResult> CREATOR = new Parcelable.Creator<ReviewResult>() {
        @Override
        public ReviewResult createFromParcel(Parcel parcel) {
            return new ReviewResult(parcel);
        }

        @Override
        public ReviewResult[] newArray(int i) {
            return new ReviewResult[i];
        }
    };
    private String author;
    private String content;


    public ReviewResult() {
    }


    private ReviewResult(Parcel in) {
        author = in.readString();
        content = in.readString();
    }

    /**
     * @return The author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author The author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return The content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content The content
     */
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(author);
        parcel.writeString(content);
    }

    @Override
    public int describeContents() {
        return 0;
    }


}

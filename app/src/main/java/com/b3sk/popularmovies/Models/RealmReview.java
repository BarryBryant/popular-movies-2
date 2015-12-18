package com.b3sk.popularmovies.Models;

import io.realm.RealmObject;

/**
 * Created by Joopk on 12/17/2015.
 */
public class RealmReview extends RealmObject {

    private String author;
    private String content;


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
}

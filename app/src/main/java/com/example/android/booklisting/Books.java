package com.example.android.booklisting;

/**
 * Created by Christophe on 24/11/2017.
 */

class Books {

    /** Title of the book */
    private String mTitle;

    /** Website URL of the book */
    private String mUrl;

    /**
     * Constructs a new {@link Books} object.
     *
     * @param title is the title of the book
     */
    public Books(String title) {
        mTitle = title;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getUrl() {return mUrl;}
}

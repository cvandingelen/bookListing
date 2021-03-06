package com.example.android.booklisting;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;

import java.util.List;

/**
 * Created by Christophe on 24/11/2017.
 */

class BooksLoader extends AsyncTaskLoader<List<Books>> {

    /** Query URL */
    private String mUrl;

    /**
     * Constructs a new {@link BooksLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */
    public BooksLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<Books> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<Books> books = QueryUtils.fetchBooksData(mUrl);
        return books;
    }
}

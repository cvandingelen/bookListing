package com.example.android.booklisting;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Books>> {

    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int BOOKS_LOADER_ID = 1;

    public static final String LOG_TAG = BookActivity.class.getName();

    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;

    /** URL for earthquake data from the USGS dataset */
    private static final String BOOKS_URL =
            "https://www.googleapis.com/books/v1/volumes?q=aspe";

    @Override
    public Loader<List<Books>> onCreateLoader(int i, Bundle bundle) {
        Log.i("Loader: ", "loader created");

        // Create a new loader for the given URL
        return new BooksLoader(this, BOOKS_URL);
    }



    @Override
    public void onLoadFinished(Loader<List<Books>> loader, List<Books> books) {
        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // Hide the loading indicator
        ProgressBar loading_spinner = (ProgressBar) findViewById(R.id.loading_spinner);
        loading_spinner.setVisibility(View.GONE);

        // Set empty state text to display "No books found."
        mEmptyStateTextView.setText(R.string.emptyList);

        // If there is a valid list of {@link Books}, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        }
        Log.i("Loader: ", "loader finished");
    }

    @Override
    public void onLoaderReset(Loader<List<Books>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
        Log.i("Loader: ", "loader resetted");
    }

    /** Adapter for the list of earthquakes */
    private BooksAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_activity);

        // Find a reference to the {@link btnSearch} in the layout
        Button btnSearch = (Button) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Find a reference to the {@link ListView} in the layout
                ListView booksListView = (ListView) findViewById(R.id.list);

                mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
                booksListView.setEmptyView(mEmptyStateTextView);

                // Create a new adapter that takes an empty list of books as input
                mAdapter = new BooksAdapter(BookActivity.this, new ArrayList<Books>());

                // Set the adapter on the {@link ListView}
                // so the list can be populated in the user interface
                booksListView.setAdapter(mAdapter);

                // Set an item click listener on the ListView, which sends an intent to a web browser
                // to open a website with more information about the selected book.
                booksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        // Find the current earthquake that was clicked on
                        Books currentBook = mAdapter.getItem(position);

                        // Convert the String URL into a URI object (to pass into the Intent constructor)
                        Uri bookUri = Uri.parse(currentBook.getUrl());

                        // Create a new intent to view the book URI
                        Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);

                        // Send the intent to launch a new activity
                        startActivity(websiteIntent);
                    }
                });

                // Get a reference to the ConnectivityManager to check state of network connectivity
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);

                // Get details on the currently active default data network
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                // If there is a network connection, fetch data
                if (networkInfo != null && networkInfo.isConnected()) {
                    // Get a reference to the LoaderManager, in order to interact with loaders.
                    LoaderManager loaderManager = getLoaderManager();

                    // Initialize the loader. Pass in the int ID constant defined above and pass in null for
                    // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
                    // because this activity implements the LoaderCallbacks interface).
                    loaderManager.initLoader(BOOKS_LOADER_ID, null, BookActivity.this);
                } else {
                    // Otherwise, display error
                    // First, hide loading indicator so error message will be visible
                    View loadingIndicator = findViewById(R.id.loading_spinner);
                    loadingIndicator.setVisibility(View.GONE);

                    // Update empty state with no connection error message
                    mEmptyStateTextView.setText(R.string.no_internet_connection);
                }

                Log.i("Loader: ", "initLoader called");
            }
        });
    }
}

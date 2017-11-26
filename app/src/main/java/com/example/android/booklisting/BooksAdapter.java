package com.example.android.booklisting;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Christophe on 24/11/2017.
 */

class BooksAdapter extends ArrayAdapter<Books> {

    /**
     * Contructs a new {@link BooksAdapter}.
     *
     * @param context of the app
     * @param books is the list of books, which is the data source of the adapter
     */
    public BooksAdapter(Context context, List<Books> books) {
        super(context, 0, books);
    }

    /** Returns a list item view that displays information about the book at the given position
     * in the list of earthquakes.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_list_item, parent, false);
        }

        // Find the book at the given position in the list of earthquakes
        Books currentBook = getItem(position);

        // Find the TextView with view ID title
        TextView magnitudeView = (TextView) listItemView.findViewById(R.id.title);

        // Display the title of the current book in that TextView
        magnitudeView.setText(currentBook.getmTitle());

        // Returns the list item view that is now showing the approopriate data
        return listItemView;
    }
}

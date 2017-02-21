/***
 * VerseAdapter.java: A custom adapter for creating Verse list views
 * @Author: Jhesed Tacadena
 * @Date: November 2016
 * */

package com.jjhsoftware.memoryversechallenge.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import com.jjhsoftware.memoryversechallenge.R;
import com.jjhsoftware.memoryversechallenge.VerseDetailsActivity;
import com.jjhsoftware.memoryversechallenge.models.Verse;

public class VerseAdapter extends ArrayAdapter<Verse> {

    /* SECTION: Variable Declarations */

    private final String TAG = "verseListAdapter";
    private final Context context;
    private final ArrayList<Verse> verse;
    private static ViewHolder viewHolder;

    public VerseAdapter(Context context, ArrayList<Verse> verse) {
        super(context, R.layout.activity_verse_list, verse);
        this.context = context;
        this.verse = verse;
        viewHolder = new ViewHolder();
    }

    private static class ViewHolder {
        /**
         * Private class for recyclable information
         * */
        int verseId;
        TextView verseName;
        // TextView verseContent;
    }

    public View getView(int position, View view, ViewGroup parent) {
        /**
         * Overrides parent for retrieving view
         * */

        Verse verse = getItem(position);

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.activity_verse_list, null, true);
        }
        else {
            viewHolder = (ViewHolder)view.getTag();
        }

        // Lookup view for data population
        viewHolder.verseId = verse.id;
        viewHolder.verseName = (TextView) view.findViewById(R.id.verseItem);

        // Populate the data into the template view using the data object
        viewHolder.verseName.setText(verse.name);
        // viewHolder.verseContent.setText(verse.content);

        view.setTag(viewHolder);
        view.setLongClickable(true);

        /* SECTION : Events */

        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, VerseDetailsActivity.class);
                intent.putExtra("verseId", viewHolder.verseId);
                context.startActivity(intent);
            }
        });
        return view;
    }
}
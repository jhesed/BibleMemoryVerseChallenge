/***
 * VerseDetailsActivity.java: Displays list of Bible Verses
 * @Author: Jhesed Tacadena
 * @Date: December 2016
 * */

package com.jjhsoftware.memoryversechallenge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import com.jjhsoftware.memoryversechallenge.adapters.VerseAdapter;
import com.jjhsoftware.memoryversechallenge.bible.BibleV1;

public class VerseListActivity extends AppCompatActivity {

    /* SECTION: Variable Initializations */

    private ListView verseListView;
    private VerseAdapter vAdapter;
    private Menu menu;
    private static final String TAG = "VerseListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verse_list);

        /* SECTION: ADS */

        try {
            mAdView = (AdView) findViewById(R.id.adView);
            AdRequest.Builder adRequest = new AdRequest.Builder();
            adRequest.addTestDevice("E0672EF9205508F55913C27654ED0CE9");
            mAdView.loadAd(adRequest.build());
        } catch(Exception e) {
            e.printStackTrace();
        }

        // Set icon in action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        verseListView = (ListView) findViewById(R.id.verseList);

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter
        if (vAdapter == null) {
            vAdapter = new VerseAdapter(this, BibleV1.versesQuery);
            verseListView.setAdapter(vAdapter);
        }
//        else {
//            Log.d(TAG, "vAdapter not Null, refreshing");
//            vAdapter.clear();
//            vAdapter.addAll(BibleV1.versesQuery);
//            vAdapter.notifyDataSetChanged();
//        }
        verseListView.setAdapter(vAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        this.menu = menu;

        // Update the menu
        invalidateOptionsMenu();
        MenuItem menuHome = menu.findItem(R.id.menu_home);
        MenuItem menuList = menu.findItem(R.id.menu_verse_list);
        menuHome.setVisible(true);
        menuList.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /* Adds behaviors to menu */
        int id = item.getItemId();

        if (id == R.id.menu_about) {
            // Shows information dialog
            showAboutDialog();
        }
        else if (id == R.id.menu_verse_list) {
            // Shows information dialog
            Intent intent = new Intent(this, VerseListActivity.class);
            this.startActivity(intent);
        }
        else if (id == R.id.menu_home) {
            // Shows information dialog
            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    private void showAboutDialog() {
        /**
         Displays dialog box of developer information
         */

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View layout = inflater.inflate(R.layout.dialog_menu_about, null);
        builder.setView(layout);
        //        builder.setNegativeButton("OK", null);
        final AlertDialog dialog = builder.create();

        // close dialog box on click
        Button okButton = (Button)layout.findViewById(R.id.dialogOk);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    /************************* ADS ******************************/

    private AdView mAdView;
    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }
}
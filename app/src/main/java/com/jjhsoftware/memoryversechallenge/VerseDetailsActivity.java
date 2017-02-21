/***
 * VerseDetailsActivity.java: Displays details (i.e. NIV and MBB content)
 *                            of a specific Bible Verse
 * @Author: Jhesed Tacadena
 * @Date: November 2016
 * */

package com.jjhsoftware.memoryversechallenge;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import com.jjhsoftware.memoryversechallenge.bible.BibleV1;

public class VerseDetailsActivity extends AppCompatActivity {

    /* SECTION: Variable Declarations */

    private Button btnPrev;
    private Button btnNext;
    private TextView titleHeader;
    private TextView titleEngNIV;
    private TextView titleFilMBB;
    private TextView contentEngNIV;
    private TextView contentFilMBB;
    private static int verseId;
    private Menu menu;
    private static final String TAG = "VerseDetailsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verse_details);

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

        // Retrieve view objects
        titleHeader = (TextView) findViewById(R.id.titleHeader);
        titleEngNIV = (TextView) findViewById(R.id.titleEngNIV);
        titleFilMBB = (TextView) findViewById(R.id.titleFilMBB);
        contentEngNIV = (TextView) findViewById(R.id.contentEngNIV);
        contentFilMBB = (TextView) findViewById(R.id.contentFilMBB);

        // Retrieve information passed from VerseListActivity class
        verseId = getIntent().getExtras().getInt("verseId");

        // Update views
        btnPrev = (Button) findViewById(R.id.buttonPrev) ;
        btnNext = (Button) findViewById(R.id.buttonNext) ;
        titleEngNIV.setText(R.string.NIV_title);
        titleFilMBB.setText(R.string.MBB_title);
        titleHeader.setText(BibleV1.versesQuery.get(verseId).name);
        contentEngNIV.setText(BibleV1.versesQuery.get(verseId).contentEnglish);
        contentFilMBB.setText(BibleV1.versesQuery.get(verseId).contentFilipino);

        /* SECTION: Button events */
        btnPrev.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
            verseId -= 1;
            if (verseId < 0) {
                verseId = BibleV1.VERSE_COUNT;  // restart to first index
            }
            titleHeader.setText(BibleV1.versesQuery.get(verseId).name);
            contentEngNIV.setText(BibleV1.versesQuery.get(verseId).contentEnglish);
            contentFilMBB.setText(BibleV1.versesQuery.get(verseId).contentFilipino);
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                verseId += 1;
                if (verseId > BibleV1.VERSE_COUNT) {
                    verseId = 0;  // restart to first index
                }
                titleHeader.setText(BibleV1.versesQuery.get(verseId).name);
                contentEngNIV.setText(BibleV1.versesQuery.get(verseId).contentEnglish);
                contentFilMBB.setText(BibleV1.versesQuery.get(verseId).contentFilipino);
            }
        });
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
        menuList.setVisible(true);
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
            startActivity(intent);
            return true;
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
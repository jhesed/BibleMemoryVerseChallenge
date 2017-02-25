/***
 * MainActivity.java: Displays the Main page for randomizing Bible verses
 * @Author: Jhesed Tacadena
 * @Date: November 2016
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
import android.widget.TextView;

//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;

import com.jjhsoftware.memoryversechallenge.bible.BibleV1;

public class HomeScreen extends AppCompatActivity {

    /* SECTION:  Variable Declarations */

    private Menu menu;
    private Button btnStart;
    private TextView gameOver;

    public static final String TAG = "HomeScreenActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        // Determine if this is coming from a gameover
        Bundle b = getIntent().getExtras();
        if (b != null) {
            boolean isGameOver = b.getBoolean("isGameOver");
            if(isGameOver) {
                // Show game over text
                gameOver = (TextView) findViewById(R.id.gameOverText);
                gameOver.setVisibility(View.VISIBLE);
            }
        }

        /* SECTION: ADS */

//        try {
//            mAdView = (AdView) findViewById(R.id.adView);
//            AdRequest.Builder adRequest = new AdRequest.Builder();
//            adRequest.addTestDevice("E0672EF9205508F55913C27654ED0CE9");
//            mAdView.loadAd(adRequest.build());
//        } catch(Exception e) {
//            e.printStackTrace();
//        }

        // Set icon in action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        // Sets Random Button
        btnStart = (Button) findViewById(R.id.startGame) ;

        /* SECTION: Events */

        btnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Start the actual game
                Intent intent = new Intent(HomeScreen.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        this.menu = menu;

        // Update the menu
        invalidateOptionsMenu();
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

//    private AdView mAdView;
//    @Override
//    public void onPause() {
//        if (mAdView != null) {
//            mAdView.pause();
//        }
//        super.onPause();
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        if (mAdView != null) {
//            mAdView.resume();
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        if (mAdView != null) {
//            mAdView.destroy();
//        }
//        super.onDestroy();
//    }
}
/***
* MainActivity.java: Displays the Main page for randomizing Bible verses
* @Author: Jhesed Tacadena
* @Date: November 2016
* */

package com.jjhsoftware.memoryversechallenge;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;

import java.util.Random;

import com.jjhsoftware.memoryversechallenge.bible.BibleV1;

public class MainActivity extends AppCompatActivity {

    /* SECTION:  Variable Declarations */

    private Button btnSubmit;
    private TextView titleHeader;
    private TextView verseTitle;
    private TextView verseContent;
    private Menu menu;
    private int lastVerseId = -1;
    private int totalScore = 0;

    // Quiz timer
    ProgressBar mProgressBar;
    CountDownTimer mCountDownTimer;
    int timerValue = 5;

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        // Generate the Bible Verses
        BibleV1.generateQuery();

        // Sets Random Button
        btnSubmit = (Button) findViewById(R.id.buttonRandom) ;

        /* SECTION: Events */

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // Get View Ids
                titleHeader = (TextView) findViewById(R.id.titleHeader);
                verseTitle = (TextView) findViewById(R.id.titleEngNIV);
                verseContent = (TextView) findViewById(R.id.contentEngNIV);

                // Generate random number for random Bible verse
                Random rand = new Random();
                int index = rand.nextInt(BibleV1.VERSE_COUNT);

                // Do not use previous verse
                while (lastVerseId == index) {
                    index = rand.nextInt(BibleV1.VERSE_COUNT);
                }
                lastVerseId = index;

                // Update the view with the new Bible Verse
                verseTitle.setText(R.string.NIV_title);
                titleHeader.setText(BibleV1.versesQuery.get(index).name);
                verseContent.setText(BibleV1.versesQuery.get(index).contentEnglish);

                // The Quiz timer before replacing the verse with blanks

                mProgressBar = (ProgressBar)findViewById(R.id.quizTimer);
                mProgressBar.setProgress(timerValue);
                mCountDownTimer = new CountDownTimer(5000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        timerValue--;  // TODO: Make this smoother
                        mProgressBar.setProgress(timerValue);
                    }

                    @Override
                    public void onFinish() {
                        // Reset progress bar
                        timerValue = 5;

                        // Generate the quiz item
                        final QuizItem question = generateQuestion(
                                titleHeader.getText().toString(),
                                verseContent.getText().toString());

                        // Replace the strings with the corresponding question
                        // Don't do anything with verse title as of now
                        //titleHeader.setText();
                        verseContent.setText(question.question);

                        // Fire the timer again. This time, the user should input
                        // his/her answers

                        mProgressBar = (ProgressBar)findViewById(R.id.quizTimer);
                        mProgressBar.setProgress(timerValue);
                        mCountDownTimer = new CountDownTimer(5000,1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                timerValue --;  // TODO: Make this smoother
                                mProgressBar.setProgress(timerValue);
                            }

                            @Override
                            public void onFinish() {
                                String userAnswer = ((EditText)findViewById(R.id.answer)).toString();
                                if (userAnswer == question.answer) {
                                    totalScore ++;

                                }

                            }
                        };
                        mCountDownTimer.start();
                    }
                };
                mCountDownTimer.start();

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
        menuHome.setVisible(false);
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

     class QuizItem {
        /*
        * This class will hold the question and answer for each item
        * */
        String question;
        String answer;
    }

    private QuizItem generateQuestion(String title, String verse) {
        /**
         * Generates question based from verse passed. Randomly replace with blank one of the words
         * in the verse
         * For now, ignore the title, and always use the verse content
         * */

        // extract words in verse
        String[] words = verse.split(" ");

        // choose random index from these words
        Random rand = new Random();
        int index = rand.nextInt(words.length);

        // Choose the word as the answer
        QuizItem quizItem = new QuizItem();
        quizItem.answer = words[index];

        // Replace answer with blank
        words[index] = "____";
        quizItem.question = TextUtils.join(" ", words);

        return quizItem;
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
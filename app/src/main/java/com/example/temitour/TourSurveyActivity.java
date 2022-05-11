package com.example.temitour;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.Queue;

/**
 * This class represents the activity for the survey after the Culture Walk tour
 *
 * @author Gavin Vogt
 */
public class TourSurveyActivity extends AppCompatActivity {

    // TODO: delete the unused fields along with commented stuff
//    private int[] questionFragmentIds;
//    private int questionNum = 1;
//    private ProgressBar progressBar;
//    private TextView progressText;
//    private Button nextButton;
//    private Button prevButton;

    /** WebView for accessing the tour survey Google Form */
    private WebView webView;

    /** Africana Studies professors URL */
    private static final String TOUR_SURVEY_URL = "https://docs.google.com/forms/d/e/1FAIpQLSc-J89XMzH704mvUhiICBu46QM-vwwmvBEqOwVu9YxU1lE8vw/viewform";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.survey_title);
        setContentView(R.layout.web_activity);

        // Navigate to the courses webpage
        webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(TOUR_SURVEY_URL);

        // TODO: delete all the commented stuff along with the SurveyFragment class since we are using Google Forms
//        setContentView(R.layout.activity_survey);
//        loadQuestionFragmentIds();
//
//        progressBar = findViewById(R.id.survey_progress_bar);
//        progressText = findViewById(R.id.survey_progress_text);
//        progressBar.setMax(questionFragmentIds.length);
//
//        nextButton = findViewById(R.id.survey_next);
//        setUpNextButton();
//
//        prevButton = findViewById(R.id.survey_back);
//        prevButton.setOnClickListener((v) -> {
//            setQuestion(questionNum - 1);
//        });
//
//        setQuestion(1);
    }

//    /**
//     * Sets up the Next button. If the user is on the last question in the survey, it
//     * changes to say "Submit" and performs a different OnClick action.
//     */
//    private void setUpNextButton() {
//        if (questionNum == questionFragmentIds.length) {
//            // Listener = submit survey
//            nextButton.setText(R.string.submit);
//            nextButton.setOnClickListener((v) -> {
//                finish();
//                Toast toast = Toast.makeText(getApplicationContext(), R.string.survey_submitted,
//                        Toast.LENGTH_SHORT);
//                toast.show();
//
//                // TODO: send survey input to database
//            });
//        } else {
//            // Listener = advance to next question
//            nextButton.setText(R.string.next);
//            nextButton.setOnClickListener((v) -> {
//                setQuestion(questionNum + 1);
//            });
//        }
//
//    }
//
//    /**
//     * Dynamically loads all survey question fragment IDs from the R.layout resources.
//     * All question fragments must follow the naming scheme "survey_fragment_*" to be
//     * detected (but the number does not matter).
//     */
//    private void loadQuestionFragmentIds() {
//        // Loop over the survey fragment resources
//        // NOTE: all survey questions must be stored in a fragment with form "survey_fragment_*"
//
//        // TODO: this loads all the fragments properly but need a way to get the data we want from each question
//        // For example reading some feedback text, or the amount of stars rated
//
//        Queue<Integer> ids = new LinkedList<>();
//        Field[] fields = R.layout.class.getFields();
//        for (Field f : fields) {
//            if (f.getName().startsWith("survey_fragment_")) {
//                // Valid survey fragment field
//                try {
//                    ids.add(f.getInt(null));
//                } catch (final IllegalAccessException e) {
//                    Log.e("GET_RESOURCE_BY_NAME: ", e.toString());
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        // Save the fragment ids
//        int n = ids.size();
//        questionFragmentIds = new int[n];
//        for (int i = 0; i < n; ++i) {
//            questionFragmentIds[i] = ids.remove();
//        }
//        Log.d("abcdefg", "Loaded " + n + " fragment ids");
//    }
//
//    /**
//     * Sets the progress in the survey to update the progress bar
//     * @param newQuestionNum is the question number to move to
//     */
//    private void setQuestion(int newQuestionNum) {
//        // Set the question number
//        this.questionNum = clampQuestionNum(newQuestionNum);
//        setUpNextButton();
//
//        // Advance the survey progress
//        progressBar.setProgress(questionNum);
//        progressText.setText(questionNum + " / " + questionFragmentIds.length);
//        Log.d("abcdefg", "Changed survey progress to " + questionNum);
//
//        // Switch to the correct question fragment
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.setReorderingAllowed(true);
//
//        // Replace whatever is in the fragment_container view with this fragment
//        SurveyFragment.setFragmentId(questionFragmentIds[questionNum - 1]);
//        transaction.replace(R.id.survey_fragment_container, SurveyFragment.class, null);
//        transaction.commit();
//    }
//
//    /**
//     * Clamps the question number to a valid range
//     * @param num is the question number
//     * @return value clamped between question 1 and the number of questions
//     */
//    private int clampQuestionNum(int num) {
//        return Math.max(1, Math.min(questionFragmentIds.length, num));
//    }
}
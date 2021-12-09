package com.example.temitour;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.robotemi.sdk.Robot;

public class MainActivity extends AppCompatActivity {

    private Button tourButton;
    private Button surveyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Tour button
        tourButton = findViewById(R.id.tour_button);
        tourButton.setOnClickListener((v) -> {
            openTourActivity();
        });

        // Survey button
        surveyButton = findViewById(R.id.survey_button);
        surveyButton.setOnClickListener((v) -> {
               openSurveyActivity();
            }
        );
    }

    @Override
    protected void onStart() {
        super.onStart();

        // NOTE: This is where various listeners can be set up for Temi
        // once the app starts. Any listeners must be removed by overriding
        // the onStop() method.

        // Hide the top bar (can be re-opened by swiping down from top of screen)
        Robot.getInstance().hideTopBar();
    }

    /**
     * Opens the tour activity
     */
    private void openTourActivity() {
        Intent intent = new Intent(this, TourActivity.class);
        startActivity(intent);
    }

    /**
     * Opens the survey activity
     */
    private void openSurveyActivity() {
        Intent intent = new Intent(this, SurveyActivity.class);
        startActivity(intent);
    }
}
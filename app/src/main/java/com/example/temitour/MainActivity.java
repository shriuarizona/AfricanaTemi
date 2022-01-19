package com.example.temitour;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.robotemi.sdk.Robot;
import com.robotemi.sdk.listeners.OnRobotReadyListener;

public class MainActivity extends AppCompatActivity implements OnRobotReadyListener {

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

        // TODO: delete the example button
        Button exampleButton = findViewById(R.id.exampleButton);
        exampleButton.setOnClickListener((v) -> {
            Intent intent = new Intent(this, ExampleActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        // NOTE: This is where various listeners can be set up for Temi
        // once the app starts. Any listeners must be removed in the onStop() method
        Robot.getInstance().addOnRobotReadyListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Remove listeners
        Robot.getInstance().removeOnRobotReadyListener(this);
    }

    @Override
    public void onRobotReady(boolean isReady) {
        if (isReady) {
            // Hide the top bar (can be re-opened by swiping down from top of screen)
            Robot.getInstance().hideTopBar();
        }
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
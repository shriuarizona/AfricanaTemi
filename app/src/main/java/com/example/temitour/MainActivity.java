package com.example.temitour;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.robotemi.sdk.Robot;
import com.robotemi.sdk.listeners.OnRobotReadyListener;

public class MainActivity extends AppCompatActivity implements OnRobotReadyListener {

    /** Button for taking the tour */
    private Button tourButton;
    /** Button for locating a professor's office */
    private Button locateOfficeButton;
    /** Button to view Africana Studies courses */
    private Button coursesButton;
    /** Button for Africana Studies events */
    private Button eventsButton;
    /** Button for leaving a message to a professor */
    private Button leaveMessageButton;
    /** Button for taking the survey */
    private Button surveyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Tour button
        tourButton = findViewById(R.id.tour_button);
        tourButton.setOnClickListener((v) -> {
            openActivity(TourActivity.class);
        });

        // Locate office button
        locateOfficeButton = findViewById(R.id.locate_office_button);
        locateOfficeButton.setOnClickListener((v) -> {
            openActivity(LocateOfficeActivity.class);
        });

        // Africana Studies courses button
        coursesButton = findViewById(R.id.courses_button);
        coursesButton.setOnClickListener((v) -> {
            openActivity(CoursesActivity.class);
        });

        // Africana Studies events button
        eventsButton = findViewById(R.id.events_button);
        eventsButton.setOnClickListener((v) -> {
            openActivity(EventsActivity.class);
        });

        // Leave message button
        leaveMessageButton = findViewById(R.id.leave_message_button);
        leaveMessageButton.setOnClickListener((v) -> {
            openActivity(LeaveMessageActivity.class);
        });

//        // Survey button
//        surveyButton = findViewById(R.id.survey_button);
//        surveyButton.setOnClickListener((v) -> {
//               openActivity(SurveyActivity.class);
//            }
//        );
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
     * Opens the activity for the given class
     * @param activityClass is the activity class to start
     */
    private void openActivity(Class activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }

}
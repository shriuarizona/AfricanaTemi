package com.example.temitour;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.robotemi.sdk.Robot;
import com.robotemi.sdk.listeners.OnRobotReadyListener;

/**
 * This class represents the main dashboard activity for the Africana Studies app
 *
 * @author Gavin Vogt
 */
public class MainActivity extends AppCompatActivity implements OnRobotReadyListener {

    /** Button for taking the tour */
    private Button tourButton;
    /** Button to view Africana Studies courses */
    private Button coursesButton;
    /** Button for Africana Studies events */
    private Button eventsButton;
    /** Button for Africana Studies professors */
    private Button professorsButton;
    /** Button for Africana Studies alumni */
    private Button alumniButton;
    /** Button for Africana Studies news */
    private Button newsButton;
    /** Button for info about Africana Studies */
    private Button infoButton;
    /** Button for leaving feedback on the app */
    private Button leaveFeedbackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Tour button
        tourButton = findViewById(R.id.tour_button);
        tourButton.setOnClickListener((v) -> {
            openActivity(TourActivity.class);
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

        // Professors button
        professorsButton = findViewById(R.id.professors_button);
        professorsButton.setOnClickListener((v) -> {
            openActivity(ProfessorsActivity.class);
        });

        // Alumni button
        alumniButton = findViewById(R.id.alumni_button);
        alumniButton.setOnClickListener((v) -> {
            openActivity(AlumniActivity.class);
        });

        // News button
        newsButton = findViewById(R.id.news_button);
        newsButton.setOnClickListener((v) -> {
            openActivity(NewsActivity.class);
        });

        infoButton = findViewById(R.id.info_button);
        infoButton.setOnClickListener((v) -> {
            openActivity(InfoActivity.class);
        });

        leaveFeedbackButton = findViewById(R.id.leave_feedback_button);
        leaveFeedbackButton.setOnClickListener((v) -> {
            openActivity(FeedbackActivity.class);
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
     * Opens the activity for the given class
     * @param activityClass is the activity class to start
     */
    private void openActivity(Class activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }

}
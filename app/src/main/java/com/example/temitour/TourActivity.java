package com.example.temitour;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TourActivity extends AppCompatActivity {

    Button cancelButton;
    Button beginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.tour_title);
        setContentView(R.layout.activity_tour);

        // Cancel button
        cancelButton = findViewById(R.id.cancelTourButton);
        cancelButton.setOnClickListener((l) -> finish());

        // Continue button
        beginButton = findViewById(R.id.beginTourButton);
        beginButton.setOnClickListener((l) -> {
            beginTour();
        });

    }

    /**
     * Begins the Temi tour
     */
    private void beginTour() {
        Toast toast = Toast.makeText(getApplicationContext(), "Beginning tour",
                Toast.LENGTH_SHORT);
        toast.show();

        // TODO: actually do the tour
        finish();
    }

}

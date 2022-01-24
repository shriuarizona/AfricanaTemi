package com.example.temitour;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.robotemi.sdk.Robot;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TourActivity extends AppCompatActivity {

    private Painting[] paintings = null;
    private ImageView paintingImage;
    private TextView artistTextView;
    private TextView yearTextView;
    private TextView mediumTextView;
    private TextView measurementsTextView;
    private TextView descriptionTextView;
    private Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.tour_title);
        setContentView(R.layout.activity_tour);
        paintingImage = findViewById(R.id.painting_image);
        artistTextView = findViewById(R.id.artist_text);
        yearTextView = findViewById(R.id.year_text);
        mediumTextView = findViewById(R.id.medium_text);
        measurementsTextView = findViewById(R.id.measurements_text);
        descriptionTextView = findViewById(R.id.description_text);
        continueButton = findViewById(R.id.continueButton);

        // Try to load the paintings and artists
        try {
            this.loadData();
        } catch (Exception e) {
            // Failed to load data
            Log.e("abcdefg", "Error loading paintings/artists", e);
            finish();
            Toast toast = Toast.makeText(getApplicationContext(), "Failed to load painting and/or artist data",
                    Toast.LENGTH_LONG);
            toast.show();
        }

        // Give the tour
        beginTour();
    }

    /**
     * Begins the Temi tour
     */
    private void beginTour() {
        Toast toast = Toast.makeText(getApplicationContext(), "Beginning tour",
                Toast.LENGTH_SHORT);
        toast.show();
        guideToPainting(0);
    }

    /**
     * Guides the user to the painting
     * @param i is the index of the painting in the array of Paintings
     */
    private void guideToPainting(int i) {
        // Take them to the painting
        if (i < paintings.length) {
            // Update the painting shown
            Painting painting = paintings[i];
            updatePaintingInfo(painting);

            // Update the Continue button
            if (i + 1 < paintings.length) {
                // Still other paintings left to go
                continueButton.setText(R.string.continue_tour);
                continueButton.setOnClickListener((v) -> {
                    guideToPainting(i + 1);
                });
            } else {
                continueButton.setText(R.string.finish_tour);
                continueButton.setOnClickListener((v) -> {
                    // ALertDialog builder to ask if they want to take survey or skip
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Rate your experience");
                    TextView tv = new TextView(this);
                    tv.setText("Help us improve your experience by taking a short survey");
                    builder.setView(tv);
                    builder.setPositiveButton("Take Survey", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO: check if this opens the survey activity properly
                            // TODO: make sure that the TourActivity is also closed after they complete the SurveyActivity
                            Intent intent = new Intent(getApplicationContext(), SurveyActivity.class);
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("No Thanks", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            finish();
                        }
                    });
                    builder.show();
                });
            }

            // Take the user to the painting
            Robot.getInstance().goTo(painting.getLocation());
            // TODO: use TTS to have robot speak about the painting
        }
    }

    private void updatePaintingInfo(Painting painting) {
        // Update the painting image
        try {
            Drawable d = getResources().getDrawable(painting.getImageId());
            paintingImage.setImageDrawable(d);
        } catch (Exception e) {
            Log.e("abcdefg", e.getMessage());
        }

        // Update the painting info displayed
        artistTextView.setText(painting.getArtist().getName());
        yearTextView.setText(painting.getYear());
        mediumTextView.setText(painting.getMedium());
        measurementsTextView.setText(painting.getMeasurements());
        descriptionTextView.setText(painting.getDescription());
    }

    /**
     * Loads the artist and painting data
     */
    private void loadData() throws Painting.InvalidArtistException {
        if (paintings != null) {
            // Data is already loaded
            return;
        }

        // Load all of the artists and paintings
        Resources resources = getResources();
        Gson gson = new Gson();
        Artist[] artists = gson.fromJson(new JsonReader(new InputStreamReader(
                resources.openRawResource(R.raw.artists))), Artist[].class);
        paintings = gson.fromJson(new JsonReader(new InputStreamReader(
                resources.openRawResource(R.raw.paintings))), Painting[].class);

        // Get the correct Artist object for each Painting
        Map<Integer, Artist> map = new HashMap<>();
        for (Artist a : artists) {
            map.put(a.getId(), a);
        }
        for (Painting p : paintings) {
            p.setArtist(map.get(p.getArtistId()));
        }
    }



}

package com.example.temitour;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.robotemi.sdk.Robot;
import com.robotemi.sdk.TtsRequest;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TourActivity extends AppCompatActivity {

    /** This array of paintings to tour */
    private Painting[] paintings = null;
    /** ImageView to hold the painting image */
    private ImageView paintingImage;
    /** Text holding the artist name */
    private TextView artistTextView;
    /** Text holding the year the painting was created */
    private TextView yearTextView;
    /** Text holding the painting medium */
    private TextView mediumTextView;
    /** Text holding the painting measurements */
    private TextView measurementsTextView;
    /** Text holding a description of the painting */
    private TextView descriptionTextView;
    /** Button to continue the tour */
    private Button continueButton;

    /** Random int generator */
    private Random rand = new Random();
    /** Low volume to avoid disturbing people */
    private static final int VOLUME_LEVEL = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.tour_title);
        setContentView(R.layout.activity_tour_crossfade);

        // Find views
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

        // Update display of painting information just in case
        if (paintings.length > 0) {
            updatePaintingInfo(paintings[0]);
        }

        // Fade from the start screen to the actual tour layout
        fadeBetweenLayoutsAndBeginTour();
    }

    private void fadeBetweenLayoutsAndBeginTour() {
        // Yoinked from https://stackoverflow.com/a/11712892
        final View tourStartLayout = findViewById(R.id.tour_start_layout);
        final View tourLayout = findViewById(R.id.tour_layout);
        final Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        final Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tourStartLayout.setVisibility(View.GONE);
                beginTour();
            }
        });

        // Animate the crossfade
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                tourStartLayout.startAnimation(fadeOut);
                tourLayout.startAnimation(fadeIn);
            }
        }, 1000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Robot.getInstance().cancelAllTtsRequests();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Robot.getInstance().cancelAllTtsRequests();
    }

    /**
     * Begins the Temi tour
     */
    private void beginTour() {
        // Set volume and begin the tour
        Robot.getInstance().setVolume(VOLUME_LEVEL);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle(R.string.rate_experience);
                    builder.setMessage(R.string.rate_experience_message);
                    builder.setPositiveButton("Take Survey", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
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
            // TODO: go to the location (uncomment below line)
            // Robot.getInstance().goTo(painting.getLocation());

            // TODO: use TTS to have robot speak about the painting
            Robot.getInstance().speak(TtsRequest.create(
                    getTextToSpeak(painting), false, TtsRequest.Language.EN_US));

        }
    }

    /**
     * Generates the text to speak to the user using Temi's TTS
     * @param painting is the Painting to describe to the user
     * @return the string to speak
     */
    private String getTextToSpeak(Painting painting) {
        String artistName = painting.getArtist().getName();
        String medium = painting.getMedium();
        String description = painting.getDescription();

        // TODO: add more speech patterns to choose from (kind of boring at the moment)
        switch (rand.nextInt(2)) {
            case 0:
                return "This work of art by " + artistName + " was created on " + medium
                    + ". Pictured is " + description;
            case 1:
                return "This " + medium + ", created by " + artistName + ", displays " + description;
            default:
                return "";
        }
    }

    /**
     * Updates the painting information on display. This includes the painting image
     * as well as metadata such as artist, year, etc
     * @param painting is the painting to update the information to
     */
    private void updatePaintingInfo(Painting painting) {
        // Update the painting image
        try {
            Drawable d = getResources().getDrawable(painting.getImageId(), null);
            int intrinsicHeight = d.getIntrinsicHeight();
            int intrinsicWidth = d.getIntrinsicWidth();
            Log.d("abcdefg", "height = " + intrinsicHeight);
            Log.d("abcdefg", "width = " + intrinsicWidth);
//            ViewGroup.LayoutParams lp = paintingImage.getLayoutParams();
//            float factor;
//            lp.width = 3;
//            lp.height = 7;
//            paintingImage.setLayoutParams(lp);
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

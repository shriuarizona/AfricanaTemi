package com.example.temitour;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TourActivity extends AppCompatActivity {

    private Button cancelButton;
    private Button beginButton;
    private static Painting[] paintings = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.tour_title);
        setContentView(R.layout.activity_tour);

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

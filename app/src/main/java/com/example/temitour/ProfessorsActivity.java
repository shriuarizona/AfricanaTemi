package com.example.temitour;

import android.os.Bundle;
import android.util.TypedValue;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.temitour.scrape.EventsScraper;

import java.io.IOException;

/**
 * This class represents the activity for showing the user the Africana Studies professors
 *
 * @author Gavin Vogt
 */
public class ProfessorsActivity extends AppCompatActivity {

    /** WebView for accessing the professors site */
    private WebView webView;

    /** Africana Studies professors URL */
    private static final String PROFESSORS_URL = "https://africana.arizona.edu/people/faculty";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.professors);
        setContentView(R.layout.web_activity);

        // Navigate to the courses webpage
        webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(PROFESSORS_URL);
    }

//    protected void onStart() {
//        super.onStart();
//
//        // TODO: can remove this if the Events website works better
//        // TODO: useful for understanding how to asynchonously load from HTTP request
//        // Load the professors from Africana Studies in background
//        TourApplication app = (TourApplication) getApplication();
//        app.executorService.execute(() -> {
//            try {
//                List<AfricanaEvent> events = EventsScraper.loadEvents();
//                runOnUiThread(() -> {
//                    // Loaded events successfully; display them
//                    mainLayout.removeView(eventsProgressBar);
//                    for (AfricanaEvent event : events) {
//                        addEvent(event);
//                    }
//                });
//            } catch (IOException e) {
//                // Failed to load events
//                runOnUiThread(() -> {
//                    mainLayout.removeView(eventsProgressBar);
//                    TextView failText = new TextView(EventsActivity.this);
//                    failText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
//                    failText.setText(R.string.event_load_fail);
//                    eventsContainer.addView(failText);
//                });
//            }
//        });
//    }

}

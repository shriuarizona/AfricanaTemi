package com.example.temitour;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Space;
import android.widget.TextView;

import com.example.temitour.scrape.EventsScraper;
import com.example.temitour.scrape.EventsScraper.AfricanaEvent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.IOException;
import java.util.List;

/**
 * This class represents the activity for showing the user Africana Studies events
 *
 * @author Gavin Vogt
 */
public class EventsActivity extends AppCompatActivity {

    /** WebView for accessing the events site */
    private WebView webView;

    /** Africana Studies events URL */
    private static final String EVENTS_URL = "https://africana.arizona.edu/events";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.events_title);
        setContentView(R.layout.web_activity);

        // Navigate to the Events website
        webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);   // Need JavaScript for Google forms
        webView.loadUrl(EVENTS_URL);
    }

    protected void onStart() {
        super.onStart();

        // TODO: can remove this if the Events website works better
        // TODO: useful for understanding how to asynchonously load from HTTP request
//        // Load the events from Africana Studies in background
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
    }

    /**
     * Adds an event to the container displaying
     * @param event is the Africana Studies event to add
     */
//    private void addEvent(AfricanaEvent event) {
//        // Inflate the event container
//        LayoutInflater inflater = getLayoutInflater();
//        ConstraintLayout eventContainer = (ConstraintLayout) inflater.inflate(R.layout.event_container, null);
//
//        // Replace image
//        if (event.image != null) {
//            ImageView image = (ImageView) eventContainer.getChildAt(0);
//            image.setImageDrawable(event.image);
//        }
//
//        // Replace the title
//        TextView title = (TextView) eventContainer.getChildAt(1);
//        title.setText(event.title);
//
//        // Replace the date
//        TextView date = (TextView) eventContainer.getChildAt(2);
//        date.setText(event.date);
//
//        // Replace the description
//        TextView desc = (TextView) eventContainer.getChildAt(3);
//        desc.setText(event.description);
//
//        // Add the event + space below to the view
//        eventsContainer.addView(eventContainer);
//        Space s = new Space(this);
//        s.setMinimumHeight(AppUtils.dpToPx(getResources(), 40));
//        eventsContainer.addView(s);
//    }

}

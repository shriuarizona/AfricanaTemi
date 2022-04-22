package com.example.temitour;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class EventsActivity extends AppCompatActivity {

    /** Container for the events */
    private LinearLayout eventContainer;
    /** URL to scrape Africana Studies events from */
    private static final String EVENTS_URL = "https://africana.arizona.edu/events";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.events_title);
        setContentView(R.layout.activity_events);
        eventContainer = findViewById(R.id.event_container);
        try {
            List<AfricanaEvent> events = new EventLoaderTask().execute().get();
            for (AfricanaEvent event : events) {
                addEvent(event);
            }
        } catch (ExecutionException|InterruptedException e) {
        }
    }

    /**
     * Adds an event to the container displaying
     * @param event is the Africana Studies event to add
     */
    private void addEvent(AfricanaEvent event) {
        TextView title = new TextView(this);
        title.setText(event.title);
        eventContainer.addView(title);

        TextView desc = new TextView(this);
        desc.setText(event.description);
        eventContainer.addView(desc);

        TextView date = new TextView(this);
        date.setText(event.date);
        eventContainer.addView(date);

        if (event.image != null) {
            ImageView image = new ImageView(this);
            image.setImageDrawable(event.image);
            eventContainer.addView(image);
        }
    }

    /**
     * This class represents an upcoming event for Africana Studies
     */
    private static class AfricanaEvent {

        /** Event title */
        private String title;
        /** Event description */
        private String description;
        /** Event date */
        private String date;
        /** Drawable image for event */
        private Drawable image;

        /**
         * Constructs a new Africana event
         * @param title is the event title
         * @param description is the event description
         * @param date is the event date
         * @param image is the Drawable image for the event
         */
        public AfricanaEvent(String title, String description, String date, Drawable image) {
            this.title = title;
            this.description = description;
            this.date = date;
            this.image = image;
        }
    }

    /**
     * Asynchronous task to load all the events for Africana Studies
     */
    private static class EventLoaderTask extends AsyncTask<Void, Void, List<AfricanaEvent>> {

        @Override
        protected List<AfricanaEvent> doInBackground(Void... voids) {
            try {
                return parseEvents();
            } catch (IOException e) {
                return null;
            }
        }

        /**
         * Parses each event from the Africana Studies website
         * @return list of upcoming events
         * @throws IOException if HTTP connection fails
         */
        private List<AfricanaEvent> parseEvents() throws IOException {
            List<AfricanaEvent> events = new ArrayList<>();
            Document doc = Jsoup.connect(EVENTS_URL).get();
            Elements viewContents = doc.getElementsByClass("view-content");
            for (Element viewContent : viewContents) {
                // Parse each event element from the view-content <div>
                for (Element eventEl : viewContent.children()) {
                    events.add(parseEvent(eventEl));
                }
            }
            return events;
        }

        /**
         * Parses the given event element from the Africana Studies page
         * @param eventEl is the element to get the event information from
         * @return Africana Studies event
         */
        private AfricanaEvent parseEvent(Element eventEl) {
            // Get the text fields
            String date = eventEl.selectXpath("//div[1]/span").text();
            String title = eventEl.selectXpath("//div[2]/div[1]").text();
            String description = eventEl.selectXpath("//div[2]/div[2]").text();

            // Load the image
            String imageSrc = eventEl.selectXpath("//div[1]/*/img").attr("src");
            Drawable image;
            try {
                InputStream is = (InputStream) new URL(imageSrc).getContent();
                image = Drawable.createFromStream(is, "src name");
            } catch (IOException e) {
                Log.e("abcdefg", Log.getStackTraceString(e));
                image = null;
            }

            return new AfricanaEvent(title, description, date, image);
        }
    }

}

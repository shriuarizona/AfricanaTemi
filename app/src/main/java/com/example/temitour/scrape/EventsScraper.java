package com.example.temitour.scrape;

import android.graphics.drawable.Drawable;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EventsScraper {

    /** URL to scrape Africana Studies events from */
    private static final String EVENTS_URL = "https://africana.arizona.edu/events";

    /**
     * Parses each event from the Africana Studies website
     * @return list of upcoming events
     * @throws IOException if HTTP connection fails
     */
    public static List<AfricanaEvent> loadEvents() throws IOException {
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
    private static AfricanaEvent parseEvent(Element eventEl) {
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

    /**
     * This class represents an upcoming event for Africana Studies
     */
    public static class AfricanaEvent {

        /** Event title */
        public final String title;
        /** Event description */
        public final String description;
        /** Event date */
        public final String date;
        /** Drawable image for event */
        public final Drawable image;

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

}

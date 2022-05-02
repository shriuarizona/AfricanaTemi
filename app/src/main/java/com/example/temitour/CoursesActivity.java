package com.example.temitour;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This class represents the activity for showing the user Africana Studies courses
 *
 * @author Gavin Vogt
 */
public class CoursesActivity extends AppCompatActivity {

    /** WebView for accessing the courses site */
    private WebView webView;

    /** Africana Studies courses URL */
    private static final String COURSES_URL = "https://africana.arizona.edu/undergraduate/current-students/course-schedule";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.courses);
        setContentView(R.layout.web_activity);

        // Navigate to the courses webpage
        webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(COURSES_URL);
    }

}

package com.example.temitour;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * This class represents the activity for getting general feedback on the app
 * from the user
 *
 * @author Gavin Vogt
 */
public class FeedbackActivity extends AppCompatActivity {

    /** WebView for accessing the feedback Google Form */
    private WebView webView;

    /** Feedback Google Form URL */
    private static final String FEEDBACK_URL = "https://docs.google.com/forms/d/e/1FAIpQLSdRo5aTC4bZC1f-Z0A5xSWc2XNhUZdYu1oBQeXbC-tdm8JmAw/viewform";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.feedback);
        setContentView(R.layout.web_activity);

        // Navigate to the feedback Google Form
        webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);   // Need JavaScript for Google forms
        webView.loadUrl(FEEDBACK_URL);
    }
}

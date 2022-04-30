package com.example.temitour;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * This class represents the activity for getting general feedback on the app
 * from the user
 */
public class FeedbackActivity extends AppCompatActivity {

    private static final String FEEDBACK_URL = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_activity);

        // Navigate to the feedback Google Form
        WebView webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);   // Need JavaScript for Google forms
        webView.loadUrl(FEEDBACK_URL);
    }
}

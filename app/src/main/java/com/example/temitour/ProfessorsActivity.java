package com.example.temitour;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This class represents the activity for showing the user the Africana Studies professors
 */
public class ProfessorsActivity extends AppCompatActivity {

    /** WebView for accessing the professors site */
    private WebView webView;

    /** Africana Studies courses URL */
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

    @Override
    public void onBackPressed() {
        if(webView != null && webView.canGoBack()) {
            // Go back if there is a previous page
            webView.goBack();
        } else {
            // No previous page; close the app
            super.onBackPressed();
        }
    }

}

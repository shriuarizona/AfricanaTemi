package com.example.temitour;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This class represents the activity for showing the user the Africana Studies alumni
 */
public class AlumniActivity extends AppCompatActivity {

    /** WebView for accessing the alumni site */
    private WebView webView;

    /** Africana Studies courses URL */
    private static final String ALUMNI_URL = "https://africana.arizona.edu/people/alumni";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.alumni);
        setContentView(R.layout.web_activity);

        // Navigate to the alumni webpage
        webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(ALUMNI_URL);
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) {
            // Go back if there is a previous page
            webView.goBack();
        } else {
            // No previous page; close the app
            super.onBackPressed();
        }
    }

}

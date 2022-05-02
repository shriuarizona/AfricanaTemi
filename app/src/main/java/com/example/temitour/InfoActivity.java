package com.example.temitour;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This class represents the activity for showing the user Africana Studies info
 *
 * @author Gavin Vogt
 */
public class InfoActivity extends AppCompatActivity {

    /** WebView for accessing the info site */
    private WebView webView;

    /** Africana Studies info URL */
    private static final String INFO_URL = "https://africana.arizona.edu/undergraduate/prospective-students/why-choose-africana-studies";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.info);
        setContentView(R.layout.web_activity);

        // Navigate to the info webpage
        webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(INFO_URL);
    }

}

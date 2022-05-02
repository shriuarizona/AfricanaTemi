package com.example.temitour;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This class represents the activity for showing the user Africana Studies news
 *
 * @author Gavin Vogt
 */
public class NewsActivity extends AppCompatActivity {

    /** WebView for accessing the news site */
    private WebView webView;

    /** Africana Studies news URL */
    private static final String NEWS_URL = "https://africana.arizona.edu/news";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.news);
        setContentView(R.layout.web_activity);

        // Navigate to the news webpage
        webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(NEWS_URL);
    }

}

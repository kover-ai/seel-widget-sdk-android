package com.seel.widget.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.seel.widget.R;
import com.seel.widget.utils.DpPxUtils;

/**
 * SeelWebViewController
 * Provides a full-screen web view with navigation controls
 */
public class SeelWebViewController extends AppCompatActivity {
    
    private WebView webView;
    private String url;
    
    // Navigation bar components
    private SeelNavigationBar navigationBar;
    private ProgressBar progressBar;
    
    // Loading indicator
    private ProgressBar loadingIndicator;
    
    public static void show(Context context, String url) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        android.content.Intent intent = new android.content.Intent(context, SeelWebViewController.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Get URL from intent
        url = getIntent().getStringExtra("url");
        if (url == null) {
            finish();
            return;
        }
        
        setupWebView();
        configViews();
        updateViews();
    }
    
    @SuppressLint("SetJavaScriptEnabled")
    private void setupWebView() {
        webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient(new SeelWebViewClient());
        webView.setWebChromeClient(new SeelWebChromeClient());
    }
    
    private void configViews() {
        // Create main container
        ConstraintLayout mainContainer = new ConstraintLayout(this);
        mainContainer.setBackgroundColor(Color.WHITE);
        setContentView(mainContainer);
        
        // Create navigation bar
        navigationBar = new SeelNavigationBar(this);
        navigationBar.setId(View.generateViewId());
        navigationBar.setTitle("Loading...");
        navigationBar.setRightButtonVisible(true);
        navigationBar.setBackButtonVisible(false);
        navigationBar.setBackButtonClickListener(() -> backButtonTapped());
        navigationBar.setRightButtonClickListener(() -> closeButtonTapped());
        mainContainer.addView(navigationBar);
        
        // Create progress bar
        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setId(View.generateViewId());
        progressBar.setProgressTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#2121C4")));
        progressBar.setProgress(0);
        progressBar.setVisibility(View.GONE);
        mainContainer.addView(progressBar);
        
        // Add WebView
        webView.setId(View.generateViewId());
        
        // Set layout params for WebView to prevent overlap
        ConstraintLayout.LayoutParams webViewParams = new ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_CONSTRAINT,
            ConstraintLayout.LayoutParams.MATCH_CONSTRAINT
        );
        webView.setLayoutParams(webViewParams);
        mainContainer.addView(webView);
        
        // Add loading indicator
        loadingIndicator = new ProgressBar(this);
        loadingIndicator.setId(View.generateViewId());
        loadingIndicator.setIndeterminate(true);
        
        // Set layout params for loading indicator
        ConstraintLayout.LayoutParams loadingParams = new ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        loadingIndicator.setLayoutParams(loadingParams);
        mainContainer.addView(loadingIndicator);
        
        // Setup constraints
        setupConstraints(mainContainer);
    }
    
    
    
    private void setupConstraints(ConstraintLayout container) {
        ConstraintSet set = new ConstraintSet();
        set.clone(container);
        
        // Navigation bar constraints - fixed full screen width, fixed 44dp height
         set.connect(navigationBar.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
         set.connect(navigationBar.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
         set.connect(navigationBar.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
//         set.constrainHeight(navigationBar.getId(), dpToPx(44)); // Fixed height to 44dp
         set.constrainWidth(navigationBar.getId(), ConstraintSet.MATCH_CONSTRAINT); // Fixed width to match parent
        
        // Progress bar constraints - below navigation bar, full screen width
        set.connect(progressBar.getId(), ConstraintSet.TOP, navigationBar.getId(), ConstraintSet.BOTTOM);
        set.connect(progressBar.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
        set.connect(progressBar.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
        set.constrainHeight(progressBar.getId(), dpToPx(2));
        set.constrainWidth(progressBar.getId(), ConstraintSet.MATCH_CONSTRAINT);
        
        // WebView constraints
        set.connect(webView.getId(), ConstraintSet.TOP, progressBar.getId(), ConstraintSet.BOTTOM);
        set.connect(webView.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
        set.connect(webView.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
        set.connect(webView.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        
        // Loading indicator constraints
        set.connect(loadingIndicator.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        set.connect(loadingIndicator.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
        set.connect(loadingIndicator.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT);
        set.connect(loadingIndicator.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
        
        set.applyTo(container);
    }
    
    private int dpToPx(int dp) {
        return DpPxUtils.dp(dp);
    }
    
    private void updateViews() {
        webView.loadUrl(url);
    }
    
    private void backButtonTapped() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }
    
    private void closeButtonTapped() {
        finish();
    }
    
    private void updateBackButtonState() {
        navigationBar.setBackButtonVisible(webView.canGoBack());
    }
    
    private void updateTitle() {
        String title = webView.getTitle();
        if (title != null && !title.isEmpty()) {
            navigationBar.setTitle(title);
        } else {
            // If no title, show URL host part
            String host = webView.getUrl();
            if (host != null) {
                try {
                    java.net.URL url = new java.net.URL(host);
                    navigationBar.setTitle(url.getHost());
                } catch (Exception e) {
                    navigationBar.setTitle("");
                }
            } else {
                navigationBar.setTitle("");
            }
        }
    }
    
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
    
    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.destroy();
        }
        super.onDestroy();
    }
    
    /**
     * Custom WebViewClient to handle navigation events
     */
    private class SeelWebViewClient extends WebViewClient {
        
        @Override
        public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            loadingIndicator.setVisibility(View.VISIBLE);
            progressBar.setProgress(0);
            progressBar.setVisibility(View.VISIBLE);
            navigationBar.setTitle("Loading...");
        }
        
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            loadingIndicator.setVisibility(View.GONE);
            progressBar.setProgress(100);
            
            // Delay hiding progress bar
            webView.postDelayed(() -> progressBar.setVisibility(View.GONE), 500);
            
            // Update back button state
            updateBackButtonState();
            
            // Delay updating title to ensure title is loaded
            webView.postDelayed(() -> updateTitle(), 100);
        }
        
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            loadingIndicator.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            android.util.Log.e("SeelWebViewController", "WebView failed to load: " + description);
        }
    }
    
    /**
     * Custom WebChromeClient to handle progress updates
     */
    private class SeelWebChromeClient extends WebChromeClient {
        
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            progressBar.setProgress(newProgress);
        }
        
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            updateTitle();
        }
    }
}

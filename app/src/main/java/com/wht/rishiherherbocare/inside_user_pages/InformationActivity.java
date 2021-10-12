package com.wht.rishiherherbocare.inside_user_pages;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.wht.rishiherherbocare.R;
import com.wht.rishiherherbocare.Initial.BaseActivity;
import com.wht.rishiherherbocare.my_library.Constants;


public class InformationActivity extends BaseActivity {

    private WebView webView;
    private String URL ;
    private ProgressBar progressBar;
    private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        bundle=getIntent().getExtras();
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView toolbar_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText(bundle.getString(Constants.Title));
        URL=bundle.getString(Constants.Description);

        webView = (WebView)findViewById(R.id.webview);
        progressBar = (ProgressBar)findViewById(R.id.progressBar1);

        WebViewLoadFunction();
        webView.clearView();
        webView.measure(100, 100);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDomStorageEnabled(true);
    }

    public void WebViewLoadFunction(){


        // Add setWebViewClient on WebView.
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon){
                // Do something here on page load start time.

            }
            @Override
            public void onPageFinished(WebView view, String url){
                try {

                    Log.d("testing", "onPageFinished: "+url);

                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });


        // Add setWebChromeClient on WebView.
        webView.setWebChromeClient(new WebChromeClient(){

            public void onProgressChanged(WebView webView1, int newProgress){

                //WebViewStatusTextView.setText("loading = " + newProgress + "%");
                progressBar.setProgress(newProgress);

                if(newProgress == 100){
                    progressBar.setVisibility(View.GONE);
                    // Page loading finish
                    //WebViewStatusTextView.setText("Page Load Finish.");
                }
            }
        });

        // Giving permissio to enable JavScript.
        webView.getSettings().setJavaScriptEnabled(true);

        // Pass the String variable which holds the website URL.
        webView.loadUrl(URL);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                if (webView.canGoBack()) {
                    webView.goBack();
                }else {
                    onBackPressed();
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            if (webView.canGoBack()) {
                webView.goBack();
            }else {
                onBackPressed();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}



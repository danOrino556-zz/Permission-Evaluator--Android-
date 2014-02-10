package com.threeAmigos.whistleblowerpro;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

/**
 * Daniel Robertson
 * 879118
 *
 * In the Settings activity users can press a button and be redirected to the PayPal
 * page
 *
 */

public class PayPalWebActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_paypal_web_activity);

        setUI();
    }

    public void setUI()
    {
        //WebView set up
        WebView myWebView = (WebView) findViewById(R.id.webViewPermission);
        myWebView.setWebViewClient(new myWebViewClient());
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.getSettings().setUseWideViewPort(true);
        myWebView.getSettings().setBuiltInZoomControls(true);
        myWebView.loadUrl("https://www.paypal.com/webapps/mpp/donate-with-paypal");

        //Button set up
        ImageButton backButton = (ImageButton) findViewById(R.id.button_back_web);
        ImageButton settingsButton = (ImageButton) findViewById(R.id.button_settings_web);
        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
                finish();
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(PayPalWebActivity.this, Settings.class);
                startActivity(i);
            }
        });
    }

    //This will allow our webview to remain within the application (no external browser)
    public class myWebViewClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pay_pal_web, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
    }
    
}

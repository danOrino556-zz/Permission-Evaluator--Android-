package com.threeAmigos.whistleblowerpro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
 * No longer used by the application. Was originally intended as a redirect
 * but a cleaner solution involving using the GooglePlay application was later
 * applied. This is still in the project because it has potential for future use.
 *
 */

public class ApplicationWebPage extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_application_web_page);

        setUI();
    }

    public void setUI()
    {
        //Get the passed permission string
        Bundle appWanted = getIntent().getExtras();
        String appURL = appWanted.getString("appURL");
        String appName = appWanted.getString("appName");

        //Set up webview
        //Noticed that we are customizing the loaded url to the parsed permission string
        WebView myWebView = (WebView) findViewById(R.id.webViewPermission);
        myWebView.setWebViewClient(new myWebViewClient());
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.getSettings().setUseWideViewPort(true);
        myWebView.getSettings().setBuiltInZoomControls(true);
        myWebView.loadUrl(appURL);

        //Set up buttons
        ImageButton backButton = (ImageButton) findViewById(R.id.button_back_web);
        ImageButton settingsButton = (ImageButton) findViewById(R.id.button_settings_web);
        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(ApplicationWebPage.this, Settings.class);
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
        getMenuInflater().inflate(R.menu.application_web_page, menu);
        return true;
    }
    
}

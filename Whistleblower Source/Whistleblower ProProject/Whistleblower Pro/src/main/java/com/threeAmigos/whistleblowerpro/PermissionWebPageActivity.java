package com.threeAmigos.whistleblowerpro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Daniel Robertson
 * 879118
 *
 * In the DefaultsSearchDetails activity users can click a permission within the
 * permissions list and be redirected to the developer page where that permissions resides
 *
 * This class opens and configures that webview
 */


public class PermissionWebPageActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_permission_web_page);

        setUI();
    }

    public void setUI()
    {
        //The string containing the permission searched was passed at the start of the activity

        //Get the passed permission string
        Bundle appWanted = getIntent().getExtras();
        String appSearched = appWanted.getString("permissionSearched");

        //Parse only the permission part of the string
        //We want to set the title to just the permission
        //We also want to go to the webpage for just that permission
        //Example : android.permission.MANAGE_ACCOUNTS
        //We only want MANAGE_ACCOUNTS
        StringTokenizer st = new StringTokenizer(appSearched, ".");
        String parsedPermission = new String();
        ArrayList<String> parsedPermissionWords = new ArrayList<String>();
        while (st.hasMoreElements())
        {
            parsedPermissionWords.add((String)st.nextElement());
        }

        parsedPermission = parsedPermissionWords.get(parsedPermissionWords.size()-1);

        //Set up webview
        //Noticed that we are customizing the loaded url to the parsed permission string
        WebView myWebView = (WebView) findViewById(R.id.webViewPermission);
        myWebView.setWebViewClient(new myWebViewClient());
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.getSettings().setUseWideViewPort(true);
        myWebView.getSettings().setBuiltInZoomControls(true);
        myWebView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        myWebView.loadUrl("http://developer.android.com/reference/android/Manifest.permission.html"+"#"+parsedPermission);



        //Set up buttons
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
                Intent i = new Intent(PermissionWebPageActivity.this, Settings.class);
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
        getMenuInflater().inflate(R.menu.permission_web_page, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
    }
    
}

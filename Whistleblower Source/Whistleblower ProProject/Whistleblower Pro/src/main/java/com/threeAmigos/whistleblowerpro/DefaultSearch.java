package com.threeAmigos.whistleblowerpro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.Window;

/**
 * Daniel Robertson
 * 879118
 *
 * Launcher for the application
 *
 * Used as a splash screen. It will wait 3000ms then transfer to the ThreeAmigos activity
 * (Also a splash screen.
 */

public class DefaultSearch extends Activity {

    private static int SPLASH_TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_default_search);
        new Handler().postDelayed(new Runnable()
        {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run()
            {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(DefaultSearch.this, ThreeAmigos.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.default_search, menu);
        return true;
    }
    
}

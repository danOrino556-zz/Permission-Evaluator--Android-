package com.threeAmigos.whistleblowerpro;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.Window;
import android.widget.Toast;

/**
 * Daniel Robertson
 * 879118
 *
 * SPlash screen
 *
 * Redirects to the main activity, DefaultSearchList
 */

public class ThreeAmigos extends Activity {

    private static int SPLASH_TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_three_amigos);

        //String jsonForSearch = new String();
        //new DownloadApplicationSearch().execute(" jacob's search URL");

        new Handler().postDelayed(new Runnable()
        {

            //Splash screen timer

            @Override
            public void run() {
                //The following will happen after the timer is complete
                Intent i = new Intent(ThreeAmigos.this, DefaultSearchList.class);

                //Bundle extras = new Bundle();
                //extras.putString("appJSON", jsonForSearch);
                //extras.putString("applicationWanted", appToSearch);
                //i.putExtras(extras);

                startActivity(i);
                overridePendingTransition(R.anim.slide_up, R.anim.slide_down);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    private class DownloadApplicationSearch extends AsyncTask<String, Void, String>
    {
        JSONParser jsonParser = new JSONParser();
        protected String doInBackground(String... urls)
        {
            return jsonParser.readJSONFeed(urls[0]);
        }

        @Override
        protected void onPostExecute(String result)
        {
            try
            {
                Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();
            }
            catch (Exception e)
            {
                e.getLocalizedMessage();
            }

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.three_amigos, menu);
        return true;
    }
    
}

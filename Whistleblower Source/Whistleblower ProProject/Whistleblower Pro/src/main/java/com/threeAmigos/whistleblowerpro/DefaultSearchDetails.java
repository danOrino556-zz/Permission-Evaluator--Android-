package com.threeAmigos.whistleblowerpro;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Daniel Robertson
 * 879118
 *
 * Secondary activity
 */


public class DefaultSearchDetails extends Activity
{
    ApplicationDetails requestedApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_default_search_details);

        if(getIntent().hasExtra("JSONreceived"))
        {
            Bundle appWanted = getIntent().getExtras();
            String appSearched = appWanted.getString("JSONreceived");
            parseDetailsJSON(appSearched);
        }
        if(requestedApp.getTitle() == null)
        {
            longPressToast("Oops!", "This application is currently unavailable on the Play store" );
            onBackPressed();
            finish();
        }
        setUI();
    }

    public void setUI()
    {
        //App Name
        TextView appName = (TextView) findViewById(R.id.textAppName);
        appName.setText(requestedApp.getTitle());

        //Play Rating
        TextView titlePlayRating = (TextView) findViewById(R.id.textTitlePlayRating);
        titlePlayRating.setText("Rating : " + requestedApp.getPlayRating());

        //Number of Downloads
        TextView titleDownloads = (TextView) findViewById(R.id.textTitleDownloads);
        titleDownloads.setText("Downloads : " + requestedApp.getNumDownloads());

        //App Author
        TextView titleAuthor = (TextView) findViewById(R.id.textTitleAuthor);
        titleAuthor.setText("Developer : " + requestedApp.getAuthor());

        //App Cost
        TextView titleCost = (TextView) findViewById(R.id.textTitleCost);
        titleCost.setText("Cost : " + requestedApp.getCost());

        //Permissions List
        ListView permissionList = (ListView) findViewById(R.id.listViewPermissions);
        TextView titlePermissions = (TextView) findViewById(R.id.textTitlePermissions);
        ArrayList<String> requestedAppPermissionList = requestedApp.getPermissions();
        Integer permissionCount = requestedAppPermissionList.size();
        titlePermissions.setText("Suspicious Permissions : " + permissionCount);
        CustomArrayAdapterPermissions myListAdapter = new CustomArrayAdapterPermissions(this,requestedAppPermissionList);
        permissionList.setAdapter(myListAdapter);
        permissionList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                ArrayList<String>requestedAppPermissionList = requestedApp.getPermissions();
                String stringToSearch = requestedAppPermissionList.get(position);
                Intent i = new Intent(getApplicationContext(), PermissionWebPageActivity.class);
                Bundle extras = new Bundle();
                extras.putString("permissionSearched", stringToSearch);
                i.putExtras(extras);
                startActivity(i);
                overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
            }
        });
        permissionList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                ArrayList<String>requestedAppPermissionList = requestedApp.getPermissions();
                String stringToSearch = requestedAppPermissionList.get(position);
                longPressToast("Permission Search", "On click : redirection to detailed information about the " + stringToSearch + "permission" );
                return true;
            }
        });

        //Googleplay Icon
        ImageView goToAppURL = (ImageView) findViewById(R.id.imageButtonAppURL);
        goToAppURL.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + requestedApp.getPackageName())));
                    overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                }
                catch (android.content.ActivityNotFoundException anfe)
                {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id="+requestedApp.getPackageName())));
                    overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                }
            }
        });
        goToAppURL.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                longPressToast("Googleplay", "On click : redirection to the Googleplay app page for " + requestedApp.getTitle());
                return true;
            }
        });

        //Help Button
        ImageButton helpButton = (ImageButton)findViewById(R.id.buttonHelpDetails);
        helpButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getApplicationContext(), DescriptionActivity.class);
                Bundle extras = new Bundle();
                extras.putString("helpInfo", getHelpString());
                i.putExtras(extras);
                startActivity(i);
                overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
            }
        });
        helpButton.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                longPressToast("Help", "On click : redirection to details of overall app functionality");
                return true;
            }
        });

        //Back Button
        ImageButton backButton = (ImageButton) findViewById(R.id.buttonBackDetails);
        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
                finish();
            }
        });
        backButton.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                longPressToast("Back", "On click : redirection back to the Application Search page");
                return true;
            }
        });

        //Settings Button
        ImageButton settingsButton = (ImageButton) findViewById(R.id.buttonSettingsDetailed);
        settingsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(DefaultSearchDetails.this, Settings.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
            }
        });
        settingsButton.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                longPressToast("Settings", "On click : redirection to developer page/ donate option");
                return true;
            }
        });

        //Application Icon
        ImageView appIcon = (ImageView) findViewById(R.id.iconDetails);
        int loader = R.drawable.ic_launcher;
        ImageHelper imgLoader = new ImageHelper(getApplicationContext());
        imgLoader.DisplayImage(requestedApp.getIcon(), loader, appIcon);
        appIcon.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + requestedApp.getPackageName())));
                    overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                }
                catch (android.content.ActivityNotFoundException anfe)
                {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id="+requestedApp.getPackageName())));
                    overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                }
            }
        });
        appIcon.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                longPressToast("Googleplay" , "On click : redirection to the Googleplay app page for " + requestedApp.getTitle());
                return true;
            }
        });

        //Description Button
        Button descriptionButton = (Button)findViewById(R.id.buttonDescription);
        descriptionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getApplicationContext(), DescriptionActivity.class);
                Bundle extras = new Bundle();
                extras.putString("AppSearched", requestedApp.getTitle());
                extras.putString("AppDescription", requestedApp.getDescription());
                i.putExtras(extras);
                startActivity(i);
                overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
            }
        });
        descriptionButton.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                longPressToast("Description :", "on click redirection to a description for "+ requestedApp.getTitle());
                return true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.default_search_details, menu);
        return true;
    }

    public void longPressToast(String textToDisplayTitle, String description)
    {
        LayoutInflater inflater = getLayoutInflater();

        View layout = inflater.inflate(R.layout.custom_toast_one,
                (ViewGroup) findViewById(R.id.relativeLayoutToast));


        Toast myToastMessage = new Toast(getApplicationContext());

        TextView toastText = (TextView) layout.findViewById(R.id.textViewToast);
        toastText.setText(textToDisplayTitle);

        TextView toastDescription = (TextView)layout.findViewById(R.id.textViewToastDescription);
        toastDescription.setText(description);

        myToastMessage.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        myToastMessage.setDuration(Toast.LENGTH_LONG);
        myToastMessage.setView(layout);
        myToastMessage.show();
    }


    private void parseDetailsJSON(String JSONString)
    {
        JSONParser myJSONParser = new JSONParser();
        requestedApp = myJSONParser.parseDetailsJSON(JSONString);
    }


    public String getHelpString()
    {
        AssetManager assetManager = getAssets();
        InputStream inputStream = null;
        String text = new String();
        try
        {
            inputStream = assetManager.open("helpInfoDetails");
            text = loadTextFile(inputStream);
        }
        catch (IOException e)
        {
            Toast.makeText(getApplicationContext(), "couldnt open sampleJSON", Toast.LENGTH_LONG).show();
        }
        finally
        {
            if (inputStream != null)
                try {
                    inputStream.close();
                }
                catch (IOException e)
                {
                    Toast.makeText(getApplicationContext(), "couldnt close", Toast.LENGTH_LONG).show();
                }
        }
        return text;
    }

    //Used by getJSONString
    public String loadTextFile(InputStream inputStream) throws IOException
    {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        byte[] bytes = new byte[4096];
        int len = 0;
        while ((len = inputStream.read(bytes)) > 0)
            byteStream.write(bytes, 0, len);
        return new String(byteStream.toByteArray(), "UTF8");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
    }
    
}

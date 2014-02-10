package com.threeAmigos.whistleblowerpro;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Looper;
import android.speech.RecognizerIntent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Daniel Robertson
 * 879118
 *
 * Main Activity
 *
 */


public class DefaultSearchList extends Activity
{
    ArrayList <ApplicationSearched> searchedApps;
    public static final int REQUEST_OK = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_default_search_list);

        if(getIntent().hasExtra("JSONreceived"))
        {
            Bundle JSONreceived = getIntent().getExtras();
            //Toast.makeText(getApplicationContext(), "Started with : " + "\n" + JSONreceived.getString("JSONreceived"), Toast.LENGTH_LONG).show();
            parseSearchJSON(JSONreceived.getString("JSONreceived"));
        }
        else
        {
            getInstalledAppInfo();
            //Toast.makeText(getApplicationContext(), "Didnt start with anything", Toast.LENGTH_LONG).show();
        }
        create_UI_elements();
    }


    //Linking the UI elements to the xml file
    private void create_UI_elements()
    {
        //Search text box
        final EditText searchText = (EditText) findViewById(R.id.text_search_field);

        //Progress bar - will stay invisible until the user attempts to search
        final ProgressBar loadingBar = (ProgressBar)findViewById(R.id.progressBarLoading);
        loadingBar.setVisibility(View.GONE);

        //Mic Button - results from the on create method are received by overriding the
        //              onActivityResults method at the bottom of the file
        ImageButton micControl = (ImageButton)findViewById(R.id.imageButtonMic);
        micControl.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
                try
                {
                    startActivityForResult(i, REQUEST_OK);
                } catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), "Error initializing speech to text engine.", Toast.LENGTH_LONG).show();
                }
            }
        });
        micControl.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                longPressToast("Voice Search", "Spoken queries will populate the search box");
                return true;
            }
        });


        //Search Market Button
        ImageButton searchButton = (ImageButton) findViewById(R.id.button_search);
        searchButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (searchText.getText().toString() != "")
                {
                    loadingBar.setVisibility(View.VISIBLE);
                    //Get the desired string and send it as a JSON object
                    final String searchedAppName = searchText.getText().toString();
                    //Server locations
                    final String urlForSearch = "http://spynot.ngrok.com/api";

                    Thread t = new Thread() {

                        public void run()
                        {
                            Looper.prepare(); //For Preparing Message Pool for the child Thread
                            HttpClient client = new DefaultHttpClient();
                            HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
                            HttpResponse response;
                            JSONObject json = new JSONObject();

                            //Sending a message to the server
                            try
                            {
                                Integer myID = 1;
                                HttpPost post = new HttpPost(urlForSearch);
                                json.put("method", "SearchApp");
                                json.put("id", myID);
                                json.put("searchString", searchedAppName);
                                StringEntity se = new StringEntity( json.toString());
                                se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                                post.setEntity(se);
                                response = client.execute(post);

                                /*Checking response */
                                if(response!=null)
                                {

                                    InputStream in = response.getEntity().getContent(); //Get the data in the entity
                                    //Toast.makeText(getApplicationContext(), "established a connection and received a response", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(getApplicationContext(), DefaultSearchList.class);
                                    Bundle extras = new Bundle();
                                    extras.putString("AppSearched", searchedAppName);
                                    extras.putString("JSONreceived", loadTextFile(in));
                                    i.putExtras(extras);
                                    startActivity(i);
                                    overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                                    finish();
                                }

                            }
                            catch(Exception e)
                            {
                                e.printStackTrace();
                                //Toast.makeText(getApplicationContext(), "couldnt establish connection", Toast.LENGTH_LONG).show();
                            }

                            Looper.loop(); //Loop in the message queue
                        }
                    };
                    t.start();
                }
            }
        });

        searchButton.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                longPressToast("Search Marketplace", "On click : the application name entered will be searched for in the Googleplay");
                return true;
            }
        });

        //Back Button
        ImageButton backButton = (ImageButton) findViewById(R.id.button_back);
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
                longPressToast("Back", "On click : Quit the application");
                return true;
            }
        });

        //Settings Button
        ImageButton settingsButton = (ImageButton) findViewById(R.id.button_settings);
        settingsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(DefaultSearchList.this, Settings.class);
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

        //Help Button
        ImageButton helpButton = (ImageButton) findViewById(R.id.buttonHelpSearch);
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
                longPressToast("Help", "On click : details of overall app functionality will be displayed");
                return true;
            }
        });

        final ListView appListView = (ListView)findViewById(R.id.list_view_main);
        final CustomArrayAdapter myListAdapter = new CustomArrayAdapter(DefaultSearchList.this,searchedApps);
        appListView.setAdapter(myListAdapter);


        appListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                loadingBar.setVisibility(View.VISIBLE);
                //Get the desired string and send it as a JSON object
                final String stringToSearch = searchedApps.get(position).getPackageName();
                final String urlForDetails = "http://spynot.ngrok.com/api";

                Thread t = new Thread()
                {

                    public void run()
                    {
                        Looper.prepare(); //For Preparing Message Pool for the child Thread
                        HttpClient client = new DefaultHttpClient();
                        HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
                        HttpResponse response;
                        JSONObject json = new JSONObject();

                        try
                        {
                            Integer myID = 1;
                            HttpPost post = new HttpPost(urlForDetails);
                            json.put("method", "GetDetails");
                            json.put("id", myID);
                            json.put("searchString", stringToSearch);
                            StringEntity se = new StringEntity( json.toString());
                            se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                            post.setEntity(se);
                            response = client.execute(post);

                            /*Checking response */
                            if(response!=null)
                            {
                                InputStream in = response.getEntity().getContent(); //Get the data in the entity
                                //Toast.makeText(getApplicationContext(), "established a connection and received a response", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(getApplicationContext(), DefaultSearchDetails.class);
                                Bundle extras = new Bundle();
                                extras.putString("AppSearched", stringToSearch);
                                extras.putString("JSONreceived", loadTextFile(in));
                                i.putExtras(extras);
                                startActivity(i);
                                overridePendingTransition(R.anim.slide_up, R.anim.slide_down);

                                loadingBar.setVisibility(View.GONE);
                            }

                        } catch(Exception e)
                        {
                            e.printStackTrace();
                            //Toast.makeText(getApplicationContext(), "couldnt establish connection", Toast.LENGTH_LONG).show();
                        }
                        Looper.loop(); //Loop in the message queue
                    }
                };
                t.start();
            }
        });

        appListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                longPressToast("Search " + searchedApps.get(position).getTitle(), "On press you will receive detailed information about " + searchedApps.get(position).getTitle());
                return true;
            }
        });

        //Search Installed Apps Button
        ImageButton searchInstalledButton = (ImageButton) findViewById(R.id.button_search_installed);
        searchInstalledButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(DefaultSearchList.this, DefaultSearchList.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
                finish();
            }
        });
        searchInstalledButton.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                longPressToast("Search Installed", "On click : all non system applications on your machine will be searched for");
                return true;
            }
        });
    }


    //Displays a custom Toast message to the user
    //Layout is definied in custom_toast_one
    //Pic on the left / Text on the right is broken down into title and description
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

    private void getInstalledAppInfo()
    {
        boolean includeSysApps = false;
        ArrayList<ApplicationSearched> apps = new ArrayList<ApplicationSearched>();
        // the package manager contains the information about all installed apps
        PackageManager packageManager = getPackageManager();
        List<PackageInfo> packs = packageManager.getInstalledPackages(0); //PackageManager.GET_META_DATA

        for(int i=0; i < packs.size(); i++)
        {
            PackageInfo p = packs.get(i);
            ApplicationInfo a = p.applicationInfo;
            // skip system apps if they shall not be included
            if ((!includeSysApps) && ((a.flags & ApplicationInfo.FLAG_SYSTEM) == 1))
            {
                continue;
            }
            ApplicationSearched app = new ApplicationSearched();
            app.setTitle(p.applicationInfo.loadLabel(packageManager).toString());
            app.setPackageName(p.packageName);
            app.setVersionName(p.versionName);
            app.setAppIcon(p.applicationInfo.loadIcon(packageManager));
            apps.add(app);
        }
       searchedApps = apps;
    }


    private void parseSearchJSON(String JSONString)
    {
        JSONParser myJSONParser = new JSONParser();
        searchedApps = myJSONParser.parseSearchJSON(JSONString);
    }


    public String getHelpString()
    {
        AssetManager assetManager = getAssets();
        InputStream inputStream = null;
        String text = new String();
        try
        {
            inputStream = assetManager.open("helpInfo");
            text = loadTextFile(inputStream);
            //Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
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
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.default_search_list, menu);
        return true;
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
    }

    //Used for receiving the results from a voice command
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_OK  && resultCode==RESULT_OK)
        {
            ArrayList<String> thingsYouSaid = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            ((TextView)findViewById(R.id.text_search_field)).setText(thingsYouSaid.get(0));
            findViewById(R.id.button_search).performClick();


        }
    }
}





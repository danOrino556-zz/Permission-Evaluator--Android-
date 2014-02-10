package com.threeAmigos.whistleblowerpro;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by danielrobertson on 10/14/13.
 *
 * This class takes JSON objects and transfers their content into Application Details
 * and ApplicationsSearched objects.
 */
public class JSONParser
{

    public JSONParser()
    {
    }

    public String readJSONFeed(String URL) {
        StringBuilder stringBuilder = new StringBuilder();
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(URL);
        try
        {
            HttpResponse response = httpClient.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200)
            {
                HttpEntity entity = response.getEntity();
                InputStream inputStream = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null)
                {
                    stringBuilder.append(line);
                }
                inputStream.close(); }
            else
            {
                Log.d("readJSONFeed", "Failed to download file");
            }
        }
        catch (Exception e)
        {
            Log.d("readJSONFeed", e.getLocalizedMessage());
        }
        return stringBuilder.toString();
    }


    //User has given an input string
    //The resulting JSON String will hold a few fields for each app that matches the searched string
    public ArrayList<ApplicationSearched> parseSearchJSON(String incomingJSONString)
    {
        ArrayList <ApplicationSearched> applicationListToReturn = new ArrayList<ApplicationSearched>();
        try
        {
            JSONObject incomingJSON = new JSONObject(incomingJSONString);

            String id = incomingJSON.getString("id");
            String searchString = incomingJSON.getString("searchString");

            // Getting Array of applications that match the search
            JSONArray searchedApplications = incomingJSON.getJSONArray("results");


            // looping through the information for each application
            for(int i = 0; i < searchedApplications.length(); i++)
            {
                JSONObject appInJSONArray = searchedApplications.getJSONObject(i);
                ApplicationSearched newApp = new ApplicationSearched();

                // Storing each json item in variable
                newApp.setPlayRating(appInJSONArray.getString("playRating"));
                newApp.setAuthor(appInJSONArray.getString("author"));
                newApp.setSuperDeveloper(appInJSONArray.getString("superDeveloper"));
                newApp.setIcon(appInJSONArray.getString("icon"));
                newApp.setTitle(appInJSONArray.getString("title"));
                newApp.setPackageName(appInJSONArray.getString("packageName"));
                applicationListToReturn.add(newApp);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return applicationListToReturn;
    }

    //User has selected an app from the ListView
    //Now we will get more specific information for the app thats selected
    public ApplicationDetails parseDetailsJSON(String incomingJSONString)
    {
        ApplicationDetails applicationToReturn = new ApplicationDetails();
        try
        {
            JSONObject incomingJSON = new JSONObject(incomingJSONString);

            JSONObject appDetailJSON = incomingJSON.getJSONObject("GooglePlayData");


            applicationToReturn.setPlayRating(appDetailJSON.getString("playRating"));
            applicationToReturn.setDescription(appDetailJSON.getString("description"));
            applicationToReturn.setAuthor(appDetailJSON.getString("author"));
            applicationToReturn.setUrl(appDetailJSON.getString("url"));
            applicationToReturn.setTitle(appDetailJSON.getString("title"));
            applicationToReturn.setPlayRating(appDetailJSON.getString("playRating"));
            applicationToReturn.setCost(appDetailJSON.getString("cost"));
            applicationToReturn.setPackageName(appDetailJSON.getString("packageName"));

            //Apps of course have multiple permissions
            JSONArray appPermissions = appDetailJSON.getJSONArray("permissions");
            // looping permissions array for each application
            for(int index = 0; index < appPermissions.length(); index++)
            {
              applicationToReturn.addPermission(appPermissions.getString(index));
            }
            applicationToReturn.setIcon(appDetailJSON.getString("icon"));
            applicationToReturn.setNumDownloads(appDetailJSON.getString("numDownloads"));

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return applicationToReturn;
    }

    private String DownloadText(String URL)
    { int BUFFER_SIZE = 2000;
        InputStream in = null;
        try
        {
            in = OpenHttpGETConnection(URL);
        }
        catch (Exception e)
        {
            Log.d("DownloadText", e.getLocalizedMessage());
            return "";
        }
        InputStreamReader isr = new InputStreamReader(in); int charRead;
        String str = "";
        char[] inputBuffer = new char[BUFFER_SIZE];
        try
        {
            while ((charRead = isr.read(inputBuffer)) > 0)
            {
                // ---convert the chars to a String---
                String readString = String.copyValueOf(inputBuffer, 0, charRead);
                str += readString;
                inputBuffer = new char[BUFFER_SIZE];
            }
            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return "";
        }
        return str;
    }

    public static InputStream OpenHttpGETConnection(String url)
    {
        InputStream inputStream = null; try {
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse httpResponse = httpclient.execute(new HttpGet(url)); inputStream = httpResponse.getEntity().getContent();
    } catch (Exception e) {
        Log.d("InputStream", e.getLocalizedMessage());
    }
        return inputStream;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

    public static DefaultHttpClient getThreadSafeClient()
    {
        DefaultHttpClient client = new DefaultHttpClient();
        ClientConnectionManager mgr = client.getConnectionManager();
        HttpParams params = client.getParams();

        client = new DefaultHttpClient(new ThreadSafeClientConnManager(params,
                mgr.getSchemeRegistry()), params);

        return client;
    }

}

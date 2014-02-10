package com.threeAmigos.whistleblowerpro;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Daniel Robertson
 * 879118
 *
 *
 */

public class Settings extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Disable the titlebar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_settings);

        //Back Button
        ImageButton backButton = (ImageButton) findViewById(R.id.button_back_settings);
        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
                finish();
            }
        });

        //Facebook Title Text
        TextView facebookTitle = (TextView) findViewById(R.id.textTitleFB);
        facebookTitle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(Settings.this, FacebookWebPageActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
            }
        });

        //PayPal Title Text
        TextView paypalTitle = (TextView) findViewById(R.id.textTitlePP);
        paypalTitle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(Settings.this, PayPalWebActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
            }
        });

        //Facebook Button
        ImageView facebookButton = (ImageView)findViewById(R.id.imageButtonFacebook);
        facebookButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(Settings.this, FacebookWebPageActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
            }
        });

        //PayPal Button
        ImageView payPalButton = (ImageView) findViewById(R.id.imageButtonPayPal);
        payPalButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(Settings.this, PayPalWebActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
            }
        });

        //Help Button
        ImageButton settingsButton = (ImageButton) findViewById(R.id.buttonHelpSettings);
        settingsButton.setOnClickListener(new View.OnClickListener()
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
        settingsButton.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                longPressToast("Help", "On click : details of overall app functionality will be displayed");
                return true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }


    public String getAsset(String assetToSearch)
    {
        AssetManager assetManager = getAssets();
        InputStream inputStream = null;
        String text = new String();
        try
        {
            inputStream = assetManager.open(assetToSearch);
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

    public String loadTextFile(InputStream inputStream) throws IOException
    {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        byte[] bytes = new byte[4096];
        int len = 0;
        while ((len = inputStream.read(bytes)) > 0)
            byteStream.write(bytes, 0, len);
        return new String(byteStream.toByteArray(), "UTF8");
    }

    public String getHelpString()
    {
        AssetManager assetManager = getAssets();
        InputStream inputStream = null;
        String text = new String();
        try
        {
            inputStream = assetManager.open("helpInfoSettings");
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
    }
}

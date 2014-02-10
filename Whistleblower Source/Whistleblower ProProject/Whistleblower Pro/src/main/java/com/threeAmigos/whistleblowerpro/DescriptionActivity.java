package com.threeAmigos.whistleblowerpro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class DescriptionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_description);

        Bundle appWanted = getIntent().getExtras();
        TextView applicationDescription = (TextView) findViewById(R.id.textViewAppDescription);
        TextView descriptionTitle = (TextView)findViewById(R.id.textViewTitleDescription);


        ImageButton backButton = (ImageButton) findViewById(R.id.buttonBackDetails);
        ImageButton settingsButton = (ImageButton) findViewById(R.id.buttonSettingsDetailed);

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
                Intent i = new Intent(DescriptionActivity.this, Settings.class);
                startActivity(i);
            }
        });

        if (getIntent().hasExtra("helpInfo"))
        {
            applicationDescription.setText(appWanted.getString("helpInfo"));
            descriptionTitle.setText("Help");
        }

        else if(appWanted.getString("AppDescription") == null)
        {
            longPressToast("Warning!", "This application does not have a description");
            onBackPressed();
        }
        else
        {
            applicationDescription.setText(Html.fromHtml(appWanted.getString("AppDescription")));
        }
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.description, menu);
        return true;
    }
    
}

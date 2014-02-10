package com.threeAmigos.whistleblowerpro;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by danielrobertson on 10/16/13.
 *
 * This custom array adapter will fill the Listview in the Main title screen
 *
 * It uses the layout file item_view
 *
 * item_view has an icon to the left / large text to the right / smaller text below
 *
 * This ArrayAdapter will take an ApplicationSearched as input. It then fills each row
 * with the object that has been inserted.....ie the getView() function will repeatedly be called
 */

public class CustomArrayAdapter extends ArrayAdapter<ApplicationSearched>
{
    private final Activity context;
    private ArrayList<ApplicationSearched> applications;

    public CustomArrayAdapter(Activity context, ArrayList<ApplicationSearched> apps)
    {
        super(context, R.layout.item_view, apps);
        this.context = context;
        this.applications = apps;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        View rowView = view;

        if (rowView == null)
        {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            rowView = vi.inflate(R.layout.item_view, null);
        }

        ApplicationSearched app = applications.get(position);
        TextView title = (TextView) rowView.findViewById(R.id.txtAppName);
        TextView description = (TextView) rowView.findViewById(R.id.txtAppDescription);
        ImageView icon = (ImageView)rowView.findViewById(R.id.listIcon);


        /*
         * This next part is a little trickier
         * This listAdapter receives data from two different sources :
         * 1) searched apps on th machine and 2) searched apps from the market
         *
         * apps from the machine contain a drawable for an icon. apps from the market contain
         * a url for the icon.
         *
         * The following logic checks to see which type of icon it is receiving, That way we know what
         * to populate the view with
         */

        //App from the marketplace
        if (app != null &&  app.getAppIcon() == null)
        {
            int loader = R.drawable.ic_launcher;
            ImageHelper imgLoader = new ImageHelper(context);
            imgLoader.DisplayImage(app.getIcon(), loader, icon);
            title.setText(app.getTitle());
            description.setText("Author : " + app.getAuthor() + "\nPlay Rating : " + app.getPlayRating());
        }

        //App from the machine
        else
        {
            title.setText(app.getTitle());
            description.setText("Version : " + app.getVersionName());
            icon.setImageDrawable(app.getAppIcon());
        }
        return rowView;
    }

}

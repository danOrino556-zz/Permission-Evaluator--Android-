package com.threeAmigos.whistleblowerpro;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by danielrobertson on 10/20/13.
 * This custom array adapter will fill the Listview in the Applications details activity
 *
 * It uses the layout file item_view
 *
 * item_view has an icon to the left / large text to the right / smaller text below
 *
 * This ArrayAdapter will take an String permission as input. It then fills each row
 * with the object that has been inserted.....ie the getView() function will repeatedly be called
 */

public class CustomArrayAdapterPermissions extends ArrayAdapter<String>
{
    private final Activity context;
    private ArrayList<String> permissionList;

    public CustomArrayAdapterPermissions(Activity context, ArrayList<String> permissions)
    {
        super(context, R.layout.item_view, permissions);
        this.context = context;
        this.permissionList = permissions;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        //print the index of the row to examine---
        Log.d("CustomArrayAdapter", String.valueOf(position));
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.item_view, null, true);

        //get a reference to all the views on the xml layout
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txtAppName);
        TextView txtDescription = (TextView)rowView.findViewById(R.id.txtAppDescription);
        ImageView listIcon = (ImageView) rowView.findViewById(R.id.listIcon);
        listIcon.setImageResource(android.R.drawable.sym_def_app_icon);

        //customize the content of each row based on position
        txtTitle.setText(permissionList.get(position));
        return rowView;
    }


    private Drawable LoadImageFromWebOperations(String url)
    {

        try
        {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        }
        catch (Exception e)
        {
            System.out.println("Exc=" + e);
            return null;
        }

    }
}

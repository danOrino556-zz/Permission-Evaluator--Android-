package com.threeAmigos.whistleblowerpro;

import android.graphics.drawable.Drawable;

/**
 * Created by danielrobertson on 10/19/13.
 *
 * This class contains all the information for one object which has been returned by a non-detailed
 * user search. The results of that search(from the device/server) are used to fill the data members
 * of this class. The DefaultSearchList Activity then uses this list full of objects to populate the
 * main search ListView.
 *
 * The objects relating to this class resides in the DefaultSearchList class. DefaultSearchList
 * contains an ArrayList that contains all of the ApplicationsSearched objects that have been
 * populate by either A)querying the marketplace or B)querying the device
 */
public class ApplicationSearched
{

    //private variables
    private String playRating;
    private String author;
    private String superDeveloper;
    private String icon;
    private String title;
    private String packageName;
    private Drawable appIcon = null;
    private String versionName;


    //setters and getters (in order)
    public String getPlayRating()
    {
        return playRating;
    }

    public void setPlayRating(String playRating)
    {
        this.playRating = playRating;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getSuperDeveloper()
    {
        return superDeveloper;
    }

    public void setSuperDeveloper(String superDeveloper)
    {
        this.superDeveloper = superDeveloper;
    }

    public String getIcon()
    {
        return icon;
    }

    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getPackageName()
    {
        return packageName;
    }

    public void setPackageName(String packageName)
    {
        this.packageName = packageName;
    }

    public Drawable getAppIcon()
    {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon)
    {
        this.appIcon = appIcon;
    }

    public String getVersionName()
    {
        return versionName;
    }

    public void setVersionName(String versionName)
    {
        this.versionName = versionName;
    }
}

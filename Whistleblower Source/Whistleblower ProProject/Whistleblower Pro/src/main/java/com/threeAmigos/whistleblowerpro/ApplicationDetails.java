package com.threeAmigos.whistleblowerpro;

import java.util.ArrayList;

/**
 * Created by danielrobertson on 10/19/13.
 *
 * This class contains all of the info that is retrieved from querying the server for
 * a specific app. i.e., The server has already returned a list of all possible results
 * relating the the inputted string.....and now the user has selected one application
 * within that list and is requesting more information on it.
 *
 * The object relating to this class resides in the DefaultSearchDetails class. DefaultSearchDetails
 * contains one reference to a Applications Details object which it uses to populate the data
 * within its activity.
 */
public class ApplicationDetails
{

    //priavte members - all have setters/getters
    private String category;
    private String description;
    private String author;
    private String url;
    private String title;
    private String playRating;
    private String cost;
    private ArrayList<String> permissions = new ArrayList<String>();
    private String icon;
    private String numDownloads;
    private String riskRating;
    private String packageName;


    //Setters and getters
    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getPlayRating()
    {
        return playRating;
    }

    public void setPlayRating(String playRating)
    {
        this.playRating = playRating;
    }

    public String getCost()
    {
        return cost;
    }

    public void setCost(String cost)
    {
        this.cost = cost;
    }

    public ArrayList<String> getPermissions()
    {
        return permissions;
    }

    public Integer getPermissionSize()
    {
        return permissions.size();
    }

    public void setPermissions(ArrayList<String> permissions)
    {
        this.permissions = permissions;
    }

    public void addPermission(String permission)
    {
        permissions.add(permission);
    }

    public String getIcon()
    {
        return icon;
    }

    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    public String getNumDownloads()
    {
        return numDownloads;
    }

    public void setNumDownloads(String numDownloads)
    {
        this.numDownloads = numDownloads;
    }

    public String getRiskRating()
    {
        return riskRating;
    }

    public void setRiskRating(String riskRating)
    {
        this.riskRating = riskRating;
    }

    public String getPackageName()
    {
        return packageName;
    }

    public void setPackageName(String packageName)
    {
        this.packageName = packageName;
    }
}

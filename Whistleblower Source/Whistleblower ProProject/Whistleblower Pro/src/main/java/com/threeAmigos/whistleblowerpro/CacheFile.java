package com.threeAmigos.whistleblowerpro;


import android.content.Context;

import java.io.File;

/**
 * Daniel Robertson
 * 879118
 *
 * Used in conjunction with ImageHelper and FileCache class.
 *
 * This class allows for the writing of temp files on the machine. This is used
 * when the ImageHelper class loads an icon from an external website in order to populate
 * the DefaultSearchDetails and DefaultSearchList activities
 *
 * The actual use of ImageHelper (which in turn utilizes this class) Happens in the CustomArray
 * Adapter, CustomArrayAdapterPermissions, and DetailedSearchList classes
 *
 * CustomArrayAdapter populates the initial search list. That listview is located inside of
 * the DefaultSearchList activity
 *
 * CustomArrayAdapter populates the permissions list. That listview is located inside of the
 * DefaultSearchDetails activity
 *
 * DefaultSearchList activity uses it to populate and icon in the top right corner
 */


public class CacheFile
{

    private File cacheDir;

    public CacheFile(Context context)
    {
        //Find the dir to save cached images
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),"TempImages");
        else
            cacheDir=context.getCacheDir();
        if(!cacheDir.exists())
            cacheDir.mkdirs();
    }

    public File getFile(String url)
    {
        String filename=String.valueOf(url.hashCode());
        File f = new File(cacheDir, filename);
        return f;

    }

    public void clear()
    {
        File[] files=cacheDir.listFiles();
        if(files==null)
        {
            return;
        }
        for(File f:files)
        {
            f.delete();
        }
    }

}
package com.threeAmigos.whistleblowerpro;


import android.graphics.Bitmap;

import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


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

public class CacheMemory
{
    private Map<String, SoftReference<Bitmap>> cache=Collections.synchronizedMap(new HashMap<String, SoftReference<Bitmap>>());

    public Bitmap get(String id)
    {
        if(!cache.containsKey(id))
            return null;
        SoftReference<Bitmap> ref=cache.get(id);
        return ref.get();
    }

    public void put(String id, Bitmap bitmap)
    {
        cache.put(id, new SoftReference<Bitmap>(bitmap));
    }

    public void clear()
    {
        cache.clear();
    }
}
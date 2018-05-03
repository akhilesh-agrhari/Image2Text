package com.aagah.mobapps.image2text;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;


public class UriToPath {
    public static String getPath(Uri uri,Context C)
    {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = C.getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index =             cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s=cursor.getString(column_index);
        cursor.close();
        Log.e("URI TO PATH : ","RETURNING ACTUAL PATH");
        return s;
    }


}
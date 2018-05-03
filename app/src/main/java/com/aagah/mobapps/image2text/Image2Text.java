package com.aagah.mobapps.image2text;


import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.SparseArray;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

public class Image2Text {

    Bitmap image;
    Context context;
    Image2Text(Bitmap image, Context context)
    {
        Log.e("Image2Text class : ","constructor");
        this.image = image;
        this.context = context;
    }

    public String[] getText()
    {
        Log.e("Image2Text class : ","getText");
        String []string = new String[100];
        TextRecognizer textRecognizer = new TextRecognizer.Builder(context).build();
        if(!textRecognizer.isOperational())
        {
            string[100]="ERROR";

        }
        else
        {
            int i;
            Frame frame = new Frame.Builder().setBitmap(image).build();
            SparseArray<TextBlock> textBlockSparseArray = textRecognizer.detect(frame);
            for( i =0;i<textBlockSparseArray.size();i++)
            {
                Log.e("getText : ","loop");
                TextBlock textBlock = textBlockSparseArray.valueAt(i);
                string[i] = textBlock.getValue();
            }
            string[i]="EOS";
        }
        Log.e("getText : ","returning");
        return string;
    }


}

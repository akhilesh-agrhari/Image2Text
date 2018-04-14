package com.aagah.mobapps.image2text;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;

public class ImageActivity extends AppCompatActivity {
    ImageView imageView ;
    //File file = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("msg","indsie ImageActivity");
        setContentView(R.layout.activity_image);
        imageView = findViewById(R.id.imageView);
        Bundle extras = getIntent().getExtras();
        String image_path = extras.getString("image_path");



        File file = new File(image_path);
        if(file.exists())
        {
            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            Log.e("msg", "photo path = "+file.getAbsolutePath());
            imageView.setImageBitmap(myBitmap);

        }
        else{
            Log.e("msg","file does not exists");
        }
        /*
        File imgFile = new  File("/sdcard/Images/test_image.jpg");

    if(imgFile.exists()){

    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

    ImageView myImage = (ImageView) findViewById(R.id.imageviewTest);

    myImage.setImageBitmap(myBitmap);

}
         */

       // imageView.setImageBitmap();

    }
}

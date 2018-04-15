package com.aagah.mobapps.image2text;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;

public class ImageActivity extends AppCompatActivity {
    ImageView imageView ;
    private File file = null;
    ImageButton btnrotateleft,btnrotateright,btncrop;
    Matrix matrix;
    Bitmap drawableImage;
    float fromdegree = 0,todegree=0;
    boolean rotationChanged=false;
    //File file = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("msg","indsie ImageActivity");
        setContentView(R.layout.activity_image);
        imageView = findViewById(R.id.imageView);
        Bundle extras = getIntent().getExtras();
        String image_path = extras.getString("image_path");
        btnrotateleft = findViewById(R.id.btnrotateleft);
        btnrotateright = findViewById(R.id.btnrotateright);
        btncrop = findViewById(R.id.btncrop);


        file = new File(image_path);
        if(file.exists())
        {
            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            Log.e("msg", "photo path = "+file.getAbsolutePath());
            imageView.setImageBitmap(myBitmap);
            drawableImage = myBitmap.copy(myBitmap.getConfig(),true);



        }
        else{
            Log.e("msg","file does not exists");
        }


        btnrotateleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rotationChanged = true;
                if(todegree==-360)todegree=0;
                todegree=todegree-90;
                rotate(fromdegree,todegree);
                fromdegree=todegree;


                /*matrix = new Matrix();
                    matrix.postRotate(-90);
                    drawableImage = Bitmap.createBitmap(drawableImage,0,0,drawableImage.getWidth(),drawableImage.getHeight(),matrix,true);
                    imageView.setImageBitmap(drawableImage);*/
            }
        });

        btnrotateright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rotationChanged = true;
                if (todegree==360)todegree=0;
                todegree=todegree+90;
                rotate(fromdegree,todegree);
                fromdegree=todegree;
               /* matrix = new Matrix();
                matrix.preRotate(90);
                drawableImage = Bitmap.createBitmap(drawableImage,0,0,drawableImage.getWidth(),drawableImage.getHeight(),matrix,true);
                imageView.setImageBitmap(drawableImage);*/
            }
        });

        btncrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disableButton();


                enableButton();
            }
        });



    }

    private void enableButton() {
        btnrotateright.setClickable(true);
        btnrotateleft.setClickable(true);
    }

    private void disableButton() {
        if(rotationChanged)
        {
            matrix = new Matrix();
            matrix.postRotate(todegree);
            drawableImage = Bitmap.createBitmap(drawableImage,0,0,drawableImage.getWidth(),drawableImage.getHeight(),matrix,true);
            imageView.setImageBitmap(drawableImage);
        }
        btnrotateright.setClickable(false);
        btnrotateleft.setClickable(false);
    }

    private void rotate(float fromdegree,float todegree) {
        final RotateAnimation rotateAnim = new RotateAnimation(fromdegree, todegree,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);

        rotateAnim.setDuration(1000);
        rotateAnim.setFillAfter(true);
        imageView.startAnimation(rotateAnim);
    }
}

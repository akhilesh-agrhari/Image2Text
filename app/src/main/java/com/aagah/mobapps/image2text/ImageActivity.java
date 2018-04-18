package com.aagah.mobapps.image2text;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.annotation.Px;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class ImageActivity extends AppCompatActivity {
    ImageView imageView ;
    private File file = null;
    ImageButton btnrotateleft,btnrotateright,btncrop;
    Matrix matrix;
    Bitmap drawableImage;
    float fromdegree = 0,todegree=0;
    boolean rotationChanged=false,isHoritzontalOver=false;
    @Px int left=0,top=50,right=0,bottom=50;
    FrameLayout.LayoutParams lp1,lp2;

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
        lp1 = new FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
        lp2 = new FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT);
        lp1.setMargins(0, 135, 0, 135);
        lp2.setMargins(0,0,0,0);



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
                if(todegree==-360){todegree=fromdegree=0;}
                todegree=todegree-90;

                if(isHoritzontalOver)isHoritzontalOver=false;
                else isHoritzontalOver=true;
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
                if (todegree==360){todegree=fromdegree=0;}
                todegree=todegree+90;


               if(isHoritzontalOver)isHoritzontalOver=false;
                else isHoritzontalOver=true;
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
    /*



     */
    private void disableButton() {
        if(rotationChanged)
        {
            rotationChanged = false;
            matrix = new Matrix();
           // matrix.postRotate(todegree);
            matrix.setRotate(imageView.getRotation());

            drawableImage = Bitmap.createBitmap(drawableImage,0,0,drawableImage.getWidth(),drawableImage.getHeight(),matrix,true);
            imageView.setImageBitmap(drawableImage);
            Toast.makeText(ImageActivity.this,"inside Disable button",Toast.LENGTH_SHORT).show();
        }
        btnrotateright.setClickable(false);
        btnrotateleft.setClickable(false);
    }

    private void rotate(float fromdegree,float todegree) {

            final RotateAnimation rotateAnim = new RotateAnimation(fromdegree, todegree,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnim.setDuration(0);
            rotateAnim.setFillAfter(true);
           if (isHoritzontalOver)
           {
               imageView.setLayoutParams(lp1);
           }
           else
               imageView.setLayoutParams(lp2);

            imageView.startAnimation(rotateAnim);
    }
}

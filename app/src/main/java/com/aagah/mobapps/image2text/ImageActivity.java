package com.aagah.mobapps.image2text;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
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

import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;
import it.sephiroth.android.library.imagezoom.graphics.IBitmapDrawable;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class ImageActivity extends AppCompatActivity {
    ImageViewTouch imageView ;
    private File file = null;
    ImageButton btnrotateleft,btnrotateright,btncrop,nextClick;
    Matrix matrix;
    Bitmap drawableImage;
    float fromdegree = 0,todegree=0;
    boolean rotationChanged=false,isHoritzontalOver=false;
    @Px int left=0,top=50,right=0,bottom=50;
    FrameLayout.LayoutParams lp1,lp2;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("msg","indsie ImageActivity");
        setContentView(R.layout.activity_image);
        imageView =(ImageViewTouch) findViewById(R.id.imageView);
        Bundle extras = getIntent().getExtras();
        String image_path = extras.getString("image_path");
        btnrotateleft = findViewById(R.id.btnrotateleft);
        btnrotateright = findViewById(R.id.btnrotateright);
        btncrop = findViewById(R.id.btncrop);
        nextClick = findViewById(R.id.nextClick);
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

        imageView.setSingleTapListener(
                new ImageViewTouch.OnImageViewTouchSingleTapListener() {

                    @Override
                    public void onSingleTapConfirmed() {
                        Log.d("ImageActivity : ", "onSingleTapConfirmed");
                    }
                }
        );

        imageView.setDoubleTapListener(
                new ImageViewTouch.OnImageViewTouchDoubleTapListener() {

                    @Override
                    public void onDoubleTap() {
                        Log.d("ImageActivity", "onDoubleTap");
                    }
                }
        );

        imageView.setOnDrawableChangedListener(
                new ImageViewTouchBase.OnDrawableChangeListener() {

                    @Override
                    public void onDrawableChanged(Drawable drawable) {
                        Log.i("ImageActivity", "onBitmapChanged: " + drawable);
                    }
                }
        );


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

        nextClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disableButton();
                Image2Text image2Text = new Image2Text(drawableImage,getApplicationContext());
                String string[] = image2Text.getText();
                startNextActivity(string);
            }
        });



    }

    private void enableButton() {
        btnrotateright.setClickable(true);
        btnrotateleft.setClickable(true);
    }

    private void disableButton() {
        Log.e("disableButton : ","top");
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
        Log.e("disableButton : ","end");
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

    public void startNextActivity(String string[])
    {
        Log.e("startNextActivity -","optionActivity");
        Intent intent = new Intent(this,OptionActivity.class);
        intent.putExtra("strings", string);
        startActivity(intent);
    }
}

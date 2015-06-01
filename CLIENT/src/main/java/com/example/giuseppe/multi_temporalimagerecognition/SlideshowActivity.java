package com.example.giuseppe.multi_temporalimagerecognition;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by giuseppe on 30/05/15.
 */
public class SlideshowActivity extends Activity {
    String PATH_RECEIVED_SLIDESHOW;
    String name;

    int num_max;
    ImageView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slideshow);

        File f = Environment.getExternalStorageDirectory();
        PATH_RECEIVED_SLIDESHOW = f.getAbsolutePath();
        String url_image = PATH_RECEIVED_SLIDESHOW + "/SlideshowRicevuto/";

        Intent n1 = getIntent();
        num_max = n1.getExtras().getInt("num");
        final Bitmap bm[] = new Bitmap[num_max];

        for (int i = 0;i < num_max; i++){

            name = String.valueOf(i) + ".jpg";

            bm[i] =  BitmapFactory.decodeFile(url_image + name);

        }

        view = (ImageView) findViewById(R.id.image);
        final Animation myFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fadein);
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int i=0;
            public void run() {
                view.setImageBitmap(bm[i]);
                i++;
                if(i>bm.length-1)
                {
                    i=0;
                }
                handler.postDelayed(this, 3000);  //for interval...

                view.startAnimation(myFadeInAnimation);
            }
        };
        handler.postDelayed(runnable, 3000); //for initial delay..
    }

}

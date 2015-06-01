package com.example.giuseppe.multi_temporalimagerecognition;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.File;

/**
 * Created by giuseppe on 30/05/15.
 */
public class GalleryViewActivity extends Activity {

    String PATH_RECEIVED_SLIDESHOW;
    String name;
    ImageView mImageView;
    int a = 0;
    int h = 0;
    int b = 0; //mi tiene conto di quanti immagini ho tolto
    int ID;
    Bitmap bitmap;
    Bitmap image_rubbish[] = new Bitmap[1000];
    RelativeLayout layout;
    RelativeLayout.LayoutParams params;
    ImageView image;
    int num_max;
    int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galleryview);

        Button btn_previous = (Button) findViewById(R.id.button4);
        Button btn_forward = (Button) findViewById(R.id.button5);
        Intent n1 = getIntent();
        num_max = n1.getExtras().getInt("num");
        num = num_max;

        File f = Environment.getExternalStorageDirectory();
        PATH_RECEIVED_SLIDESHOW = f.getAbsolutePath();
        String url_image = PATH_RECEIVED_SLIDESHOW + "/SlideshowRicevuto/";

        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (b < num) {

                    image = (ImageView) findViewById(ID);
                    image_rubbish[h] = ((BitmapDrawable) image.getDrawable()).getBitmap();
                    image.setImageBitmap(null);
                    ID = ID - 1;
                    h++;
                    b++;//mi tiene conto di quanti immagini ho tolto
                }
            }
        });

        btn_forward.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View view) {

                if (b > 0){
                    ImageView image = (ImageView) findViewById(ID + 1);
                    //CREA nuova IMAGEVIEW
                    image.setImageBitmap(image_rubbish[h - 1]);
                    ID = ID + 1;
                    h--;
                    b--; //mi tiene conto di quanti immagini aggiungo
                }
            }
        });

        layout = (RelativeLayout) findViewById(R.id.frame1);

        for (int i = 0;i < num; i++){

            mImageView = new ImageView (this);
            mImageView.setId(i+1);
            name = String.valueOf(i) + ".jpg";
            bitmap = BitmapFactory.decodeFile(url_image + name);
            mImageView.setImageBitmap(bitmap);
            params = new RelativeLayout.LayoutParams(1400,1400);
            params.setMargins(0, a , 0, 0);
            layout.addView(mImageView,params);
            a = a + 90;
            ID = mImageView.getId();
        }
    }

}

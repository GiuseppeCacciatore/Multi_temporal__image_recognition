package com.example.giuseppe.multi_temporalimagerecognition;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by giuseppe on 30/05/15.
 */
public class ResultActivity extends Activity {

    int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent n1 = getIntent();
        int num_max = n1.getExtras().getInt("num");
        num = num_max;
    }

    public void Pass_to_GalleryView_activity (View view){

        Intent pass = new Intent (this,GalleryViewActivity.class);
        pass.putExtra("num",num );
        startActivity(pass);
    }

    public void Pass_to_slideshow_activity (View view){

        Intent pass = new Intent (this,SlideshowActivity.class);
        pass.putExtra("num",num );
        startActivity(pass);
    }

    public void Back_to_menu (View view){

        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage( getBaseContext().getPackageName() );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}

package com.example.giuseppe.multi_temporalimagerecognition;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {

    EditText editText;
    Button button;
    String selectedImagePath;
    String ip;
    int controll = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.email_address);
        button = (Button) findViewById(R.id.button_Send);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ip = editText.getText().toString();
                System.out.println(ip);
                editText.setText(" ");
                // editText.setHint("Ex: 10.66.20.105");
                controll = 1;
            }
        });
    }

    public void Pass_to_second_activity(View view) {

        Intent pass = new Intent(this, TakePhotoCameraActivity.class);
        pass.putExtra("ip", ip);
        ////IMPORTANTE: mettere qui il controllo.
        if (controll == 1) {
            startActivity(pass);
        }
    }

    public void Pass_to_takephotogallery_activity(View view) {

        Intent pass = new Intent(this, TakePhotoGalleryActivity.class);
        pass.putExtra("ip", ip);
        pass.putExtra("path_from_gallery", selectedImagePath);
       if (controll == 1) {
           startActivity(pass);
       }
    }

}

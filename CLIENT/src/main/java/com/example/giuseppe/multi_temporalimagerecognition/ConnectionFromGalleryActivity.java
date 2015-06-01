package com.example.giuseppe.multi_temporalimagerecognition;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by giuseppe on 30/05/15.
 */
public class ConnectionFromGalleryActivity extends Activity {

    int num;
    transferfileClient t;
    Socket soc;
    String IP;
    String path_from_gallery;
    TextView view;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connectionfromgallery);
        Intent intent = getIntent();
        IP = intent.getExtras().getString("ip");
        path_from_gallery = intent.getExtras().getString("path_from_gallery");

        System.out.println("Stringa PASSATA:" + path_from_gallery);

        if (path_from_gallery == null){
            Intent i = new Intent (this,MainActivity.class);
            startActivity(i);
        }

        view = (TextView) findViewById(R.id.textView2);
        view.startAnimation(AnimationUtils.loadAnimation(ConnectionFromGalleryActivity.this, android.R.anim.slide_in_left));

        new Thread(new Runnable() {

            public void run() {

                try
                {
                    soc = new Socket(IP, 5217);
                    System.out.println("SONO NELL'activity ConnectionFromGalleryActivity:" + IP);
                    t = new transferfileClient(soc);
                }

                catch (IOException e) {
                    e.printStackTrace();
                }

                try
                {
                    t.SendFile(path_from_gallery);
                }

                catch (Exception e)
                {
                    System.out.println("t.SendFile non funziona");
                    e.printStackTrace();
                }

                try
                {
                    num =  t.ReceiveSlideshow();
                    System.out.println("IL VALORE DI NUM : : : " + num);
                }

                catch (Exception e)
                {
                    System.out.println("t.ReceiveSlideshow() non funziona");
                    e.printStackTrace();
                }

                if (num == 0){

                    try {
                        t.dout.writeUTF("DISCONNECT");
                        soc.close();
                        Pass_to_result2_activity();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //faccio disconnetter il server
                else {
                    try {
                        t.dout.writeUTF("DISCONNECT");
                        soc.close();
                        Pass_to_result_activity();


                    } catch (Exception e) {
                        System.out.println("errore!");
                    }
                }
            }
        }).start();
    }

    public void Pass_to_result_activity (){

        Intent pass = new Intent (this,ResultActivity.class);
        pass.putExtra("num",num );
        startActivity(pass);
    }

    public void Pass_to_result2_activity (){

        Intent pass = new Intent (this,Result2Activity.class);
        startActivity(pass);
    }

}

package com.example.giuseppe.multi_temporalimagerecognition;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by giuseppe on 30/05/15.
 */
public class TakePhotoCameraActivity extends Activity {


    transferfileClient t;
    File path;
    String name;
    File file;
    String Path = null;
    Socket soc;

    private Uri currentImageUri;
    final static String tag=MainActivity.class.getSimpleName();
    private static final String TAG = MainActivity.class.getSimpleName();

    ImageView mImageView;
    String currentImagePath;



    static File imagePath;

    String IP;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takephotocamera);

        Intent n1 = getIntent();
        IP = n1.getExtras().getString("ip");

        System.out.println("Sono nella second Activity:" + IP);

        mImageView = (ImageView) findViewById(R.id.imagePreviewThumb);

        if(currentImageUri==null && savedInstanceState==null)
        {
            currentImageUri = getImageFileUri();

        }

        if(savedInstanceState != null)
        {
            currentImagePath = savedInstanceState.getString("currentImagePath");
        }
        else
        {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, currentImageUri); // set the image file name
            // start the image capture Intent
            startActivityForResult(intent, 1001);
        }
    }

    public void Pass(String _path) {

        Intent pass_to_activity = new Intent (this,ConnectionFromCameraActivity.class);
        pass_to_activity.putExtra("path_camera", _path);
        pass_to_activity.putExtra("ip",IP);
        startActivity(pass_to_activity);

    }

    public  Uri getImageFileUri()
    {
        // Create a storage directory for the images
        // To be safe(er), you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this
        imagePath = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Tuxuri");
        Log.d(tag, "Find " + imagePath.getAbsolutePath());


        if (! imagePath.exists())
        {
            if (! imagePath.mkdirs())
            {
                Log.d("CameraTestIntent", "failed to create directory");
                return null;
            }else{
                Log.d(tag,"create new Tux folder");
            }
        }

        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File image = new File(imagePath,"TUX_"+ timeStamp + ".jpg");
        file = image;
        name = file.getName();
        path = imagePath;


        if(!image.exists())
        {
            try {
                image.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return Uri.fromFile(image);
    }




    private void galleryAddPic() {
        /**
         * copy current image to Galerry
         */
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(currentImageUri);
        this.sendBroadcast(mediaScanIntent);
    }



    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("currentImagePath", currentImageUri.getPath());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //data available only when default environment is setting. null for customize filename.
        System.out.println("SONO IN FUNZIONE: onActivityResult");

        if (requestCode == 1001)
        {

            if (resultCode == Activity.RESULT_OK)
            {
                System.out.println("SONO IN FUNZIONE: onActivityResult - dentro i due if");

                galleryAddPic();

                //ora vorrei tentare di inviare l'immagine ricevuta
                String path_e_nome_foto = currentImageUri.toString();
                System.out.println("PROVA NOME FOTO: " + path_e_nome_foto);
                // path_e_nome_foto mi si presenterà così:
                // file:///storage/sdcard/Pictures/Tuxuri/TUX_20150428_190207.jpg
                //quindi voglio togliere i primi 7 caratteri di quella stringa
                path_e_nome_foto = path_e_nome_foto.substring(7);

                Path = path_e_nome_foto;
                System.out.println("PROVA NOME FOTO: " + path_e_nome_foto);


            }
            else if (resultCode == Activity.RESULT_CANCELED) {
                // User cancelled the image capture
            } else
            {
                // Image capture failed, advise user
            }
        }

        Pass (Path);
    }
}


package com.example.giuseppe.multi_temporalimagerecognition;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

/**
 * Created by giuseppe on 30/05/15.
 */
public class TakePhotoGalleryActivity extends Activity {

    private static final int SELECT_PICTURE = 1;
    String selectedImagePath;
    String IP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takephotogallery);

        Intent n1 = getIntent();
        IP = n1.getExtras().getString("ip");
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);
                System.out.println("Stringa:" + selectedImagePath);
                System.out.println("Uri:" + selectedImageUri);
            }
        }
        Pass_to_connectionfromgallery_activity();
    }

    public String getPath(Uri uri) {
        // just some safety built in
        if( uri == null ) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // this is our fallback here
        return uri.getPath();
    }

    public void Pass_to_connectionfromgallery_activity(){

        Intent pass = new Intent(this, ConnectionFromGalleryActivity.class);
        pass.putExtra("ip", IP);
        pass.putExtra("path_from_gallery", selectedImagePath);
        startActivity(pass);
    }
}

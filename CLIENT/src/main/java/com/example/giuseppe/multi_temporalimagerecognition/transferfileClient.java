package com.example.giuseppe.multi_temporalimagerecognition;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by giuseppe on 30/05/15.
 */
public class transferfileClient {

    Socket ClientSoc;

    DataInputStream din;
    DataOutputStream dout;
    BufferedReader br;
    //private final String PATH_RECEIVED_SLIDESHOW = "/storage/sdcard/Pictures/SlideshowRicevuto";
    private String PATH_RECEIVED_SLIDESHOW;
    //costruttore
    transferfileClient(Socket soc)
    {
        try
        {
            ClientSoc = soc;
            din = new DataInputStream(ClientSoc.getInputStream());
            dout = new DataOutputStream(ClientSoc.getOutputStream());
            br = new BufferedReader(new InputStreamReader(System.in));

            //prendo il percorso per la SD card:
            File f = Environment.getExternalStorageDirectory();
            PATH_RECEIVED_SLIDESHOW = f.getAbsolutePath();

            System.out.println("path storage memory: " + PATH_RECEIVED_SLIDESHOW);
        }
        catch (Exception ex)
        {
            System.out.println("Errore nel costruttore");
        }
    }

    /////////////////////
    void SendFile(String nomefile) throws Exception
    {

        dout.writeUTF("SEND");
        File f = new File(nomefile);

        if (!f.exists())
        {
            System.out.println("File not Exists...");
            dout.writeUTF("File not found");
            return;
        }

        dout.writeUTF(nomefile);

        System.out.println("Sending File ...");
        FileInputStream fin = new FileInputStream(f);
        int ch;
        do
        {
            ch = fin.read();
            dout.writeUTF(String.valueOf(ch));
        }
        while (ch != -1);
        fin.close();

        System.out.println("DEB3");
        System.out.println(din.readUTF());
    }

    int ReceiveSlideshow() throws Exception
    {
        String name;

        dout.writeUTF("GET");
        System.out.println("DEB10");
        String num_photos_str;
        //prima di tutto ricevo il numero di foto che mi aspetto di ricevere
        num_photos_str = din.readUTF();

        ///Controllo numero foto: se è zero cambia layout
        System.out.println("STRINGA RICEVUTA:" + num_photos_str);
        int num_photos = Integer.parseInt(num_photos_str);
        System.out.println("Num foto da ricevere: " + num_photos);




        //CREO LA DIRECTORY SULLA SCHEDA SD DOVE VADO A SALVARE LE FOTO
        //NB: qui ci andrebbe bene anche un controllo se la SD c'è, e anche una certa
        //funzione che mi dice il path esatto della sd
        //vedi: http://stackoverflow.com/questions/2130932/how-to-create-directory-automatically-on-sd-card
        // create a File object for the parent directory
        String url_image = PATH_RECEIVED_SLIDESHOW + "/SlideshowRicevuto/";
        File make_dir = new File(url_image);
        // have the object build the directory structure, if needed.
        make_dir.mkdirs();

        if (num_photos > 0)
        {
            for (int i=0; i < num_photos; i++)
            {
                name = String.valueOf(i) + ".jpg";
                ReceiveFile(url_image + name);
                System.out.println("Ricevuta foto: " + url_image + name);
            }
        }
        else
        {
            System.out.println("I'm going to receive 0 photos");
        }
        return num_photos;
    }
    //////////////////////////////////////////////////
    void ReceiveFile(String name) throws Exception
    {
        String msgFromServer = din.readUTF();
        System.out.println("ricevuto: " + msgFromServer);

        if(msgFromServer.compareTo("File Not Found") == 0)
        {
            System.out.println("File not found on Server ...");
            //return;
        }
        else if(msgFromServer.compareTo("READY") == 0)
        {
            System.out.println("Receiving File ...");

            File f = new File(name);

            if (f.exists())
                System.out.println("file esiste");
            if (f.canWrite())
                System.out.println("posso scrivere sul file");
            if (f.canRead())
                System.out.println("posso leggere sul file");
            FileOutputStream stream_out = new FileOutputStream(f);
            System.out.println("aperto fout");

            int ch;
            String temp;
            do
            {
                temp = din.readUTF();
                ch = Integer.parseInt(temp);

                if (ch != -1)
                {
                    stream_out.write(ch);
                }
            }
            while(ch!= -1);

            stream_out.close();

            System.out.println(din.readUTF());
        }
    }
}

/* SERVER JAVA DEFINITIVO PER PROGETTO
       MULTI-TEMPORAL IMAGE RECOGNITION
*/
import java.net.*;
import java.io.*;
import java.util.*;

public class TX_RX_Server
{
   public static void main(String args[]) throws Exception
   {
      ServerSocket soc = new ServerSocket(5217);
      //System.out.println("FTP Server Started on Port Number 5217");
        
      //System.out.println("Waiting for Connection ...");
      TransferFile t = new TransferFile( soc.accept() );
      
   }
}

class TransferFile extends Thread
{
   Socket client_soc;

   DataInputStream din;
   DataOutputStream dout;
   
   //COSTANTI
   final String NOME_IMMAGINE_RICEVUTA = "immagine_ricevuta.jpg";
   final String PATH_TO_IMAGE_DIRECTORY =
      "/home/eugenio/Scrivania/Multi_Temporal_Image_Recognition/SERVER/Images/";
   final long MILLISECONDS_SLEEP = 2000;
   
   //nomi dei file che mi servono per comunicare tra il server java e quello c++
   final String RICEZIONE_AVVENUTA = "ricezione_avvenuta.txt";
   final String ELABORAZIONE_EFFETTUATA = "elaborazione_effettuata.txt";
   final String SLIDESHOW_SPEDITO = "slideshow_spedito.txt";
   
   ///////////////////////////////////////////////////////////////////////
   //costruttore
   TransferFile(Socket soc)
   {
      try
      {
         client_soc = soc;                        
         din = new DataInputStream(client_soc.getInputStream());
         dout = new DataOutputStream(client_soc.getOutputStream());
         //System.out.println("FTP Client Connected ...");
         
         run();
            
      }
      catch(Exception ex)
      {
         System.out.println("Error in the constructor");
      }        
   }
   
   
   //////////////////////////////////////////////
   void SendSlideshow(Vector< String > nomi_foto_inviare) throws Exception
   {
      String str;
      
      /*
      //aspetto un secondo...
      try 
      {
         Thread.sleep(2000);       //1000 milliseconds is one second.
      } catch(InterruptedException ex) {
         Thread.currentThread().interrupt();
      }
      */ 
      
      //invio numero di foto che sto per spedire
      str = String.valueOf(nomi_foto_inviare.size());
      dout.writeUTF(str);
      
      for (int i=0; i < nomi_foto_inviare.size(); i++)
      {
         SendFile( nomi_foto_inviare.elementAt(i) );
         
         //System.out.println("DEB" + i);
      }
      
   }
   
   
   ///////////////////////////////////////
   void SendFile(String name) throws Exception
   {          
      //String filename = din.readUTF();
      
      File f = new File(name);
   
      if(!f.exists())
      {
         dout.writeUTF("File Not Found");
         return;
      }
      else
      {
         dout.writeUTF("READY");
         FileInputStream fin = new FileInputStream(f);
         int ch;
    
         do
         {
            ch = fin.read();
            dout.writeUTF(String.valueOf(ch));
         }
         while(ch != -1);    
         
         fin.close();    
      
         dout.writeUTF("File Received Successfully");
         //System.out.println("File " + name + " received successfully");                    
      }
   }
   
   
   ///////////////////////////////////////
   void ReceiveFile() throws Exception
   {
      String filename = din.readUTF();
      
      //System.out.println("Nome del file che sono in procinto di ricevere:\n" + filename);
      
      if(filename.compareTo("File not found") == 0)
      {
         //System.out.println("DEB0");
         
         return;
      }
      
      //al file ricevuto do il nome NOME_IMMAGINE_RICEVUTA 
      File f = new File(PATH_TO_IMAGE_DIRECTORY + NOME_IMMAGINE_RICEVUTA);
      String option;
         
      FileOutputStream fout = new FileOutputStream(f);
         
      int ch;
      String temp;
         
      do
      {
         temp = din.readUTF();
         ch = Integer.parseInt(temp);
            
         //System.out.println("DEB3");
        
         if(ch!=-1)
         {
            //System.out.println("DEB4");
               
            fout.write(ch);                    
         }
      }
      while(ch != -1);
         
      fout.close();
      dout.writeUTF("File Sent Successfully");
            
   }
   
   ////////////////////////////////////////
   public void run()
   {    
      try
      {
         File f = null;
          
         String command = din.readUTF();
           
         if(command.compareTo("SEND") != 0)
         {
            System.out.println("Errore: non ho ricevuto per primo un comando SEND");
            System.exit(1);
         }
                        
         ReceiveFile();
         
         //ora devo salvare un file vuoto che avvisi che la ricezione della foto è
         //stata completata
         try {
            f = new File(PATH_TO_IMAGE_DIRECTORY + RICEZIONE_AVVENUTA);
            f.createNewFile();
         } catch (IOException e) {
            System.out.println("Errore: non ho creato il file " + RICEZIONE_AVVENUTA);
         }
         
         //ora devo aspettare che il server c++ faccia la sua elaborazione
         try 
         {
            f = new File(PATH_TO_IMAGE_DIRECTORY + ELABORAZIONE_EFFETTUATA);
            do {
               Thread.sleep(MILLISECONDS_SLEEP);
            } while( !f.exists() );
            
         } catch(InterruptedException ex) {
            //Thread.currentThread().interrupt();
            System.out.println("Errore: nello sleep aspettando l'elaborazione");
         }
         
         //ora il server ha fatto l'elaborazione, devo leggere nel file ELABORAZIONE_EFFETTUATA
         //il numero di foto da spedire e la loro URL
         Vector< String > nomi_foto_inviare = new Vector< String >();
         if ( f.isFile() )
         {
            try {
               BufferedReader input = new BufferedReader(new FileReader(f));

               String text;
               while ((text = input.readLine()) != null)
                  nomi_foto_inviare.add(text);

               input.close();

            } catch (IOException ioException) {
               System.out.println("Errore: nel leggere il file " + ELABORAZIONE_EFFETTUATA);
            }
         }
         
         //distruggo il file ELABORAZIONE_EFFETTUATA
         f.delete();
         
         //INVIO LO SLIDESHOW
         command = din.readUTF();
           
         if(command.compareTo("GET") != 0)
         {
            System.out.println("Errore: non ho ricevuto il comando GET quando avrei dovuto riceverlo");
            System.exit(1);
         }
            
         SendSlideshow(nomi_foto_inviare);
      
      
         //AVVISO IL SERVER C++ CHE STO PER SPEGNERMI
         try {
            f = new File(PATH_TO_IMAGE_DIRECTORY + SLIDESHOW_SPEDITO);
            f.createNewFile();
         } catch (IOException e) {
            System.out.println("Errore: non ho creato il file " + SLIDESHOW_SPEDITO);
         }
         
         System.exit(1);
         
         //RICEVO IL COMANDO DI DISCONNESSIONE
         command = din.readUTF();
           
         if(command.compareTo("DISCONNECT") != 0)
         {
            System.out.println("Errore: non ho ricevuto il comando DISCONNECT quando avrei dovuto riceverlo");
            System.exit(1);
         }
         
         System.exit(1);
     
      }
      catch(Exception ex)
      {
            System.out.println("Sono nell'exception del metodo Run()");
      }
   }

}


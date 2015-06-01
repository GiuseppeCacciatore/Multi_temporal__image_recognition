#ifndef MAIN_CLASS_HPP_
#define MAIN_CLASS_HPP_

#include <cmath>
#include <fstream>
#include <cstdio>
#include <unistd.h>

#include "opencv2/core/core.hpp"
#include "opencv2/features2d/features2d.hpp"
#include "opencv2/highgui/highgui.hpp"
#include "opencv2/flann/flann.hpp"
#include "opencv2/imgproc/imgproc.hpp"
#include "opencv2/nonfree/features2d.hpp"
#include "opencv2/nonfree/nonfree.hpp"

#include "Topic_Photo.hpp"

using namespace cv;

//costante che definisce la distanza massima delle coordinate geografiche della foto
//ricevuta rispetto a quelle già memorizzate
//questa costante è il lato del quadrato dove vado a vedere se le coordinate vanno
//bene
const long double MAX_DISTANCE_LATITUDE = 0.01;
const long double MAX_DISTANCE_LONGITUDE = 0.01;

//minimo numero di punti in comune tra due foto
const unsigned MIN_MATCHES = 100;

//massimo numero di pixel per lato che una foto da spedire può avere
const int MAX_PHOTO_EDGE = 700;

//percorso per la directory con le cartelle con le immagini
const string PATH_TO_IMAGE_DIRECTORY =
"/home/eugenio/Scrivania/Multi_Temporal_Image_Recognition/SERVER/Images/";

const string READY_TO_BE_SENT_FOLDER = "READY_TO_BE_SENT/";

//costante che mi serve per tarare l'algoritmo di surf
const int MIN_HESSIAN = 800;

//nome dell'immagine ricevuta
const string NOME_IMMAGINE_RICEVUTA = "immagine_ricevuta.jpg";

//nomi dei file che mi servono per comunicare tra il server java e quello c++
const string RICEZIONE_AVVENUTA = "ricezione_avvenuta.txt";
const string ELABORAZIONE_EFFETTUATA = "elaborazione_effettuata.txt";
const string SLIDESHOW_SPEDITO = "slideshow_spedito.txt";

const unsigned SECONDS_SLEEP = 3;

/////////////  CLASS DEFINITION
class Main_Class
{
   public:
	 Main_Class(string filename_config);

    void Start();

   private:
	 string filename_received;
	 Photo received_photo;
	 vector< Topic_Photo > topics;

	 //altro campo della foto ricevuta che mi servono per il SURF con altre foto
	 Mat descriptors_received_photo;

	 //campo che mi serve per stampare il report//
	 ofstream file_report;

	 /////////////////////////////////////////////////// metodi privati:
	 void ReceivePhoto();
	 void ReadReceivedPhoto();
	 vector< int > FindGPSMatching ();
	 int MatchingControl(const vector<int>& indexes);
	 void SendSlideshow(int index);
	 void GetDataFromImage(const string& name, Date& date,
	                       GPS_Coordinates& coord, int& orientation);
	 string FromUnsignedToString(unsigned k);
	 bool DoesFileExist(const string& url);
	 long unsigned NumOfSURFMatchingPoints(const string& nome_foto);
	 void MakePhotosOKForSlideshow(int index);
	 void DecreasePhotoResolution(int index);
	 void RemovePhotoFromREADY_TO_BE_SENT_FOLDER();
};



#endif /* MAIN_CLASS_HPP_ */

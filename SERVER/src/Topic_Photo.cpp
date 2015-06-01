#include "Topic_Photo.hpp"

ostream& operator<<(ostream& os, const Topic_Photo& t)
{
   os << t.topic << endl
      << t.mean_coordinates << endl;

   for(unsigned i=0; i < t.photos.size(); i++)
      os << t.photos[i] << endl;

   return os;
}

Topic_Photo::Topic_Photo()
{}

Topic_Photo::Topic_Photo(string t, const GPS_Coordinates& c)
            :topic(t), mean_coordinates(c)
{}

void Topic_Photo::AddPhoto(const Photo& ph)
{
   photos.push_back(ph);
}

void Topic_Photo::ReorderPhotos()
{
   //ordino le foto per data crescente
   //implemento l'algoritmo bubblesort
   bool fatto_scambio;
   unsigned k;
   Photo temp;

   if (photos.size() == 1)
      return;

   fatto_scambio = true;
   while (fatto_scambio)
   {
      fatto_scambio = false;

      for(k = 1; k < photos.size(); k++)
      {
         if (photos[k].GetDate() < photos[k-1].GetDate())
         {
            temp = photos[k];
            photos[k] = photos[k-1];
            photos[k-1] = temp;

            fatto_scambio = true;
         }
      }
   }
}




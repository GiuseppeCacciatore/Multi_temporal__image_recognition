#ifndef TOPIC_PHOTO_HPP_
#define TOPIC_PHOTO_HPP_

#include "GPS_Coordinates.hpp"
#include "Photo.hpp"

class Topic_Photo
{
   friend ostream& operator<<(ostream& os, const Topic_Photo& t);

   public:
    Topic_Photo();
    Topic_Photo(string t, const GPS_Coordinates& c = GPS_Coordinates(0,0) );

    string Topic() const { return topic; }
    long double Latitude() const { return mean_coordinates.latitude; }
    long double Longitude() const { return mean_coordinates.longitude; }
    unsigned NumOfPhotos() const { return photos.size(); }
    Photo& GetPhoto(unsigned i) { return photos.at(i); }

    void SetMeanGPSCoordinates(const GPS_Coordinates& c) { mean_coordinates = c; }
    void AddPhoto(const Photo& ph);
    void ReorderPhotos();  //riordina le foto per data

   private:
	 string topic;
	 GPS_Coordinates mean_coordinates;
    vector< Photo > photos;


};


#endif /* TOPIC_PHOTO_HPP_ */

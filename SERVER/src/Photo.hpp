#ifndef PHOTO_HPP_
#define PHOTO_HPP_

#include <stdio.h>
#include <iostream>
#include <vector>
#include <cassert>

using namespace std;

#include "GPS_Coordinates.hpp"
#include "Date.hpp"

class Photo
{
   friend ostream& operator<<(ostream& os, const Photo& p);

   public:
    Photo();
    Photo(const string& n, const Date& d, const GPS_Coordinates& c,
          int o = 0, bool slideshow = false );

    string Name() const { return name; }
    Date GetDate() const { return date; }
    long double Latitude() const { return coordinates.latitude; }
    long double Longitude() const { return coordinates.longitude; }
    int Orientation() const { return orientation; }
    bool OkForSlideshow() const { return ok_for_slideshow; }

    void SetOrientation(int o);
    void MakeOKForSlideshow(bool response) { ok_for_slideshow = response; }

   private:
	 string name;    //SOLO il nome della foto! non il path!
	 Date date;
	 GPS_Coordinates coordinates;
	 int orientation;   //orientazione della foto: 0 o 90 degrees
	                    //esempio: la matrice dei pixel è 1200x600 ma io vedo la
	                    //foto 600x1200 => orientation = 90
	 bool ok_for_slideshow;

};



#endif /* PHOTO_HPP_ */

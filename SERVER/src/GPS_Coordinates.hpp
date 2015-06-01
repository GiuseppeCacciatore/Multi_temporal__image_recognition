#ifndef GPS_COORDINATES_HPP_
#define GPS_COORDINATES_HPP_

#include <iostream>
#include <fstream>

using namespace std;

class GPS_Coordinates
{
   friend ostream& operator<<(ostream& os, const GPS_Coordinates& c);

   public:
	 GPS_Coordinates(long double lat = 0, long double lon = 0)
    { latitude = lat;  longitude = lon; }

	 long double latitude, longitude;
};

//NOTE con un long double ho una precisione del tipo 180.000001
//e mi va bene, anche perchè con le coordinate non supero mai in modulo 180


inline ostream& operator<<(ostream& os, const GPS_Coordinates& c)
{
   os.precision(9);

   os << c.latitude << " " << c.longitude;

   return os;
}

#endif /* GPS_COORDINATES_HPP_ */

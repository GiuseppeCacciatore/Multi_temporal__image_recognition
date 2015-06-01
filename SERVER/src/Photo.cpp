#include "Photo.hpp"

ostream& operator<<(ostream& os, const Photo& p)
{
   os << p.name << " " << p.coordinates << " " << p.date << " "
      << p.orientation << "° " << p.ok_for_slideshow;

   return os;
}

Photo::Photo()
{
   ok_for_slideshow = false;
   orientation = 0;
}

Photo::Photo(const string& n, const Date& d, const GPS_Coordinates& c,
             int o, bool slideshow)
      :name(n), date(d), coordinates(c)
{
   ok_for_slideshow = slideshow;
   orientation = o;
}

void Photo::SetOrientation(int o)
{
    assert(o == 0  ||  o == 90);

    orientation = o;
}

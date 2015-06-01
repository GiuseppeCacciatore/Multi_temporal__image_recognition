#include "Date.hpp"

bool operator==(const Date& d1, const Date& d2)
{
   return d1.day == d2.day  &&  d1.month == d2.month  &&  d1.year == d2.year  &&
          d1.hour == d2.hour  &&  d1.minute == d2.minute  &&
          d1.second == d2.second;
}

bool operator>(const Date& d1, const Date& d2)
{
   return d1.year > d2.year ||
         (d1.year == d2.year && d1.month > d2.month)
      || (d1.year == d2.year && d1.month == d2.month && d1.day > d2.day)
      || (d1.year == d2.year && d1.month == d2.month && d1.day == d2.day && d1.hour > d2.hour)
      || (d1.year == d2.year && d1.month == d2.month && d1.day == d2.day && d1.hour == d2.hour && d1.minute > d2.minute)
      || (d1.year == d2.year && d1.month == d2.month && d1.day == d2.day && d1.hour == d2.hour && d1.minute == d2.minute && d1.second > d2.second);
}

bool operator>=(const Date& d1, const Date& d2)
{
   return d1.year > d2.year ||
            (d1.year == d2.year && d1.month > d2.month)
         || (d1.year == d2.year && d1.month == d2.month && d1.day > d2.day)
         || (d1.year == d2.year && d1.month == d2.month && d1.day == d2.day && d1.hour > d2.hour)
         || (d1.year == d2.year && d1.month == d2.month && d1.day == d2.day && d1.hour == d2.hour && d1.minute > d2.minute)
         || (d1.year == d2.year && d1.month == d2.month && d1.day == d2.day && d1.hour == d2.hour && d1.minute == d2.minute && d1.second >= d2.second);
}

bool operator<(const Date& d1, const Date& d2)
{
   return d2 > d1;
}

bool operator<=(const Date& d1, const Date& d2)
{
   return d2 >= d1;
}

bool operator!=(const Date& d1, const Date& d2)
{
   return d1.day != d2.day  ||  d1.month != d2.month  ||  d1.year != d2.year  ||
          d1.hour != d2.hour  ||  d1.minute != d2.minute  ||  d1.second != d2.second;
}

/*
int operator-(const Date& d1, const Date& d2)
{
   Date d = d1;
   int i = 0;

   if (d2 > d)
   {
      while (d != d2)
      {
         ++d;
         i--;
      }
   }
   else
   {
      while (d != d2)
      {
         --d;
         i++;
      }
   }

   return i;
}
*/

istream& operator>>(istream& is, Date& d)
{
   char ch;

   //input format:
   // 2014/05/03 14:36:44

   is >> d.year >> ch >> d.month >> ch >> d.day
      >> d.hour >> ch >> d.minute >> ch >> d.second;

   return is;
}

ostream& operator<<(ostream& os, const Date& d)
{
   os << d.year << '/' << d.month << '/' << d.day << ' '
      << d.hour << ':' << d.minute << ':' << d.second;

   return os;
}

Date::Date(int d, int m, int y, int h, int min, int s)
{
   day = d;
   month = m;
   year = y;
   hour = h;
   minute = min;
   second = s;

   if (!Valid())
   {
      day = 1;
      month = 1;
      year = 1;
      hour = 0;
      minute = 0;
      second = 0;
   }
}

Date::Date()
{
   day = 1;
   month = 1;
   year = 1;
   hour = 0;
   minute = 0;
   second = 0;
}

/*
void Date::operator++()
{
   if (day != DaysOfAMonth())
      day++;
   else
   {
      if (month != 12)
      {
         day = 1;
         month++;
      }
      else
      {
         day = 1;
         month = 1;
         year++;
      }
   }
}

void Date::operator--()
{
   if (day != 1)
      day--;
   else
   {
      if(month != 1)
      {
         month--;
         day = DaysOfAMonth();
      }
      else
      {
         year--;
         month = 12;
         day = DaysOfAMonth();
      }
   }
}

void Date::operator+=(int n)
{
   int i;
   if (n > 0)
      for (i = 0; i < n; i++)
         ++(*this);
   else
      for (i = 0; i < -n; i++)
         --(*this);
}

Date Date::operator+(int n)
{
   Date d = *this;

   int i;
   if (n > 0)
      for (i = 0; i < n; i++)
         ++d;
   else
      for (i = 0; i < -n; i++)
         --d;

   return d;
}

string Date::DayOfTheWeek() const
{
   const char *week[DAYS_IN_A_WEEK] = {MONDAY.c_str(), TUESDAY.c_str(), WEDNESDAY.c_str(),
                                       THURSDAY.c_str(), FRIDAY.c_str(), SATURDAY.c_str(),
                                       SUNDAY.c_str()};

   Date date_to_convert = *this;

   int idex_reference_day;
   for(idex_reference_day = 0; idex_reference_day < DAYS_IN_A_WEEK; idex_reference_day++)
      if(REFERENCE.second == week[idex_reference_day])
         break;

   int days_between = date_to_convert - REFERENCE.first;

   int x = days_between % DAYS_IN_A_WEEK;

   if (x > 0)
   {
      while(x > 0)
      {
         x--;
         idex_reference_day++;
         if (idex_reference_day >= DAYS_IN_A_WEEK)
            idex_reference_day = 0;
      }
   }
   else
   {
      while(x < 0)
      {
         x++;
         idex_reference_day--;
         if (idex_reference_day < 0)
            idex_reference_day = DAYS_IN_A_WEEK - 1;
      }
   }

   return week[idex_reference_day];
}
*/

bool Date::Valid() const
{
   return year >= 1  &&  year < 10000
      && month >= 1  &&  month <= 12
        && day >= 1  &&  day <= DaysOfAMonth()
       && hour >= 0  &&  hour < 24
     && minute >= 0  &&  minute < 60
     && second >= 0  &&  second < 60;
}

bool Date::LeapYear() const
{
   if (year % 4 != 0)
      return false;
   else if (year % 100 != 0)
      return true;
   else if (year % 400 != 0)
      return false;
   else
      return true;
}

int Date::DaysOfAMonth() const
{
   if (month == 4 || month == 6 || month == 9 || month == 11)
      return 30;
   else if (month == 2)
     if (LeapYear())
        return 29;
     else
        return 28;
   else
      return 31;
}





#ifndef DATE_HPP_
#define DATE_HPP_

#include <iostream>
#include <vector>

using namespace std;

const string MONDAY = "Monday";
const string TUESDAY = "Tuesday";
const string WEDNESDAY = "Wednesday";
const string THURSDAY = "Thursday";
const string FRIDAY = "Friday";
const string SATURDAY = "Saturday";
const string SUNDAY = "Sunday";

const int DAYS_IN_A_WEEK = 7;

class Date
{
   friend bool operator==(const Date& d1, const Date& d2);
   friend bool operator>(const Date& d1, const Date& d2);
   friend bool operator>=(const Date& d1, const Date& d2);
   friend bool operator<(const Date& d1, const Date& d2);
   friend bool operator<=(const Date& d1, const Date& d2);
   friend bool operator!=(const Date& d1, const Date& d2);
   friend int operator-(const Date& d1, const Date& d2);
   friend istream& operator>>(istream& is, Date& d);
   friend ostream& operator<<(ostream& os, const Date& d);

   public:
    Date(int d, int m, int y, int h=0, int min=0, int s=0);
    Date();

    int Day() const { return day; }
    int Month() const { return month; }
    int Year() const { return year; }
    int Hour() const { return hour; }
    int Minute() const { return minute; }
    int Second() const { return second; }

    void operator++();
    void operator--();
    void operator+=(int n);
    Date operator+(int n);

    string DayOfTheWeek() const;

   private:
    int day;
    int month;
    int year;

    int hour;
    int minute;
    int second;

    bool Valid() const;
    bool LeapYear() const;
    int DaysOfAMonth() const;
};

const pair<Date, string> REFERENCE = make_pair(Date(30,1,2014), THURSDAY);


#endif /* DATE_HPP_ */

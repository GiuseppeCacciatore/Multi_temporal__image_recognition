pre-requisites on OS:
we used: Ubuntu 14.04 LTS - 64 bit

software/libraries required:
- C/C++ default libraries
- Java default libraries
- OpenCV 2.4.10 libraries, including the "nonfree" module "features2d"
- the "Exiftool" software able to read EXIF data from photos

before compiling the code, you HAVE TO DO A CHANGE:
- in the "src/" folder, in the file "Main_Class.hpp", in the lines number 35-36 there is the 
  string that indicates the path in order to reach the Image/ folder
  => you have to set it according to your folder organization
     it must have this format: "/home/ ... /Images/"
- in the "src/src_TX_RX_Server/", in the file "TX_RX_Server.java", int the lines number 30/31
  you have to do the same modification of the string of the path to the "Image" directory  
   
then you can compile the program using the the command "make": it will compile both the Elaborating server and the TX/RX Server

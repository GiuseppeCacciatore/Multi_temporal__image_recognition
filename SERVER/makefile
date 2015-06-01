all: TX_RX_Server.java Multi_Temporal_Image_Recognition_SERVER.exe move1 move2

TX_RX_Server.java:
	javac src/src_TX_RX_Server/TX_RX_Server.java

Multi_Temporal_Image_Recognition_SERVER.exe:
	g++ -o Multi_Temporal_Image_Recognition_SERVER.exe -Wall src/Main_Server.cpp src/Main_Class.cpp src/Topic_Photo.cpp src/Photo.cpp src/Date.cpp src/GPS_Coordinates.hpp  -I /usr/local/include/opencv -L /usr/local/lib -lopencv_core -lopencv_imgproc -lopencv_flann -lopencv_nonfree -lopencv_features2d -lopencv_highgui

move1:
	mv src/src_TX_RX_Server/TX_RX_Server.class ./
move2:
	mv src/src_TX_RX_Server/TransferFile.class ./


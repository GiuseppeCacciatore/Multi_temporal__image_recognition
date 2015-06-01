#include "Main_Class.hpp"

const string NAME_FILE_DATABASE = "config.txt";


int main( int argc, char** argv )
{
   Main_Class server(PATH_TO_IMAGE_DIRECTORY + NAME_FILE_DATABASE);

   server.Start();

   return 0;
}

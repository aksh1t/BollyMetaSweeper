package Controller;

import java.io.File;
import java.util.ArrayList;

public class OpenFileDialogHandler {
    
    //Get all mp3 files from the folder
    public static File[] getFiles(File[] f){
        
        //ArrayList for storing all the files
        ArrayList fileList = new ArrayList();
        
        //Loop for storing files in ArrayList
        for(int i=0;i<f.length;i++){
            getFiles(f[i],fileList);
        }
        
        //File Array for output
        File [] list = new File[fileList.size()];
        
        //Loop for converting ArrayList to File Array
        for(int i=0;i<fileList.size();i++){
            list[i] = (File)fileList.get(i);
        }
        
        //Setting the status text of the View
//        LyricHound.statusText.setText("Searching done!");
        
        //Returning File Array as output
        return list;
    }
    
    //Used in getFiles method above
    public static void getFiles(File f,ArrayList fileList){
        
        //Setting the status text of the View
//        LyricHound.statusText.setText("Searching for mp3 files...");
    	
        //Check for mp3 file
        if(f.isFile()&&f.toString().toLowerCase().endsWith(".mp3")){
            
            //Add file to ArrayList if file is mp3
            fileList.add(f);
            
        }
        
        //Check for directory
        else if(f.isDirectory()){
            
            //Temporary File Array for storing Files
            File [] tempList = f.listFiles();
            
            //Recursively run getFiles method to get all Files from Directories
            for(int i=0;i<tempList.length;i++){
                getFiles(tempList[i],fileList);
            }
        }
    }
}

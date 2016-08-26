package com.purang.grab.util;

import java.io.File;
import java.io.IOException;

public class FileUtils {

	public static void createFile(String path){
		File file=new File(path);
		boolean dircreate=false;
		boolean filecreate=false;
		if (!file.getParentFile().exists()) {  
            if (file.getParentFile().mkdirs()) {  
            	dircreate=true;
            }  
        }  
        if(!file.exists()) {  
            try {  
            	file.createNewFile();  
            	filecreate=true;
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
	}
}

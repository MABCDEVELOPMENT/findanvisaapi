package com.anvisa.core.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Download {
	
	 static String FOLDER_IMAGE = "file://findimage/";
	
	 public static void DownloadImage(String search, String path) {

		    // This will get input data from the server
		    InputStream inputStream = null;

		    // This will read the data from the server;
		    OutputStream outputStream = null;

		    try {
		        // This will open a socket from client to server
		        URL url = new URL(search);

		       // This user agent is for if the server wants real humans to visit
		        String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";

		       // This socket type will allow to set user_agent
		        URLConnection con = url.openConnection();

		        // Setting the user agent
		        con.setRequestProperty("User-Agent", USER_AGENT);

		        // Requesting input data from server
		        inputStream = con.getInputStream();

		        // Open local file writer
		        outputStream = new FileOutputStream(path);

		        // Limiting byte written to file per loop
		        byte[] buffer = new byte[2048];

		        // Increments file size
		        int length;

		        // Looping until server finishes
		        while ((length = inputStream.read(buffer)) != -1) {
		            // Writing data
		            outputStream.write(buffer, 0, length);
		        }

			     outputStream.close();
			     inputStream.close();   
		    } catch (Exception ex) {
		        Logger.getLogger(Download.class.getName()).log(Level.SEVERE, null, ex);
		        
		    }

		     // closing used resources
		     // The computer will not be able to use the image
		     // This is a must

		}

}

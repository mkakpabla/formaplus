package com.formaplus.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javafx.scene.image.Image;

public class Utils {
	
	
	
	public static Image getImage(InputStream is) {
		try(OutputStream os = new FileOutputStream(new File("temp.jpg"))) {
			
			byte[] content = new byte[1024];
			int size = 0;
			while((size = is.read(content)) != -1) {
				os.write(content, 0, size);
			}
			Image image = new Image("file:temp.jpg", 120, 120, true, true);
			return image;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}

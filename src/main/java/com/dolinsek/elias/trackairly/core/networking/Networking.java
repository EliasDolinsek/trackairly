package com.dolinsek.elias.trackairly.core.networking;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class Networking {
	
	public static void getServerStatus(String address, String urlParameters) {
		HttpURLConnection connection = null;
		try {
			URL url = new URL(address);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
		    connection.setUseCaches(false);
		    connection.setDoOutput(true);
		    
		    System.out.println(connection.getResponseCode());

		    DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
		    dataOutputStream.writeBytes(urlParameters);
		    dataOutputStream.close();
		    
		    System.out.println(getResponse(connection));
		} catch (Exception e) {
			e.printStackTrace();
			//return new ServerStatus("UNREACHABLE", "Couldn't connect to trackairly EU1");
		}
		
	}
	
	public static String getResponse(HttpURLConnection connection) throws IOException {
	    InputStream is = connection.getInputStream();
	    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
	    StringBuilder response = new StringBuilder();
	    
	    String line;
	    while ((line = rd.readLine()) != null) {
	      response.append(line);
	      response.append('\r');
	    }
	    
	    rd.close();
	    return response.toString();
	}
}

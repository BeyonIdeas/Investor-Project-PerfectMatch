package com.beyonideas.httpcom;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class HttpCom {
	
	private String httpGetResponse;
	private int httpGetCode;
	
	private String httpPostResponde;
	private int httpPostCode;
	
	public HttpCom(){
		this.httpGetCode = 0;
		this.httpGetResponse = "";
		this.httpPostCode = 0;
		this.httpPostResponde = "";
	}
	
	public int getGetCode(){
		return this.httpGetCode;
	}
	
	public String getGetResponse(){
		return this.httpGetResponse;
	}
	
	public int getPostCode(){
		return this.httpPostCode;
	}
	
	public String getPostResponse(){
		return this.httpPostResponde;
	}
	
	public String httpGET(String requestURL) {
        
        //Variables
        URL request = null;
        HttpURLConnection connection = null;
        Scanner scanner = null;
        String response = "";
        
        try {
			//connect
			request = new URL(requestURL);
			connection = (HttpURLConnection) request.openConnection();
			
			//connection properties
			connection.setRequestMethod("GET");
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(false);
			
			//read 
			scanner = new Scanner(request.openStream());
			response = scanner.useDelimiter("\\Z").next();
			
			//messages
			this.httpGetCode = connection.getResponseCode();
			this.httpGetResponse = connection.getResponseMessage();
			
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error: "+e.getMessage());
			response = null;
		} finally {
			scanner.close();
			connection.disconnect();
		}
        
        //return
        return response;
    }//httpGET - end

	public String httpPOST(String targetURL, String urlParameters) {
		
	    URL url = null;
	    HttpURLConnection connection = null;  
	    try {
	      //Create connection
	      url = new URL(targetURL);
	      
	      connection = (HttpURLConnection) url.openConnection();
	      connection.setRequestMethod("POST");
	      connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");	
	      connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
	      connection.setRequestProperty("Content-Language", "en-US");  
	      connection.setUseCaches (false);
	      connection.setDoInput(true);
	      connection.setDoOutput(true);
	      
	      //Send request
	      DataOutputStream wr = new DataOutputStream (connection.getOutputStream ());
	      wr.writeBytes (urlParameters);
	      wr.flush ();
	      wr.close ();
	
	      //Get Response	
	      InputStream is = connection.getInputStream();
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is));
	      String line;
	      StringBuffer response = new StringBuffer(); 
	      while((line = rd.readLine()) != null) {
	        response.append(line);
	        response.append('\r');
	      }
	      rd.close();
	      //Debug:
	      //System.out.println(response.toString());
	      return response.toString();
	
	    } catch (Exception e) {
	
	      e.printStackTrace();
	      return null;
	
	    } finally {
	
	      if(connection != null) {
	    	  try {
	    		  this.httpGetResponse = connection.getResponseMessage();
	    		  this.httpPostCode = connection.getResponseCode();  
	    	  } catch (IOException e){
	    		  e.printStackTrace();
	    	  }
	          connection.disconnect(); 
	      }
	    }
	  }//httpExcutePost - end

}

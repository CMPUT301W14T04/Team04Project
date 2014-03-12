package com.example.geoloc;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.google.gson.Gson;

public class ElasticSearchOperations {
	
	public static void pushComment(final JSONArray commentArr) {
		Thread thread = new Thread() {
			
			@Override
			public void run() {
				HttpClient client = new DefaultHttpClient();
				HttpPost request = new HttpPost("http://cmput301.softwareprocess.es:8080/cmput301w14t04/testcomments/comments");
				
				try {
					JSONObject commentObj = null;
					if (commentArr != null){
						commentObj = new JSONObject();
						commentObj.put("comments", commentArr);
						Log.d("commentMap", commentObj.toString());
					}

					Log.d("Model", commentObj.toString());
					String jsonString = commentObj.toString();
					Log.d("Model String", jsonString);
					request.setEntity(new StringEntity(jsonString));
					HttpResponse response = client.execute(request);
					Log.w("ElasticSearch", response.getStatusLine().toString());
					
					response.getStatusLine().toString();
					HttpEntity entity = response.getEntity();
					
					BufferedReader reader = new BufferedReader (new InputStreamReader(entity.getContent()));
					String output = reader.readLine();
					while (output != null) {
						Log.w("ElasticSearch", output);
						output = reader.readLine();
					}
					
				}
				catch (Exception e) {
					e.printStackTrace();
				
				}
			}
		};
		
		thread.start();
	}

}

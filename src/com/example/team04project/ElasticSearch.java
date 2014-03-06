package com.example.team04project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


/*
 * Adds the comment to elasticsearch
 */
public class ElasticSearch {
	public static final String SERVER_URL = "http://cmput301.softwareprocess.es:8080/cmput301w14t04/TestArea/Tests";
	public static final String LOG_TAG = "ElasticSearch";
	static HttpClient client = new DefaultHttpClient();
	static Gson GSON = new Gson();

	public static void pushComment(final Comments comment) {
		Thread thread = new Thread() {

			@Override
			public void run() {
				HttpClient client = new DefaultHttpClient();
				HttpPost request = new HttpPost(SERVER_URL+comment.getId());

				try {
					request.setEntity(new StringEntity(GSON.toJson(comment)));
				}
				catch (UnsupportedEncodingException exception) {
					Log.w(LOG_TAG, "Error encoding PicPostModel: " + exception.getMessage());
					return;
				}

				HttpResponse response;
				try {
					response = client.execute(request);
					Log.i(LOG_TAG, "Response: " + response.getStatusLine().toString());
				} 
				catch (IOException exception) {
					Log.w(LOG_TAG, "Error sending PicPostModel: " + exception.getMessage());
				}
			}
		};

		thread.start();

	}
	
	/*
	 * The comment will be updated on elastic search
	 */
	
	public static void updateComment(final Comments comment)throws ClientProtocolException, IOException{
		Thread thread = new Thread() {
			@Override
			public void run() {
				HttpPost updateRequest = new HttpPost(SERVER_URL+comment.getId());

				try {
					updateRequest.setEntity(new StringEntity(GSON.toJson(comment)));
				}
				catch (UnsupportedEncodingException exception) {
					Log.w(LOG_TAG, "Error encoding Comments: " + exception.getMessage());
					return;
				}

				HttpResponse response;
				try {
					response = client.execute(updateRequest);
					Log.i(LOG_TAG, "Response: " + response.getStatusLine().toString());
				} 
				catch (IOException exception) {
					Log.w(LOG_TAG, "Error sending Comments: " + exception.getMessage());
				}
			}
		};
	thread.start();
	}

	public void retrieveComments(){
		Thread thread = new Thread() {
			@Override
			public void run() {
				HttpGet getRequest = new HttpGet("http://cmput301.softwareprocess.es:8080/cmput301w14t04/TestArea/");
			
				try {
					HttpResponse response =client.execute(getRequest);
					String output;
					BufferedReader br = new BufferedReader(
							new InputStreamReader((response.getEntity().getContent())));
					String json = "";
					while ((output = br.readLine()) != null) {
						json += output;
						output=br.readLine();
					}
					Type elasticSearchResponseType = new TypeToken<ArrayList<Comments>>(){}.getType();
					ArrayList<Comments> comment = GSON.fromJson(json, elasticSearchResponseType);
			
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		thread.start();
		}
		
	
	
	public static void deleteComment(final Comments comment){
		Thread thread = new Thread() {
			@Override
			public void run() {
				HttpDelete httpDelete = new HttpDelete(SERVER_URL + comment.getId());
				HttpResponse response;
				try {
					response = client.execute(httpDelete);

				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		thread.start();
	}
}

	



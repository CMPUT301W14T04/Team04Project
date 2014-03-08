package com.example.team04project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



 /* Adds the comment to elasticsearch*/
 
public class ElasticSearch {
	public static final String SERVER_URL = "http://cmput301.softwareprocess.es:8080/cmput301w14t04/TestArea/Test-+-";
	public static final String LOG_TAG = "ElasticSearch";
	static HttpClient client = new DefaultHttpClient();
	static Gson GSON = new Gson();
	
	private static ArrayList<TopLevel> list;

	public static void pushComment(final TopLevel comment) {
		Thread thread = new Thread() {

			@Override
			public void run() {
				HttpClient client = new DefaultHttpClient();
				HttpPost request = new HttpPost(SERVER_URL+comment.getDate());

				try {
					request.setEntity(new StringEntity(GSON.toJson(comment)));

				}
				catch (UnsupportedEncodingException exception) {
					Log.w(LOG_TAG, "Error encoding Comment: " + exception.getMessage());
					return;
				}

				HttpResponse response;
				try {
					response = client.execute(request);
					Log.i(LOG_TAG, "Response: " + response.getStatusLine().toString());
				} 
				catch (IOException exception) {
					Log.w(LOG_TAG, "Error sending Comment: " + exception.getMessage());
				}
			}
		};

		thread.start();

	}
	
	
	 /* The comment will be updated on elastic search*/
	 
	
	public static void updateComment(final TopLevel comment){
		Thread thread = new Thread() {
			@Override
			public void run() {
				HttpPost updateRequest = new HttpPost(SERVER_URL+comment.getDate());

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

	
	
	
	public static void retrieveComments(){
		Thread thread = new Thread() {
			@Override
			public void run() {
				HttpGet getRequest = new HttpGet("http://cmput301.softwareprocess.es:8080/cmput301w14t04/TestArea/_search?q=textComment:1");
			
				String responseJson = "";

				try {
					HttpResponse response = client.execute(getRequest);
					Log.i(LOG_TAG, "Response: " + response.getStatusLine().toString());

					HttpEntity entity = response.getEntity();
					BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));

					String output = reader.readLine();
					while (output != null) {
						responseJson+= output;
						output = reader.readLine();
					}
				}
				catch (IOException exception) {
					Log.w(LOG_TAG, "Error receiving search query response: " + exception.getMessage());
					return;
				}

				Type elasticSearchSearchResponseType = new TypeToken<ElasticSearchSearchResponse<TopLevel>>(){}.getType();
				final ElasticSearchSearchResponse<TopLevel> returnedData = GSON.fromJson(responseJson, elasticSearchSearchResponseType);


			}
		};

		thread.start();
	}
		
		
	
	
	public static void deleteComment(final Comments comment){
		Thread thread = new Thread() {
			@Override
			public void run() {
				HttpDelete httpDelete = new HttpDelete(SERVER_URL );
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
	
	public static void addPicPostCollection(Collection<TopLevel> posts) {//RENAME
		list.addAll(posts);
		//this.adapter.notifyDataSetChanged();
	}
	public static void clear() {
		list.clear();
		//this.adapter.notifyDataSetChanged();
	}
	
	public static ArrayList<TopLevel> getList() {
		return (ArrayList<TopLevel>) Collections.unmodifiableList(list);
	}
	
}

	




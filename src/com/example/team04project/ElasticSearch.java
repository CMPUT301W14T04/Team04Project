package com.example.team04project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

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
	public static final String SERVER_URL = "http://cmput301.softwareprocess.es:8080/cmput301w14t04/TestArea/";//URL CHANGED FOR RETRIEVING
	public static final String LOG_TAG = "ElasticSearch";
	static HttpClient client = new DefaultHttpClient();
	
	private static ArrayList<TopLevel> list= new ArrayList<TopLevel>();
	static String test =null;
	
	/*
	 * If you want to run push comment add an id field to the server url ex.TestArea/Test-+-
	 */
	public static void pushComment(final TopLevel comment) {
		Thread thread = new Thread() {
			Gson GSON = new Gson();

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
	 
	/*
	 * If you want to run update comment add an id field to the server url ex.TestArea/Test-+-
	 */
	public static void updateComment(final TopLevel comment){
		Thread thread = new Thread() {
			Gson GSON = new Gson();
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

	
	/*
	 * Decides to work or not. If it does want to work then it takes a few seconds to retrieve results. 
	 * Ultimately I want it to search for things with a comment
	 * tag such as TopLevel or Reply
	 */
	
	public static void retrieveComments(){
		
		Thread thread = new Thread() {
			Gson GSON = new Gson();
			String searchTerm= "11";
				@Override
				public void run() {
					HttpClient client = new DefaultHttpClient();
					HttpPost request = new HttpPost(SERVER_URL + "_search");
					String query = 	"{\"query\": {\"query_string\": {\"default_field\": \"textComment\",\"query\": \"*" + searchTerm + "*\"}}}";					
					String responseJson = "";

					try {
						request.setEntity(new StringEntity(query));
					}
					catch (UnsupportedEncodingException exception) {
						Log.w(LOG_TAG, "Error encoding search query: " + exception.getMessage());
						return;
					}

					try {
						HttpResponse response = client.execute(request);
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
					final ElasticSearchSearchResponse<TopLevel> returnedData = GSON.fromJson(responseJson, elasticSearchSearchResponseType);				//for (ElasticSearchResponse<TopLevel> r : returnedData.getHits()) {
					
					for (ElasticSearchResponse<TopLevel> r : returnedData.getHits()) {
						TopLevel level = r.getSource();
						list.add(level);
					}
			}
		};

		thread.start();	
	}
		
	public static String returnTest(){
		return test;
	}
	
	
	public static void deleteComment(final String date){
		Thread thread = new Thread() {
			@Override
			public void run() {
				HttpDelete httpDelete = new HttpDelete(SERVER_URL+date);
				try {
					HttpResponse response = client.execute(httpDelete);
					String status = response.getStatusLine().toString();
					System.out.println(status);

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
		return list;
	}
}

	




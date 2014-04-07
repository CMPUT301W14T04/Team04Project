package com.example.geocomment.elasticsearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.geocomment.CommentBrowseActivity;
import com.example.geocomment.ProfileActivity;
import com.example.geocomment.model.Commentor;
import com.example.geocomment.model.TopLevel;
import com.example.geocomment.model.UserProfile;
import com.example.geocomment.util.BitmapJsonConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class ElasticSearchOpertationUser {
	
	public static final String SERVER_URL = "http://cmput301.softwareprocess.es:8080/testing/team04_users/";

	public static final String LOG_TAG = "ElasticSearch";

	private static Gson GSON;
	
	private static UserProfile profile;
	
	public static void pushUserProfile(final UserProfile profile) {
		if (GSON == null)
			constructGson();
		
		Thread thread = new Thread() {

			@Override
			public void run() {
				HttpClient client = new DefaultHttpClient();
				HttpPost request = new HttpPost(SERVER_URL + profile.getUserID());

				try {
					request.setEntity(new StringEntity(GSON.toJson(profile)));
					Log.e("profile push", GSON.toJson(profile));
				} catch (UnsupportedEncodingException exception) {
					Log.w(LOG_TAG,
							"ERROR USER: "
									+ exception.getMessage());
					return;
				}

				HttpResponse response;
				try {
					response = client.execute(request);
					Log.i(LOG_TAG, "Response: "
							+ response.getStatusLine().toString() +" ");
				} catch (IOException exception) {
					Log.w(LOG_TAG,
							"Error sending Profile: "
									+ exception.getMessage());
				}
			}
		};

		thread.start();
	}
	
	public static UserProfile searchProfile(final ProfileActivity activity, final String ID) {

		if (GSON == null)
			constructGson();

		Thread thread = new Thread() {

			@Override
			public void run() {
				HttpClient client = new DefaultHttpClient();
				HttpPost request = new HttpPost(SERVER_URL + "_search");
				String query = "{\"query\": {\"match\": {\"ID\" :\"*"
						+ ID + "*\" }}}";
				String responseJson = "";

				try {
					request.setEntity(new StringEntity(query));
				} catch (UnsupportedEncodingException exception) {
					Log.w(LOG_TAG,
							"Error encoding search query: "
									+ exception.getMessage());
					return;
				}

				try {
					HttpResponse response = client.execute(request);
					Log.i(LOG_TAG, "Response: "
							+ response.getStatusLine().toString());

					HttpEntity entity = response.getEntity();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(entity.getContent()));

					String output = reader.readLine();
					Log.e("Replies search", output);
					while (output != null) {
						responseJson += output;
						output = reader.readLine();
					}
				} catch (IOException exception) {
					Log.w(LOG_TAG, "Error receiving search query response: "
							+ exception.getMessage());
					return;
				}
				Type elasticSearchSearchResponseType = new TypeToken<ElasticSearchSearchResponse<UserProfile>>() {
				}.getType();
				final ElasticSearchSearchResponse<UserProfile> returnedData = GSON
						.fromJson(responseJson, elasticSearchSearchResponseType);
				Runnable updateModel = new Runnable() {
					@Override
					public void run() {
						profile = (UserProfile) returnedData.getSources();
						this.notify();
					}
				};
				activity.runOnUiThread(updateModel);
			}
		};
		thread.start();
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return profile;
	}
	
	/**
	 * 
	 * This method construct a custom Gson for picture.
	 */
	private static void constructGson() {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Bitmap.class, new BitmapJsonConverter());
		GSON = builder.create();
	}
}

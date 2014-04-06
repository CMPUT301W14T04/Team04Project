package com.example.geocomment.elasticsearch;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

import com.example.geocomment.model.userProfile;
import com.google.gson.Gson;

public class ElasticSearchOpertationUser {
	
	public static final String SERVER_URL = "http://cmput301.softwareprocess.es:8080/testing/team04_users/";

	public static final String LOG_TAG = "ElasticSearch";

	private static Gson GSON;
	
	public static void pushUserProfile(final userProfile profile) {
		
		Thread thread = new Thread() {

			@Override
			public void run() {
				HttpClient client = new DefaultHttpClient();
				HttpPost request = new HttpPost(SERVER_URL);

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
}

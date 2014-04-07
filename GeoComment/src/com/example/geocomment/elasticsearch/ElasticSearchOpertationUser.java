/*
Copyright (c) 2013, Guillermo Ramirez, Nadine Yushko, Tarek El Bohtimy, Yang Wang
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright notice,
this list of conditions and the following disclaimer in the documentation
and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

The views and conclusions contained in the software and documentation are those
of the authors and should not be interpreted as representing official policies,
either expressed or implied, of the FreeBSD Project.
*/

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
import com.example.geocomment.GeoCommentActivity;
import com.example.geocomment.ProfileActivity;
import com.example.geocomment.model.Commentor;
import com.example.geocomment.model.TopLevel;
import com.example.geocomment.model.UserProfile;
import com.example.geocomment.util.BitmapJsonConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class ElasticSearchOpertationUser {
	
	public static final String SERVER_URL = "http://cmput301.softwareprocess.es:8080/testing/team04_users1/";

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
					Log.e("User profile", output);
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
						this.notifyAll();
					}
				};
				activity.runOnUiThread(updateModel);
			}
		};
		thread.start();
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

/**
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
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.example.geocomment.CommentBrowseActivity;
import com.example.geocomment.GeoCommentActivity;
import com.example.geocomment.model.Commentor;
import com.example.geocomment.model.TopLevel;
import com.example.geocomment.model.TopLevelList;
import com.example.geocomment.util.BitmapJsonConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * This Class handle the communication between the app and the sever using
 * Elasticsearch
 * 
 * Taken from https://github.com/zjullion/PicPosterComplete
 * 
 */
public class ElasticSearchOperations {

	private ElasticSearchOperationsProduct elasticSearchOperationsProduct = new ElasticSearchOperationsProduct();
	/**
	 * Define the sever URL
	 */
	public static final String SERVER_URL = "http://cmput301.softwareprocess.es:8080/testing/team04_mycomments9/";
	public static final String SERVER_URL_REPLY = "http://cmput301.softwareprocess.es:8080/testing/team04_replie1/";
	// for Log method.
	public static final String LOG_TAG = "ElasticSearch";

	public static Gson GSON;

	/**
	 * This method push a TopLevel comment to the sever.
	 * 
	 * @param model
	 */
	public static void pushComment(final Commentor model, int type) {
		Log.d("pushcomment", GSON.toJson(model));
		if (GSON == null)
			constructGson();
		String url = null;
		switch(type)
		{
		case 1:
			url=SERVER_URL + model.getID();
			Log.e("www", url);
			break;
		case 2:
			url=SERVER_URL_REPLY + model.getID();
			Log.e("www", "reply");
			break;
		}
		
		final String _URL = url;

		Thread thread = new Thread() {

			@Override
			public void run() {
				HttpClient client = new DefaultHttpClient();
				HttpPost request = new HttpPost(_URL);

				try {
					request.setEntity(new StringEntity(GSON.toJson(model)));
					Log.e("new comment push", GSON.toJson(model));
				} catch (UnsupportedEncodingException exception) {
					Log.w(LOG_TAG,
							"Error encoding PicPostModel: "
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
							"Error sending PicPostModel: "
									+ exception.getMessage());
				}
			}
		};

		thread.start();
		
	}

	/**
	 * This method get all top level comment created from the sever.
	 * 
	 * @param model
	 * @param activity
	 */

	public static void searchALL(final TopLevelList model,
			final GeoCommentActivity activity) {

		ElasticSearchOperationsProduct.searchALL(model, activity);
		
//		Log.e("Finish Elastic", "Finish ElasticsearchSearcALl");
	}

	/**
	 * search replies that match with the TopLevel ID
	 * 
	 * @param model
	 * @param activity
	 * @param ID
	 */
	public static void searchReplies(final TopLevelList model,
			final CommentBrowseActivity activity, final String ID) {

		ElasticSearchOperationsProduct.searchReplies(model, activity, ID);
	}

	
	/**
	 * 
	 * This method construct a custom Gson for picture.
	 */
	public static void constructGson() {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Bitmap.class, new BitmapJsonConverter());
		GSON = builder.create();
	}

}

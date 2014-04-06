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

	/**
	 * Define the sever URL
	 */
	public static final String SERVER_URL = "http://cmput301.softwareprocess.es:8080/testing/team04_mycomments2s/";
	public static final String SERVER_URL_REPLY = "http://cmput301.softwareprocess.es:8080/testing/team04_replie1/";
	// for Log method.
	public static final String LOG_TAG = "ElasticSearch";

	private static Gson GSON;

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
		case 3:
			url=SERVER_URL + model.getID();
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

		if (GSON == null)
			constructGson();

		Thread thread = new Thread() {

			@Override
			public void run() {
				HttpClient client = new DefaultHttpClient();
				HttpPost request = new HttpPost(SERVER_URL + "_search");
				String query = "{\"query\": {\"matchAll\": {}}}";
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
//					Log.e("paja", output);
					while (output != null) {
						responseJson += output;
						output = reader.readLine();
					}
				} catch (IOException exception) {
					Log.w(LOG_TAG, "Error receiving search query response: "
							+ exception.getMessage());
					return;
				}
				Type elasticSearchSearchResponseType = new TypeToken<ElasticSearchSearchResponse<TopLevel>>() {
				}.getType();
				final ElasticSearchSearchResponse<Commentor> returnedData = GSON
						.fromJson(responseJson, elasticSearchSearchResponseType);
				Runnable updateModel = new Runnable() {
					@Override
					public void run() {
						model.clear();
						model.addTopLevelCollection(returnedData.getSources());
					}
				};
				activity.runOnUiThread(updateModel);
			}
		};
		thread.start();
		if(!thread.isAlive())
		{
			Log.i("Thread is Alive", "true");
		}
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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

		if (GSON == null)
			constructGson();

		Thread thread = new Thread() {

			@Override
			public void run() {
				HttpClient client = new DefaultHttpClient();
				HttpPost request = new HttpPost(SERVER_URL_REPLY + "_search");
				String query = "{\"query\": {\"match\": {\"parentID\" :\"*"
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
				Type elasticSearchSearchResponseType = new TypeToken<ElasticSearchSearchResponse<TopLevel>>() {
				}.getType();
				final ElasticSearchSearchResponse<Commentor> returnedData = GSON
						.fromJson(responseJson, elasticSearchSearchResponseType);
				Runnable updateModel = new Runnable() {
					@Override
					public void run() {
						model.clear();
						model.addTopLevelCollection(returnedData.getSources());
					}
				};
				activity.runOnUiThread(updateModel);
			}
		};
		thread.start();
	}

	public static void searchReplies1(final ArrayList<Commentor> model,
			final GeoCommentActivity activity, final String ID) {

		if (GSON == null)
			constructGson();

		Thread thread = new Thread() {

			@Override
			public void run() {
				HttpClient client = new DefaultHttpClient();
				HttpPost request = new HttpPost(SERVER_URL_REPLY + "_search");
				String query = "{\"query\": {\"match\": {\"parentID\" :\"*"
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
				Type elasticSearchSearchResponseType = new TypeToken<ElasticSearchSearchResponse<TopLevel>>() {
				}.getType();
				final ElasticSearchSearchResponse<Commentor> returnedData = GSON
						.fromJson(responseJson, elasticSearchSearchResponseType);
				Runnable updateModel = new Runnable() {
					@Override
					public void run() {
						//model.clear();

						Log.i("BEFORE", "Response: "
								+ GSON.toJson(model));
						model.addAll(returnedData.getSources());
						Log.i("AFTER", "Response: "
								+ GSON.toJson(model));
					}
				};
				activity.runOnUiThread(updateModel);
			}
		};
		thread.start();
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

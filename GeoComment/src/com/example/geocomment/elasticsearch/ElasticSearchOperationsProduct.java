package com.example.geocomment.elasticsearch;


import com.example.geocomment.model.TopLevelList;
import com.example.geocomment.GeoCommentActivity;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import java.io.UnsupportedEncodingException;
import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.HttpEntity;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import com.example.geocomment.model.TopLevel;
import com.example.geocomment.model.Commentor;
import com.example.geocomment.CommentBrowseActivity;

public class ElasticSearchOperationsProduct {
	/**
	 * This method get all top level comment created from the sever.
	 * @param model
	 * @param activity
	 */
	public static void searchALL(final TopLevelList model,
			final GeoCommentActivity activity) {
		if (ElasticSearchOperations.GSON == null)
			ElasticSearchOperations.constructGson();
		Thread thread = new Thread() {
			@Override
			public void run() {
				HttpClient client = new DefaultHttpClient();
				HttpPost request = new HttpPost(
						ElasticSearchOperations.SERVER_URL + "_search");
				String query = "{\"query\": {\"matchAll\": {}}}";
				String responseJson = "";
				try {
					request.setEntity(new StringEntity(query));
				} catch (UnsupportedEncodingException exception) {
					Log.w(ElasticSearchOperations.LOG_TAG,
							"Error encoding search query: "
									+ exception.getMessage());
					return;
				}
				try {
					HttpResponse response = client.execute(request);
					Log.i(ElasticSearchOperations.LOG_TAG, "Response: "
							+ response.getStatusLine().toString());
					HttpEntity entity = response.getEntity();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(entity.getContent()));
					String output = reader.readLine();
					while (output != null) {
						responseJson += output;
						output = reader.readLine();
					}
				} catch (IOException exception) {
					Log.w(ElasticSearchOperations.LOG_TAG,
							"Error receiving search query response: "
									+ exception.getMessage());
					return;
				}
				Type elasticSearchSearchResponseType = new TypeToken<ElasticSearchSearchResponse<TopLevel>>() {
				}.getType();
				final ElasticSearchSearchResponse<Commentor> returnedData = ElasticSearchOperations.GSON
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
		if (!thread.isAlive()) {
			Log.i("Thread is Alive", "true");
		}
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * search replies that match with the TopLevel ID
	 * @param model
	 * @param activity
	 * @param ID
	 */
	public static void searchReplies(final TopLevelList model,
			final CommentBrowseActivity activity, final String ID) {
		if (ElasticSearchOperations.GSON == null)
			ElasticSearchOperations.constructGson();
		Thread thread = new Thread() {
			@Override
			public void run() {
				HttpClient client = new DefaultHttpClient();
				HttpPost request = new HttpPost(
						ElasticSearchOperations.SERVER_URL_REPLY + "_search");
				String query = "{\"query\": {\"match\": {\"parentID\" :\"*"
						+ ID + "*\" }}}";
				String responseJson = "";
				try {
					request.setEntity(new StringEntity(query));
				} catch (UnsupportedEncodingException exception) {
					Log.w(ElasticSearchOperations.LOG_TAG,
							"Error encoding search query: "
									+ exception.getMessage());
					return;
				}
				try {
					HttpResponse response = client.execute(request);
					Log.i(ElasticSearchOperations.LOG_TAG, "Response: "
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
					Log.w(ElasticSearchOperations.LOG_TAG,
							"Error receiving search query response: "
									+ exception.getMessage());
					return;
				}
				Type elasticSearchSearchResponseType = new TypeToken<ElasticSearchSearchResponse<TopLevel>>() {
				}.getType();
				final ElasticSearchSearchResponse<Commentor> returnedData = ElasticSearchOperations.GSON
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
}
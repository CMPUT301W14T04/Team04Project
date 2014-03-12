package com.example.geoloc;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class ElasticSearch {
	
	public static final String SERVER_URL = "http://cmput301.softwareprocess.es:8080/cmput301w14t04/testcomments/n100";
	public static final String LOG_TAG = "ElasticSearch";


	public static void pushComment(final JSONObject commentObj) {
		Thread thread = new Thread() {
			
			@Override
			public void run() {
				HttpClient client = new DefaultHttpClient();
				HttpPost request = new HttpPost(SERVER_URL);
				/*JSONObject json_obj = null;
				try {
					json_obj = new JSONObject("{'_index':'cmput301w14t04','_type':'TestArea','_id':'n100','_version':3,'exists':true, '_source' : {'comment':'Hello Monkey','date':'Nov 2- 2003','user':'telbohtim'}}");
					//json_obj.putOpt("_source", commentObj);
					Log.d("JSONMap", commentObj.toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				JSONArray commentArray = null;
				if (commentObj != null){
					try {
						commentArray = new JSONArray();
						commentArray.put(commentObj);
						Log.d("commentMap", commentArray.toString());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				try {
					//StringEntity entity = new StringEntity(json_obj.toString());
					StringEntity entity = new StringEntity(commentArray.toString());
					Log.d("Set Entity", entity.toString());
					request.setEntity(entity);
					Log.d("Request first", request.getEntity().toString());
				}
				catch (Exception exception) {
					Log.w(LOG_TAG, "Error encoding Comments: " + exception.getMessage());
					return;
				}

				HttpResponse response;
				try {
					response = client.execute(request);
					Log.d("Request second", request.toString());
					Log.i(LOG_TAG, "Response: " + response.getStatusLine().toString());
				} 
				catch (ClientProtocolException exception) {
					Log.w(LOG_TAG, "Error sending Comments: " + exception.getMessage());
				}
				catch (IOException exception) {
					Log.w(LOG_TAG, "Error sending Comments: " + exception.getMessage());
				}
			}
		};

		thread.start();
	}

}

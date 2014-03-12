package com.example.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Parser {

	public Parser() {
		// TODO Auto-generated constructor stub
	}
	
	public JSONArray JSONResponse(String command){
		String url = "http://cmput301.softwareprocess.es:8080/cmput301w14t04/testcomments/comments";
		JSONArray comments = null;
		JSONParser jsonParser = new JSONParser();
		String json = jsonParser.makeHttpRequest(url, "GET", "");
		try{
			JSONObject main_json_object = new JSONObject(json);
			Log.d("main JSONObject", main_json_object.toString());
			if (main_json_object.has("_source")){
				JSONObject source = main_json_object.getJSONObject("_source");
				Log.d("Comments Source", source.toString());
				if (source.has("comments")){
					comments = source.getJSONArray("comments");
					Log.d("Comments", comments.toString());
				}
			}
		}
		catch (JSONException e){
			e.printStackTrace();
		}
		return comments;
		
	}

}

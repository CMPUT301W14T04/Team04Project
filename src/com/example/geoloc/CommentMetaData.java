package com.example.geoloc;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class CommentMetaData {

	protected String comment;
	protected String date;

	protected CommentMetaData(JSONArray comments) {
		if (comments.length() > 0) {
			try {
				for (int i = 0; i < comments.length(); i++) {
					JSONObject comment_obj = comments.getJSONObject(i);
					if (comment_obj.has("comment")) {
						comment = comment_obj.getString("comment");
					}
					if (comment_obj.has("date")) {
						comment = comment_obj.getString("date");
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	protected CommentMetaData(JSONObject comment) {
		if (comment.has("comment")) {
			try {
				this.comment = comment.getString("comment");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (comment.has("date_added")) {
			try {
				this.date = comment.getString("date_added");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}

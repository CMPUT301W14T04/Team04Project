package com.example.geoloc;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CommentMetaData {

	protected String comment;
	protected String date;
	protected String aUser;
	protected String aPicture;

	protected CommentMetaData(JSONArray comments) {
		if (comments.length() > 0) {
			try {
				//for (int i = 0; i < comments.length(); i++) {
				for (int i = comments.length()-1; i >= 0; i--) {
					JSONObject comment_obj = comments.getJSONObject(i);
					if (comment_obj.has("comment")) {
						comment = comment_obj.getString("comment");
					}
					if (comment_obj.has("date")) {
						comment = comment_obj.getString("date");
					}
					if (comment_obj.has("username")) {
						comment = comment_obj.getString("username");
					}
					if (comment_obj.has("photo")) {
						comment = comment_obj.getString("photo");
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
		if (comment.has("username")) {
			try {
				this.aUser = comment.getString("username");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		if (comment.has("photo")) {
			try {
				this.aPicture = comment.getString("photo");
			} catch (JSONException e) {
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
	
	public String getUserName() {
		return aUser;
	}
	
	public void setUsername(String username) {
		this.aUser = username;
	}
	
	public String getaPicture() {
		return aPicture;
	}
	
	public void setaPicture(String aPicture) {
		this.aPicture = aPicture;
	}

}

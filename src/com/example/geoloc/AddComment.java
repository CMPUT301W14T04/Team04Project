package com.example.geoloc;

import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.utils.JSONParser;

public class AddComment extends Activity {
	HashMap<String, String> commentMap = new HashMap<String, String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_comment);
		Button submit_comment = (Button) findViewById(R.id.submit_comment);
		submit_comment.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CommentAsync postComment = new CommentAsync();
				postComment.execute();
			}
		});
	}

	public class CommentAsync extends AsyncTask<String, String, JSONArray> {

		@Override
		protected JSONArray doInBackground(String... params) {
			// TODO Auto-generated method stub
			String url = "http://cmput301.softwareprocess.es:8080/cmput301w14t04/testcomments/comments";
			JSONParser jsonParser = new JSONParser();
			String json = jsonParser.makeHttpRequest(url, "GET", "");
			JSONArray comments = null;
			try {
				JSONObject main_json_object = new JSONObject(json);
				Log.d("main JSONObject", main_json_object.toString());
				if (main_json_object.has("_source")) {
					JSONObject json_object = main_json_object
							.getJSONObject("_source");
					if (json_object.has("comments")) {
						comments = json_object.getJSONArray("comments");
					} else {
						comments = new JSONArray();
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return comments;
		}

		@SuppressLint("NewApi")
		@Override
		protected void onPostExecute(JSONArray comments) {
			// TODO Auto-generated method stub
			super.onPostExecute(comments);
			EditText comment = null;
			try {
				comment = (EditText) findViewById(R.id.comment_box);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			if (comment != null) {
				String curr_comment = comment.getText().toString();
				Log.d("Current Comment", curr_comment);
				if (!curr_comment.isEmpty()) {
					commentMap.put("comment", curr_comment);
					commentMap.put("date_added", GetDate.getDate());
					JSONObject commentObj = new JSONObject(commentMap);
					ElasticSearchOperations.pushComment(comments
							.put(commentObj));
					finish();
				} else {
					Toast.makeText(getApplicationContext(),
							"Enter a comment bozo. This is serious shit!",
							Toast.LENGTH_LONG).show();
				}
			} else {
				Toast.makeText(getApplicationContext(), "Please try again!",
						Toast.LENGTH_LONG).show();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == R.id.action_add) {
			item.setVisible(false);
			// Intent intent = new Intent(MainActivity.this, AddComment.class);
			// startActivity(intent);
		}
		if (item.getItemId() == R.id.action_user) {
			Toast.makeText(getApplicationContext(), "User!", Toast.LENGTH_LONG)
					.show();
		}
		return super.onOptionsItemSelected(item);
	}

}

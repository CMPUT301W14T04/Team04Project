package com.example.geoloc;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.example.utils.JSONParser;
import com.example.utils.ListCommentAdapter;
import com.example.utils.Parser;

public class MainActivity extends Activity {

	JSONArray comments = null;
	List<CommentMetaData> comment_meta;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		CommentAsync com_async = new CommentAsync();
		com_async.execute();
	}

	public class CommentAsync extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String url = "http://cmput301.softwareprocess.es:8080/cmput301w14t04/testcomments/comments";
			comment_meta = new ArrayList<CommentMetaData>();
			JSONParser jsonParser = new JSONParser();
			String json = jsonParser.makeHttpRequest(url, "GET", "");
			Parser parser = new Parser();
			JSONArray comments = parser.JSONResponse("");
			if (comments.length() > 0) {
				try {

					for (int i = 0; i < comments.length(); i++) {
						JSONObject comment_obj = comments.getJSONObject(i);
						CommentMetaData comment = new CommentMetaData(
								comment_obj);
						comment_meta.add(comment);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			ListView comment_list = (ListView) findViewById(R.id.comment_list);
			ListCommentAdapter adapter = new ListCommentAdapter(
					getApplicationContext(), R.layout.comment_row, comment_meta);
			comment_list.setAdapter(adapter);
			comment_list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					Log.d("Arg2", Integer.toString(arg2));
				}
			});
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
			Toast.makeText(getApplicationContext(), "Ello, Nadine!",
					Toast.LENGTH_LONG).show();
			Intent intent = new Intent(MainActivity.this, AddComment.class);
			if (comments != null) {
				intent.putExtra("comments", comments.toString());
			}
			startActivity(intent);
		}
		if (item.getItemId() == R.id.action_user) {
			Toast.makeText(getApplicationContext(), "User!", Toast.LENGTH_LONG)
					.show();
		}
		return super.onOptionsItemSelected(item);
	}

}

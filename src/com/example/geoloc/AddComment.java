package com.example.geoloc;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.utils.JSONParser;

public class AddComment extends Activity {
	protected static final int CAMERA_REQUEST = 0;
	protected static final int GALLARY_REQUEST = 1;
	HashMap<String, String> commentMap = new HashMap<String, String>();
	private ImageView imageView = null;
	private Bitmap photo = null;
	public String encodedImage = null;
	Author author = new Author();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_comment);
		Button submit_comment = (Button) findViewById(R.id.submit_comment);
		imageView = (ImageView)findViewById(R.id.imageView1);
		imageView.setImageBitmap(null);
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
			if (comment.getText().toString() != null) {
				String curr_comment = comment.getText().toString();
				Log.d("Current Comment", curr_comment);
				if (!curr_comment.isEmpty()) {
					commentMap.put("comment", curr_comment);
					commentMap.put("date_added", GetDate.getDate());
					commentMap.put("username", "Gu");
					commentMap.put("photo", encodedImage);
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
		getMenuInflater().inflate(R.menu.add_comment, menu);
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
		if (item.getItemId() == R.id.action_new_picture) {
			dialog();
		}
		return super.onOptionsItemSelected(item);
	}

	public void dialog() {
		AlertDialog.Builder builder = new Builder(AddComment.this);
		builder.setTitle("Attach a picture from:");
		builder.setPositiveButton("Camera", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
				startActivityForResult(cameraIntent, CAMERA_REQUEST); 
			}
		});

		builder.setNeutralButton("Gallary", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(intent, GALLARY_REQUEST);
			}
		});

		builder.create().show();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
			photo = (Bitmap)data.getExtras().get("data");
			imageView.setImageBitmap(photo);
			getStringFromBitmap(photo);
		}
		if (requestCode == GALLARY_REQUEST && resultCode == RESULT_OK) {  
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };
			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();
			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();
			photo = (BitmapFactory.decodeFile(picturePath));
			imageView.setImageBitmap(photo);
			getStringFromBitmap(photo);
		}  
	}

	public String getStringFromBitmap(Bitmap photo) {
		final int COMPRESSION_QUALITY = 100;
		ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
		photo.compress(Bitmap.CompressFormat.PNG, COMPRESSION_QUALITY, byteArrayBitmapStream);
		byte[] b = byteArrayBitmapStream.toByteArray();
		encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
		return encodedImage;
	}
	
	
}

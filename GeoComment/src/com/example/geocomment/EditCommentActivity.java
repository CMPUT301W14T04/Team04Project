package com.example.geocomment;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geocomment.model.Commentor;
import com.example.geocomment.model.LocationList;
import com.example.geocomment.model.Reply;
import com.example.geocomment.model.TopLevel;
import com.example.geocomment.model.User;
import com.example.geocomment.util.Resource;

public class EditCommentActivity extends Activity {
	protected String commentId;
	protected String text = "";
	protected String edited_comment;
	Commentor Comment;
	Reply reply;
	User user;
	LocationList locationList;
	Location location;
	String parentID;
	String user_name;

	int type;

	protected static final int CAMERA_REQUEST = 0;
	protected static final int GALLARY_REQUEST = 1;
	// private static final int MAX_BITMAP_DIMENSIONS = 50;
	private ImageView imageView = null;
	Bitmap photo = null;
	TextView editText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_comment);
		Intent intent = getIntent();
		commentId = intent.getStringExtra("commentId");
		text = intent.getStringExtra("text");
		user_name = intent.getStringExtra("user_name");
		editText = (TextView) findViewById(R.id.edit_comment_box);
		editText.setText(text);

		Bundle bundle = intent.getExtras();

		user = (User) bundle.getParcelable(Resource.USER_INFO);
		locationList = (LocationList) bundle
				.getParcelable(Resource.USER_LOCATION_HISTORY);
		type = bundle.getInt(Resource.TOP_LEVEL_COMMENT);
		parentID = bundle.getString("parentID");
		imageView = (ImageView) findViewById(R.id.imageView123);
		imageView.setImageBitmap(null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_comment, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			/**
			 * This ID represents the Home or Up button. In the case of this
			 * activity, the Up button is shown. Use NavUtils to allow users to
			 * navigate up one level in the application structure. For more
			 * details, see the Navigation pattern on Android Design:
			 * http://developer
			 * .android.com/design/patterns/navigation.html#up-vs-back
			 * http://developer
			 * .android.com/design/patterns/navigation.html#up-vs-back
			 */
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		if (item.getItemId() == R.id.action_new_picture) {
			Intent cameraIntent = new Intent(
					android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(cameraIntent, CAMERA_REQUEST);
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * dialog pops up after clicking action_new_picture icon in action bar opens
	 * camera or gallery
	 */
	@Override
	@SuppressWarnings("static-access")
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
			photo = (Bitmap) data.getExtras().get("data");
			// resize chosen photo
			photo = Bitmap.createScaledBitmap(photo, 100, 100, false);
			// set photo for preview
			Log.d("Image", "set photo for preview");
			imageView.setImageBitmap(photo);
		}
	}

	public void submitEdit(View view) {
		Log.d("Submit Edit Clicked", "Submit Edit Clicked");
		if (type == Resource.TYPE_TOP_LEVEL) {
			Calendar timeStamp = Calendar.getInstance();
			String text = editText.getText().toString();
			String ID = commentId;
			double[] location = user.getUserLocation();
			if (text.isEmpty()) {
				Toast.makeText(this, "You can't submit an empty text",
						Toast.LENGTH_LONG).show();
			} else {
				User u = new User();
				u.setUserName(user_name);
				Comment = new TopLevel(u, timeStamp, photo, text, location,
						ID);
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putParcelable(Resource.TOP_LEVEL_COMMENT,
						(TopLevel) Comment);
				intent.putExtras(bundle);
				setResult(Resource.RESQUEST_NEW_TOP_LEVEL, intent);
				finish();
			}
			/*Internet internet = new Internet(EditCommentActivity.this);
			if (internet.isConnectedToInternet()) {
				if (text.isEmpty()) {
					Toast.makeText(this, "You can't submit an empty text",
							Toast.LENGTH_LONG).show();
				} else {
					Comment = new TopLevel(user, timeStamp, photo, text,
							location, ID);
					Intent intent = new Intent();
					Bundle bundle = new Bundle();
					bundle.putParcelable(Resource.TOP_LEVEL_COMMENT,
							(TopLevel) Comment);
					intent.putExtras(bundle);
					setResult(Resource.RESQUEST_NEW_TOP_LEVEL, intent);
					Toast.makeText(getApplicationContext(),
							"Comment succesfully edited", Toast.LENGTH_LONG)
							.show();
				}
			} else {
				Toast.makeText(getApplicationContext(),
						"Please check your network connection",
						Toast.LENGTH_LONG).show();
			}*/
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onBackPressed()
	 */
	/*@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent next_intent = new Intent(EditCommentActivity.this,
				GeoCommentActivity.class);
		startActivity(next_intent);
		finish();
	}*/

}

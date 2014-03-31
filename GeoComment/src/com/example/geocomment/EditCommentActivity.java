package com.example.geocomment;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geocomment.model.Commentor;
import com.example.geocomment.model.LocationList;
import com.example.geocomment.model.Reply;
import com.example.geocomment.model.TopLevel;
import com.example.geocomment.model.User;
import com.example.geocomment.util.Internet;
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
		editText = (TextView) findViewById(R.id.edit_comment_box);
		editText.setText(text);

		Bundle bundle = intent.getExtras();

		user = (User) bundle.getParcelable(Resource.USER_INFO);
		locationList = (LocationList) bundle
				.getParcelable(Resource.USER_LOCATION_HISTORY);
		type = bundle.getInt(Resource.TOP_LEVEL_COMMENT);
		parentID = bundle.getString("parentID");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_comment, menu);
		return true;
	}

	public void submitEdit(View view) {
		Log.d("Submit Edit Clicked", "Submit Edit Clicked");
		if (type == Resource.TYPE_TOP_LEVEL) {
			Calendar timeStamp = Calendar.getInstance();
			String text = editText.getText().toString();
			String ID = commentId;
			double[] location = user.getUserLocation();
			Internet internet = new Internet(EditCommentActivity.this);
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
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent next_intent = new Intent(EditCommentActivity.this,
				GeoCommentActivity.class);
		startActivity(next_intent);
		finish();
	}

}

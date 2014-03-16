package com.example.geocomment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.geocomment.model.LocationList;
import com.example.geocomment.model.Reply;
import com.example.geocomment.model.TopLevel;
import com.example.geocomment.model.User;
import com.example.geocomment.util.Resource;

public class CreateCommentActivity extends Activity {

	Button pushButton;
	EditText textComment;

	TopLevel topLevel;
	Reply reply;
	User user;
	LocationList locationList;
	Location location;

	int type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_comment);
		// Show the Up button in the action bar.
		setupActionBar();

		pushButton = (Button) findViewById(R.id.submit_comment);
		textComment = (EditText) findViewById(R.id.comment_box);

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();

		user = (User) bundle.getParcelable(Resource.USER_INFO);
		locationList = (LocationList) bundle
				.getParcelable(Resource.USER_LOCATION_HISTORY);
		type = bundle.getInt(Resource.TOP_LEVEL_COMMENT);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_comment, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void submitComment(View view) {
		if (type == Resource.TYPE_TOP_LEVEL) {
			Calendar timeStamp = Calendar.getInstance();
			Bitmap aPicture = null;
			String text = textComment.getText().toString();
			String ID = Resource.generateID();
			double[] location = user.getUserLocation();
			reply = new Reply(user, timeStamp, aPicture, "hollalala", location);
			List<Reply> replies = new ArrayList<Reply>();
			replies.add(reply);

			if (text.isEmpty()) {
				Toast.makeText(this, "You can't submit an empty text",
						Toast.LENGTH_LONG).show();
			} else {

				topLevel = new TopLevel(user, timeStamp, aPicture, text,
						location, ID);
				topLevel.setReplies(replies);

				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putParcelable(Resource.TOP_LEVEL_COMMENT, topLevel);
				intent.putExtras(bundle);
				setResult(Resource.RESQUEST_NEW_TOP_LEVEL, intent);
				Log.e("Comment ID:", topLevel.getID());

				finish();
			}

		}

	}
}

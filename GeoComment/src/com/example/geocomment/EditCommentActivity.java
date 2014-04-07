/*
Copyright (c) 2013, Guillermo Ramirez, Nadine Yushko, Tarek El Bohtimy, Yang Wang
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright notice,
this list of conditions and the following disclaimer in the documentation
and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

The views and conclusions contained in the software and documentation are those
of the authors and should not be interpreted as representing official policies,
either expressed or implied, of the FreeBSD Project.
*/

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

import com.example.geocomment.elasticsearch.ElasticSearchOperations;
import com.example.geocomment.model.Commentor;
import com.example.geocomment.model.LocationList;
import com.example.geocomment.model.Reply;
import com.example.geocomment.model.TopLevel;
import com.example.geocomment.model.User;
import com.example.geocomment.util.Resource;
import com.google.gson.Gson;

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
	int likes;

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
		String topLevel_string = intent.getStringExtra("comment");
		Commentor topLevel = new Gson().fromJson(topLevel_string, TopLevel.class);
		commentId = topLevel.getID();
		text = topLevel.getTextComment();
		user_name = topLevel.getUserName();
		likes = topLevel.getLikes();
		editText = (TextView) findViewById(R.id.edit_comment_box);
		editText.setText(text);
		user = topLevel.getUser();
		photo = topLevel.getaPicture();
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
		Calendar timeStamp = Calendar.getInstance();
		String text = editText.getText().toString();
		String ID = commentId;
		double[] location = user.getUserLocation();
		if (text.isEmpty()) {
			Toast.makeText(this, "You can't submit a blank comment",
					Toast.LENGTH_LONG).show();
		} else {
			Comment = new TopLevel(user, timeStamp, photo, text, location,
					ID, likes);
			ElasticSearchOperations.pushComment(Comment, 3);
			Log.d("REQUEST TYPE EDIT", Integer.toString(Resource.COMMENT_EDITED));
			setResult(Resource.COMMENT_EDITED);
			finish();
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

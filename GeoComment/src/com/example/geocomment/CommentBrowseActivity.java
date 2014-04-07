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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geocomment.elasticsearch.ElasticSearchOperations;
import com.example.geocomment.model.Commentor;
import com.example.geocomment.model.Reply;
import com.example.geocomment.model.TopLevel;
import com.example.geocomment.model.TopLevelList;
import com.example.geocomment.model.User;
import com.example.geocomment.model.favourites;
import com.example.geocomment.util.BitmapJsonConverter;
import com.example.geocomment.util.Format;
import com.example.geocomment.util.Internet;
import com.example.geocomment.util.Resource;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * This activity allow the user to see the top level comment at the top and its
 * replies.
 * 
 * @author CMPUT 301 Team 04
 * 
 */
public class CommentBrowseActivity extends Activity {

	private TextView username;
	private TextView date;
	private Button likes;
	private TextView text;
	private TextView location;
	private ImageView picture;
	private ListView replies;

	private TopLevel toplevel;
	private TopLevelList repliesList;
	private CommentAdapter adapter;
	private User user;
	
	private Internet internet;

	/*
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 * Creates all the information that comes with a comment, such as
	 * username, date, text comment, location, picture, the number of likes
	 * the comment has and all the replies the comment has
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment_browse);
		// Show the Up button in the action bar.
		setupActionBar();

		username = (TextView) findViewById(R.id.usernameBrowse);
		date = (TextView) findViewById(R.id.dateBrowse);
		text = (TextView) findViewById(R.id.textBrowse);
		location = (TextView) findViewById(R.id.locationBrowse);
		picture = (ImageView) findViewById(R.id.topLevelPicture);
		likes = (Button) findViewById(R.id.likeBrowse);
		replies = (ListView) findViewById(R.id.repliesListViewBrowse);
		
		internet= new Internet(CommentBrowseActivity.this);
		repliesList = new TopLevelList();
		adapter = new CommentAdapter(getApplicationContext(),
				R.layout.comment_row, repliesList.getList());

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();

		toplevel = (TopLevel) bundle.getParcelable("test");
		user = (User) bundle.getParcelable("user");

		/*
		 * just variable that store info from comment object
		 */
		String displayUsername = toplevel.getUserName();
		String displayDate = Format.dateFormat(toplevel.getDate());
		String displayText = toplevel.getTextComment();

		username.setText(displayUsername);
		date.setText(displayDate);
		text.setText(displayText);
		location.setText(loca(toplevel.getaLocation()));
		likes.setText("Likes: " + toplevel.getLikes());

		if (toplevel.getaPicture() != null) {
			picture.setImageBitmap(toplevel.getaPicture());
		}

		replies.setAdapter(adapter);
		repliesList.setAdapter(adapter);
		
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Bitmap.class, new BitmapJsonConverter());

		ElasticSearchOperations.searchReplies(repliesList,
				CommentBrowseActivity.this, toplevel.getID());
		if(internet.isConnectedToInternet()==false){
			
		}

	}
	
	/*
	 * Increments the like button when it is pressed
	 */
	public void likeIncrement(View v) {
		toplevel.setLikes(toplevel.getLikes() + 1);
		((Button) v).setText("Like: " + toplevel.getLikes());
		ElasticSearchOperations.pushComment(toplevel, 3);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	@Override
	protected void onPause() {
		super.onPause();
		favourites.updateBrowse(repliesList.getList());
	}

	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.comment_browse, menu);
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
		case R.id.reply_comment:
			if (user.getUserName() == null) {
				Toast.makeText(
						this,
						"Before you share your experience with others,"
								+ "you need to create a "
								+ "username", Toast.LENGTH_LONG).show();
			} else
				creatNewComment();

		}
		return super.onOptionsItemSelected(item);
	}

	// Thinking in use this method when the comment is create so the user do to
	// have to wait till the geocoder find the location.
	/**
	 * this method use the latitude and longitude of the comment to determine
	 * the city, and country using geocoder.
	 * 
	 * @param location
	 * @return
	 */
	private StringBuilder loca(double[] location) {
		double lat = location[0];
		double lon = location[1];
		StringBuilder local = null;
		List<Address> addresses;

		try {
			Geocoder geo = new Geocoder(getApplicationContext(), Locale.ENGLISH);
			addresses = geo.getFromLocation(lat, lon, 1);
			local = new StringBuilder();
			if (Geocoder.isPresent()) {
				try {
					Address returnAddress = addresses.get(0);

					String localityString = returnAddress.getLocality();
					String city = returnAddress.getCountryName();

					local.append(localityString + ", ");
					local.append(city + ".");
				} catch (IndexOutOfBoundsException e) {
					Toast.makeText(this, "Error in Location",
							Toast.LENGTH_SHORT).show();
				}

			} else {
				Toast.makeText(getApplicationContext(), "Geocoder not present",
						Toast.LENGTH_SHORT).show();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block

			Log.e("tag", e.getMessage());
		}
		return local;
	}

	/*
	 * Creates a new comment, does not let the user create a 
	 * comment if the user does not have a username
	 */
	public void creatNewComment() {
		Intent intent = new Intent(CommentBrowseActivity.this,
				CreateCommentActivity.class);

		if (user.getUserName() != null) {
			Bundle bundle = new Bundle();
			bundle.putParcelable(Resource.USER_INFO, user);
			bundle.putString("parentID", toplevel.getID());
			bundle.putInt(Resource.TOP_LEVEL_COMMENT, Resource.TYPE_REPLY);
			intent.putExtras(bundle);
			startActivityForResult(intent, 100);
		} else
			Toast.makeText(this, "Please make a username before creating a comment",
					Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (requestCode) {
		case 100:
			if (data != null) {
				Reply aTopLevel = data
						.getParcelableExtra(Resource.TOP_LEVEL_COMMENT);
				repliesList.AddTopLevel(aTopLevel, 2);
				Log.e("Comment ID in Main", aTopLevel.getID());
			} else
				Log.e("error in action", "data = null");
			break;
		}
}

}

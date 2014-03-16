package com.example.geocomment;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.geocomment.elasticsearch.ElasticSearchOperations;
import com.example.geocomment.model.LocationList;
import com.example.geocomment.model.TopLevel;
import com.example.geocomment.model.TopLevelList;
import com.example.geocomment.model.User;
import com.example.geocomment.model.UserPreference;
import com.example.geocomment.util.GPSLocation;
import com.example.geocomment.util.Internet;
import com.example.geocomment.util.Resource;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 
 * 
 * @author CMPUT 301 Team 04
 * 
 */
public class GeoCommentActivity extends Activity {

	Gson gson;
	Internet internet;
	GPSLocation location;

	User user;
	TopLevelList favouritesList;
	TopLevelList commentList;
	LocationList locationHistory;
	UserPreference userPre;
	CommentAdapter adapter;

	Spinner sortList;
	ListView commentListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// initialization variables
		sortList = (Spinner) findViewById(R.id.sortList);
		commentListView = (ListView) findViewById(R.id.commentListView);

		favouritesList = new TopLevelList();
		commentList = new TopLevelList();
		locationHistory = new LocationList();
		adapter = new CommentAdapter(getApplicationContext(), R.layout.comment_row, commentList.getList());

		location = new GPSLocation(GeoCommentActivity.this);
		internet = new Internet(GeoCommentActivity.this);
		GsonBuilder gsonBuilder = new GsonBuilder();
		gson = gsonBuilder.create(); 
		userPre = new UserPreference();
		
		commentListView.setAdapter(adapter);
		commentList.setAdapter(adapter);
		ElasticSearchOperations.searchALL(commentList, GeoCommentActivity.this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.create_new_comment:
			if (user.getUserName() == null) {
				Toast.makeText(
						this,
						"Before you share your exp with other,"
								+ "you need to identificate yourself with an "
								+ "UserName", Toast.LENGTH_LONG).show();
			} else
				creatNewComment();
			return true;
		case R.id.settings:
			openSettings();
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	protected void onStart() {
		super.onStart();
		double [] locations={location.getLatitude(),location.getLongitude()};
		// get a json string a check if the null or not
		// if null create a new user without username.
		String info = load(Resource.GENERAL_INFO_LOAD);
		if (info == null) {
			
			user = new User(locations, "Guillermo", Resource.generateID());
			locationHistory.addLocation(location.getLocation());
			userPre = new UserPreference(user.getUserName(), user.getID(),
					locationHistory);
			locationHistory.addLocation(location.getLocation());
			save(Resource.GENERAL_INFO_SAVE);
		} else {
			userPre = gson.fromJson(info, UserPreference.class);
			user = new User(locations, userPre.getUserName(), userPre.getId());
			locationHistory = userPre.getLocationList();
		}
	}

	protected void onPause() {
		super.onPause();

		save(Resource.GENERAL_INFO_SAVE);
	}

	protected void onResume() {
		super.onResume();
	}

	public String load(int type) {
		switch (type) {
		case Resource.GENERAL_INFO_LOAD:
			String userInfo = null;
			FileInputStream fis;
			try {
				fis = openFileInput(Resource.GENERAL_INFO);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						fis));
				userInfo = in.readLine();
				fis.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return userInfo;
		case Resource.FAVOURITE_LOAD:
			String favouritesJson = null;
			try {
				fis = openFileInput(Resource.FAVOURITE_FILE);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						fis));
				favouritesJson = in.readLine();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return favouritesJson;

		}

		return null;
	}

	/**
	 * This method save in file information
	 * 
	 * @param type
	 *            all the type are define in the {@link Resource} class
	 * @param anObject
	 */
	public void save(int type) {
		FileOutputStream fos;
		switch (type) {
		case Resource.GENERAL_INFO_SAVE:
			String userPreference = gson.toJson(userPre);
			try {
				fos = openFileOutput(Resource.GENERAL_INFO,
						Context.MODE_PRIVATE);
				fos.write(userPreference.getBytes());
				Log.e("Guillermo", userPreference);
				fos.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case Resource.FAVOURITE_SAVE:

		}
	}

	public void creatNewComment() {
		Intent intent = new Intent(GeoCommentActivity.this,
				CreateCommentActivity.class);
		Bundle bundle = new Bundle();
		bundle.putParcelable(Resource.USER_INFO, user);
		bundle.putParcelable(Resource.USER_LOCATION_HISTORY, locationHistory);
		bundle.putInt(Resource.TOP_LEVEL_COMMENT, Resource.TYPE_TOP_LEVEL);
		intent.putExtras(bundle);
		startActivityForResult(intent, Resource.RESQUEST_NEW_TOP_LEVEL);
	}

	public void openSettings() {
		Intent intent = new Intent(GeoCommentActivity.this , OptionActivity.class);
		intent.putExtra("Uusername", user.getUserName());
		startActivityForResult(intent, 90);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		switch(requestCode)
		{
		case Resource.RESQUEST_NEW_TOP_LEVEL:
			if(data!=null){
			TopLevel aTopLevel = data.getParcelableExtra(Resource.TOP_LEVEL_COMMENT);
			commentList.AddTopLevel(aTopLevel);
			Log.e("Comment ID in MAin", aTopLevel.getID());
			}
			else
				Log.e("error in acti", "data = null");
			break;
		case 90:
			if(data!=null)
			{
				String name = data.getStringExtra("setUsername");
				user.setUserName(name);
				Log.e("USERNAME CHANGES TO", name);
				userPre = new UserPreference(user.getUserName(), user.getID(),
						locationHistory);
				save(Resource.GENERAL_INFO_SAVE);
			}
			break;
		}

	}

}

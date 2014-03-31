package com.example.geocomment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geocomment.elasticsearch.ElasticSearchOperations;
import com.example.geocomment.model.Commentor;
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
import com.google.gson.reflect.TypeToken;

/**
 * 
 * This is the main activity for the application
 * 
 * @author CMPUT 301 Team 04
 * 
 */

public class GeoCommentActivity extends Activity implements
		OnItemClickListener, OnItemSelectedListener {

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

	public void cacheSave() {
		File folder = getCacheDir();
		File cache = new File(folder, Resource.CACHE_STORE);
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(cache);
			String string = gson.toJson(commentList.getList()) + "\n";
			Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
			fos.write(string.getBytes());
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cacheLoad() {
		File folder = getCacheDir();
		File cache = new File(folder, Resource.CACHE_STORE);
		FileInputStream fis;
		try {
			fis = new FileInputStream(cache);
			BufferedReader in = new BufferedReader(new InputStreamReader(fis));
			String readCache = in.readLine();
			Type Type = new TypeToken<ArrayList<TopLevel>>() {
			}.getType();
			List<Commentor> listFav = gson.fromJson(readCache, Type);
			Toast.makeText(this, readCache, Toast.LENGTH_SHORT).show();
			fis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// initialization variables
		sortList = (Spinner) findViewById(R.id.sortList);
		sortList.setOnItemSelectedListener(this);
		ArrayAdapter<CharSequence> spinner_adapter = ArrayAdapter
				.createFromResource(this, R.array.sorting_array,
						android.R.layout.simple_spinner_item);
		spinner_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sortList.setAdapter(spinner_adapter);
		commentListView = (ListView) findViewById(R.id.commentListView);

		favouritesList = new TopLevelList();
		commentList = new TopLevelList();
		locationHistory = new LocationList();
		// adapter = new CommentAdapter(getApplicationContext(),
		// R.layout.comment_row, commentList.getList());

		location = new GPSLocation(GeoCommentActivity.this);
		internet = new Internet(GeoCommentActivity.this);
		GsonBuilder gsonBuilder = new GsonBuilder();
		gson = gsonBuilder.create();
		userPre = new UserPreference();
		// commentListView.setAdapter(adapter);
		// commentList.setAdapter(adapter);
		ElasticSearchOperations.searchALL(commentList, GeoCommentActivity.this);
		commentListView.setOnItemClickListener(this);
		registerForContextMenu(commentListView);
		commentListView
				.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> arg0,
							View arg1, int arg2, long arg3) {
						// TODO Auto-generated method stub
						return false;
					}

				});
		load(Resource.FAVOURITE_LOAD);
		/*
		 * Toast.makeText(this, commentList.getList().toString(),
		 * Toast.LENGTH_SHORT).show(); cacheSave(); cacheLoad();
		 */
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * Before the user can make a comment, they must enter in a username
	 */
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
			return true;
		case R.id.refresh_button:
			internet = new Internet(GeoCommentActivity.this);
			if (internet.isConnectedToInternet()) {
				commentList.clear();
				ElasticSearchOperations.searchALL(commentList,
						GeoCommentActivity.this);
			} else {
				Toast.makeText(getApplicationContext(),
						"No internet connection!", Toast.LENGTH_LONG).show();
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * When starting, the application will get the user location, which will be
	 * the users default location when posting comments. If the user enters no
	 * username, the default username is Guillermo
	 */
	protected void onStart() {
		super.onStart();
		double[] locations = { location.getLatitude(), location.getLongitude() };
		// get a json string a check if the null or not
		// if null create a new user without username.
		String info = load(Resource.GENERAL_INFO_LOAD);
		if (info == null) {

			user = new User(locations, null, Resource.generateID());
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

	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long id) {

		Commentor topLevel = commentList.getComment(position);
		Intent intent = new Intent(this, CommentBrowseActivity.class);
		Bundle bundle = new Bundle();
		bundle.putParcelable("test", (TopLevel) topLevel);
		bundle.putParcelable("user", user);
		intent.putExtras(bundle);
		startActivity(intent);

	}

	/**
	 * This loads
	 * 
	 * @param type
	 * @return
	 */
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
			try {
				fis = openFileInput(Resource.FAVOURITE_FILE);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						fis));
				String fav = in.readLine();
				Type Type = new TypeToken<ArrayList<TopLevel>>() {
				}.getType();
				List<Commentor> listFav = gson.fromJson(fav, Type);
				// Toast.makeText(this, listFav.toString(),
				// Toast.LENGTH_SHORT).show();
				for (Commentor c : listFav) {
					c.setFavourite(true);
					/*
					 * for(int i=0; i<commentList.getList().size();i++){
					 * 
					 * }
					 */
					Toast.makeText(this, c.getTextComment(), Toast.LENGTH_SHORT)
							.show();
					// commentList.addFav(c);
				}
				// commentList.setFavourite(listFav);
				fis.close();
			} catch (FileNotFoundException e) {
				// TODOAuto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

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
			commentList.updateFav();
			try {
				fos = openFileOutput(Resource.FAVOURITE_FILE,
						Context.MODE_PRIVATE);

				String fav = gson.toJson(commentList.getFavList())+"\n"; 
				Toast.makeText(this, fav, Toast.LENGTH_SHORT).show();
				fos.write(fav.getBytes());
				fos.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
	}

	/**
	 * This pases all the data from one activity to another This includes the
	 * username, the ID, and the location of the comment
	 */
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

	public void editComment() {
		Intent intent = new Intent(GeoCommentActivity.this,
				EditCommentActivity.class);
		Bundle bundle = new Bundle();
		bundle.putParcelable(Resource.USER_INFO, user);
		bundle.putParcelable(Resource.USER_LOCATION_HISTORY, locationHistory);
		bundle.putInt(Resource.TOP_LEVEL_COMMENT, Resource.TYPE_TOP_LEVEL);
		intent.putExtras(bundle);
		startActivityForResult(intent, Resource.RESQUEST_NEW_TOP_LEVEL);
	}

	/**
	 * This opens up the settings and the user can either make a new username or
	 * change their current username
	 */
	public void openSettings() {
		Intent intent = new Intent(GeoCommentActivity.this,
				OptionActivity.class);
		intent.putExtra("Username", user.getUserName());
		startActivityForResult(intent, 90);
	}

	/**
	 * Sets username, and adds all the information about the top level comment
	 * (such as ID, location, username, the comment itself and possibly a
	 * picture) to a list of all the comments.
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (requestCode) {
		case Resource.RESQUEST_NEW_TOP_LEVEL:

			if (data != null) {
				TopLevel aTopLevel = data
						.getParcelableExtra(Resource.TOP_LEVEL_COMMENT);
				commentList.AddTopLevel(aTopLevel, 1);
				Log.e("Comment ID in MAin", aTopLevel.getID());
			} else
				Log.e("error in acti", "data = null");
			break;
		case 90:
			if (data != null) {
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

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		if (parent.getItemAtPosition(pos).equals("Home")) {
			//commentList.updateDate();
			adapter = new CommentAdapter(getApplicationContext(),
					R.layout.comment_row, commentList.getList());
			commentListView.setAdapter(adapter);
			commentList.setAdapter(adapter);

		
		} else if (parent.getItemAtPosition(pos).equals("Favourites")) {
			commentList.updateFav();
			adapter = new CommentAdapter(getApplicationContext(),
					R.layout.comment_row, commentList.getFavList());
			commentListView.setAdapter(adapter);
			commentList.setAdapter(adapter);
			save(Resource.FAVOURITE_SAVE);
			
		} else if (parent.getItemAtPosition(pos).equals("Picture")) {
			commentList.updatePicture();
			adapter = new CommentAdapter(getApplicationContext(),
					R.layout.comment_row, commentList.getPictureList());
			commentListView.setAdapter(adapter);
			commentList.setAdapter(adapter);
		} else if (parent.getItemAtPosition(pos).equals("Score")) {
			commentList.updateSocre();
			adapter = new CommentAdapter(getApplicationContext(),
					R.layout.comment_row, commentList.getScoreList());
			commentListView.setAdapter(adapter);
			commentList.setAdapter(adapter);
		} else if (parent.getItemAtPosition(pos).equals("Proximity to me")) {
			commentList.updateProxiMe();
			adapter = new CommentAdapter(getApplicationContext(),
					R.layout.comment_row, commentList.getProxiMeList());
			commentListView.setAdapter(adapter);
			commentList.setAdapter(adapter);
		} else if (parent.getItemAtPosition(pos).equals(
				"Proximity to another location")) {
			adapter = new CommentAdapter(getApplicationContext(),
					R.layout.comment_row, commentList.getProxiLocList());
			commentListView.setAdapter(adapter);
			commentList.setAdapter(adapter);
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		// Long click to edit comment
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		switch (item.getItemId()) {
		case R.id.edit:
			// info.targetView.
			String text = ((TextView) info.targetView
					.findViewById(R.id.comment)).getText().toString();
			String id = ((TextView) info.targetView
					.findViewById(R.id.commentId)).getText().toString();
			Intent intent = new Intent(GeoCommentActivity.this,
					EditCommentActivity.class);
			intent.putExtra("commentId", id);
			intent.putExtra("text", text);
			Bundle bundle = new Bundle();
			bundle.putParcelable(Resource.USER_INFO, user);
			bundle.putParcelable(Resource.USER_LOCATION_HISTORY,
					locationHistory);
			bundle.putInt(Resource.TOP_LEVEL_COMMENT, Resource.TYPE_TOP_LEVEL);
			intent.putExtras(bundle);
			startActivityForResult(intent, Resource.RESQUEST_NEW_TOP_LEVEL);
			// startActivity(intent);
		default:
			return super.onContextItemSelected(item);
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		if (v.getId() == R.id.commentListView) {
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.comment_menu, menu);
		}
	}

}

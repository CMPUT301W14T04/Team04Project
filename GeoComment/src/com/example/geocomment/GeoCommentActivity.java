/**
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
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
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
import android.widget.EditText;
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
import com.example.geocomment.model.UserProfile;
import com.example.geocomment.model.favourites;
import com.example.geocomment.util.BitmapJsonConverter;
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

	private GeoCommentActivityProduct geoCommentActivityProduct = new GeoCommentActivityProduct();
	Gson gson;
	Internet internet;
	GPSLocation location;

	User user;
	TopLevelList commentList;
	LocationList locationHistory;
	UserPreference userPre;
	CommentAdapter adapter;
	UserProfile profile;

	Spinner sortList;
	ListView commentListView;
	ArrayList<Commentor> cacheList;
	final Context context = this;
	public double[] modifiedLocation;

	private void constructGson() {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Bitmap.class, new BitmapJsonConverter());
		gson = builder.create();
	}

	
	/**
	 * This function takes the last
	 * comments gotten from elastic search and 
	 * saves them into memory just incase
	 * the user has not internet the next time they access the app
	 */
	public void cacheSave() {
		this.deleteFile(Resource.CACHE_STORE);
		File folder = getCacheDir();
		File cache = new File(folder, Resource.CACHE_STORE);
		FileOutputStream fos;
		try {
			if (gson == null)
				constructGson();
			fos = new FileOutputStream(cache);
			String string = gson.toJson(commentList.getList()) + "\n";
			fos.write(string.getBytes());
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This function loads the comments
	 * saved from the last time the user had internet
	 * and shows it them in the event they have no internet available
	 */
	public List<Commentor> cacheLoad() {
		File folder = getCacheDir();
		File cache = new File(folder, Resource.CACHE_STORE);
		FileInputStream fis;
		try {
			fis = new FileInputStream(cache);
			BufferedReader in = new BufferedReader(new InputStreamReader(fis));
			String readCache = in.readLine();
			Log.i("Cached:", readCache);
			Type Type = new TypeToken<ArrayList<TopLevel>>() {
			}.getType();
			List<Commentor> listFav = gson.fromJson(readCache, Type);
			fis.close();
			return listFav;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		cacheList = new ArrayList<Commentor>();
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

		commentList = new TopLevelList();
		locationHistory = new LocationList();
		location = new GPSLocation(GeoCommentActivity.this);
		internet = new Internet(GeoCommentActivity.this);
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder
		.registerTypeAdapter(Bitmap.class, new BitmapJsonConverter());

		gson = gsonBuilder.create();

		userPre = new UserPreference();

		
		ElasticSearchOperations.searchALL(commentList, GeoCommentActivity.this);
		Toast.makeText(this, "" + commentList.getList().size(),
				Toast.LENGTH_SHORT).show();
		commentListView.setOnItemClickListener(this);
		registerForContextMenu(commentListView);
		commentListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0,
					View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				return false;
			}

		});
		
		load(Resource.FAVOURITE_LOAD);
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		/**
		 * Inflate the menu; this adds items to the action bar if it is present.
		 */
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * Before the user can make a comment, they must enter in a username
	 */
	@Override
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
				geoCommentActivityProduct.creatNewComment(user, locationHistory, this);
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
	@Override
	protected void onStart() {
		super.onStart();
		double[] locations = { location.getLatitude(), location.getLongitude() };
		/**
		 * get a json string and check if the null or not
		 * if null create a new user without username.
		 */
		String info = load(Resource.GENERAL_INFO_LOAD);
		if (info == null) {

			user = new User(locations, null, Resource.generateID());
			profile = new UserProfile(user);
			locationHistory.addLocation(location.getLocation());
			userPre = new UserPreference(user.getUserName(), user.getID(),
					locationHistory,profile);
			locationHistory.addLocation(location.getLocation());
			save(Resource.GENERAL_INFO_SAVE);
		} else {
			userPre = gson.fromJson(info, UserPreference.class);
			user = new User(locations, userPre.getUserName(), userPre.getId());
			locationHistory = userPre.getLocationList();
			profile = userPre.getProfile();

		}

	}

	@Override
	protected void onPause() {
		super.onPause();
		save(Resource.GENERAL_INFO_SAVE);

		if (internet.isConnectedToInternet() == true) {
			cacheSave();
		}
		commentList.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		ElasticSearchOperations.searchALL(commentList, this);

		if(internet.isConnectedToInternet()==false){
			cacheLoad();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long id) {

		Commentor topLevel = commentList.getComment(position);
		Intent intent = new Intent(getApplicationContext(), CommentBrowseActivity.class);
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
			
		/*
		 * This function loads the saved favourites
		 * so that the user can read them regardless
		 * of network connectivity
		 */
		case Resource.FAVOURITE_LOAD:
			try {
				fis = openFileInput(Resource.FAVOURITE_FILE);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						fis));
				String fav = in.readLine();
				Type Type = new TypeToken<ArrayList<TopLevel>>() {
				}.getType();
				List<Commentor> listFav = gson.fromJson(fav, Type);

				fis.close();
		
				favourites.load(listFav);
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
			/*
			 * This function takes the favourites list and
			 * saves into memory so that the user can access
			 * them regardless of network connectivity
			 */
		case Resource.FAVOURITE_SAVE:
			favourites.updateGeo(commentList.getList());
			this.deleteFile(Resource.FAVOURITE_FILE);
			try {
				if(gson==null){
					constructGson();
					}
				fos = openFileOutput(Resource.FAVOURITE_FILE,
						Context.MODE_PRIVATE);

				String fav = gson.toJson(favourites.returnFav()) + "\n";

				fos.write(fav.getBytes());
		

				fos.close();
			/*	if (internet.isConnectedToInternet()==true){
					ArrayList<Commentor> replies=new ArrayList<Commentor>();
					fos = openFileOutput(Resource.FAVOURITE_REPLIES,
							Context.MODE_PRIVATE);
					for(Commentor c:favourites.returnFav()){
						ElasticSearchOperations.searchReplies1(replies,
								GeoCommentActivity.this, c.getID());
						Toast.makeText(this, gson.toJson(replies), Toast.LENGTH_SHORT).show();
					}
					String favReplies=gson.toJson(replies);
					//Toast.makeText(this, favReplies, Toast.LENGTH_SHORT).show();
					fos.write(favReplies.getBytes());
					fos.close();
<<<<<<< HEAD
					
				}*/

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
	 * This opens up the settings and the user can either make a new username or
	 * change their current username
	 */
	public void openSettings() {
		Intent intent = new Intent(GeoCommentActivity.this,
				OptionActivity.class);
		Bundle bundle = new Bundle();
		bundle.putParcelable("Profile", profile);
		intent.putExtras(bundle);
		startActivityForResult(intent, 90);
	}

	/**
	 * Sets username, and adds all the information about the top level comment
	 * (such as ID, location, username, the comment itself and possibly a
	 * picture) to a list of all the comments.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("REQUEST CODE", Integer.toString(requestCode));
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
		case Resource.COMMENT_EDITED:
			Log.d("INSIDE COMMENT EDIT", "INSIDE COMMENT EDIT");
			commentList.clear();
			//commentList = new TopLevelList();
			ElasticSearchOperations.searchALL(commentList,
					GeoCommentActivity.this);
		case 90:
			if (data != null) {
				profile = data.getParcelableExtra("profile");
				
				user.setUserName(profile.getUsername());

				userPre = new UserPreference(user.getUserName(), user.getID(),
						locationHistory,profile);
				save(Resource.GENERAL_INFO_SAVE);
			}
			break;
		}

	}

	
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		if (parent.getItemAtPosition(pos).equals("Home")) {
			// commentList.updateDate();
			if (internet.isConnectedToInternet() == false) {
				Toast.makeText(this, "No internet from last visit",
						Toast.LENGTH_LONG).show();
				commentList.clear();
				commentList.addTopLevelCollection(cacheLoad());
				adapter = new CommentAdapter(getApplicationContext(),
						R.layout.comment_row, commentList.getList());
			} else {
				adapter = new CommentAdapter(getApplicationContext(),
						R.layout.comment_row, commentList.getList());
			}
			commentListView.setAdapter(adapter);
			commentList.setAdapter(adapter);
			
			/*
			 * This option shows the comments that
			 * the user has favourited
			 */
		} else if (parent.getItemAtPosition(pos).equals("Favourites")) {
			favourites.updateGeo(commentList.getList());
			adapter = new CommentAdapter(getApplicationContext(),
					R.layout.comment_row, favourites.returnFav());
			commentListView.setAdapter(adapter);
			commentList.setAdapter(adapter);
			
			/*
			 * This option shows the user
			 * all the comments sorted by date
			 */
		} else if (parent.getItemAtPosition(pos).equals("Date")) {
			if (internet.isConnectedToInternet() == false) {
				Toast.makeText(this, "No internet from last visit",
						Toast.LENGTH_LONG).show();
				commentList.clear();
				commentList.addTopLevelCollection(cacheLoad());
				adapter = new CommentAdapter(getApplicationContext(),
						R.layout.comment_row, commentList.getList());
			} else {
				adapter = new CommentAdapter(getApplicationContext(),
						R.layout.comment_row, commentList.getList());
			}
			commentListView.setAdapter(adapter);
			commentList.setAdapter(adapter);
			
			/*
			 * this option shows the user
			 * all the comments sorted by picture
			 */
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
			/*
			 * this option shows the user
			 * all the comments sorted by location
			 * relative to them
			 */
		} else if (parent.getItemAtPosition(pos).equals("Proximity to me")) {
			commentList.updateProxiMe();

			adapter = new CommentAdapter(getApplicationContext(),
					R.layout.comment_row, commentList.getProxiMeList());
			commentListView.setAdapter(adapter);
			commentList.setAdapter(adapter);
			
			/*
			 * this option shows the user
			 * all the comments sorted by location
			 * relative to another location
			 */
		} else if (parent.getItemAtPosition(pos).equals(
				"Proximity to another location")) {
			openDialogLocation();
			commentList.updateProxiLoc();
			adapter = new CommentAdapter(getApplicationContext(),
					R.layout.comment_row, commentList.getProxiLocList());
			commentListView.setAdapter(adapter);
			commentList.setAdapter(adapter);
		}

	}
	
	private void openDialogLocation() {
		LayoutInflater li = LayoutInflater.from(context);
		View locationChange = li.inflate(R.layout.location_dialog, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
		alertDialogBuilder.setTitle("Change Location");
		alertDialogBuilder.setView(locationChange);

		final EditText editLat = (EditText) locationChange
				.findViewById(R.id.LatitudeChange);
		final EditText editLog = (EditText) locationChange
				.findViewById(R.id.longitudeChange);

		alertDialogBuilder.setCancelable(false);
		alertDialogBuilder.setNeutralButton("Change", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				String stringLat = editLat.getText().toString();
				String stringLong = editLog.getText().toString();
				if(!stringLat.isEmpty() && !stringLong.isEmpty()){
				double lat = Double.parseDouble(stringLat);
				double log = Double.parseDouble(stringLong);

					modifiedLocation[0] = log;
					modifiedLocation[1] = lat;
					Log.e("Change Latitude", ""+modifiedLocation[0] + "-" + modifiedLocation[1]);}
				else
					Toast.makeText(getApplicationContext(), "error",
							Toast.LENGTH_SHORT).show();	

			}
		});

		alertDialogBuilder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();

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
			//THIS BEGIN//
			//EDIT COMMENT CAN BE OPTIMIZED AND BY DE-SERIALIZING THE EXTRA BELOW//
			//AND USING IT THROUGHOUT FROM ITS ACCESSOR METHODS//
			Commentor topLevel = commentList.getComment(info.position);
			Intent intent = new Intent(GeoCommentActivity.this,
					EditCommentActivity.class);
			intent.putExtra("comment", new Gson().toJson(topLevel));

			String user_name = ((TextView) info.targetView
					.findViewById(R.id.tvtitle)).getText().toString();
			if (user_name.equals(user.getUserName())) {
				startActivityForResult(intent, Resource.COMMENT_EDITED);
			} else {
				Toast.makeText(getApplicationContext(),
						"You don't have permission to edit this comment!",
						Toast.LENGTH_LONG).show();
			}
			// startActivity(intent);
			break;
		case R.id.viewProfile:
			int i = info.position;
			Commentor comment = commentList.getComment(i);

			viewProfile(comment);
		default:
			return super.onContextItemSelected(item);
		}
		return false;
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

	private void viewProfile(Commentor comment) {
		Intent intent = new Intent(GeoCommentActivity.this, ProfileActivity.class);
		Bundle bundle = new Bundle();
		
		bundle.putParcelable("user", comment.getUser());
		intent.putExtras(bundle);
		startActivity(intent);
	}

}

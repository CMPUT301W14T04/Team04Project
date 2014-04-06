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
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.ImageView;
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

	Gson gson;
	Internet internet;
	GPSLocation location;

	User user;
	TopLevelList commentList;
	LocationList locationHistory;
	UserPreference userPre;
	CommentAdapter adapter;

	Spinner sortList;
	ListView commentListView;
	ArrayList<Commentor> cacheList;

	private void constructGson() {
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Bitmap.class, new BitmapJsonConverter());
		gson = builder.create();
	}

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
			// for (int i=0;i<listFav.size();i++){
			// cacheList.add(listFav.get(i));
			// }
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

		// commentListView.setAdapter(adapter);
		// commentList.setAdapter(adapter);

		ElasticSearchOperations.searchALL(commentList, GeoCommentActivity.this);
		Toast.makeText(this, "" + commentList.getList().size(),
				Toast.LENGTH_SHORT).show();
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
		/*
		 * if (internet.isConnectedToInternet()==false){ cacheLoad();
		 * Toast.makeText(this,
		 * gson.toJson(cacheLoad()),Toast.LENGTH_SHORT).show(); }
		 */
		load(Resource.FAVOURITE_LOAD);
		// Toast.makeText(this,
		// gson.toJson(cacheList),Toast.LENGTH_SHORT).show();
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
	@Override
	protected void onStart() {
		super.onStart();
		double[] locations = { location.getLatitude(), location.getLongitude() };
		// get a json string and check if the null or not
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

	@Override
	protected void onPause() {
		super.onPause();
		save(Resource.GENERAL_INFO_SAVE);
		save(Resource.FAVOURITE_SAVE);
		if (internet.isConnectedToInternet() == true) {
			cacheSave();
		}
		commentList.clear();
		ElasticSearchOperations.searchALL(commentList, GeoCommentActivity.this);
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
		// Toast.makeText(this, gson.toJson(commentList.getList()),
		// Toast.LENGTH_SHORT).show();
		/*
		 * for(Commentor c: commentList.getList()){ for(Commentor c1:
		 * favourites.getFavouriteGeo()){ if(c.getID().equals(c1.getID())){
		 * c.setFavourite(true); } } }
		 */
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
		case Resource.FAVOURITE_LOAD:
			try {
				// Toast.makeText(this, gson.toJson(commentList.getList()),
				// Toast.LENGTH_SHORT).show();
				fis = openFileInput(Resource.FAVOURITE_FILE);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						fis));
				String fav = in.readLine();
				Type Type = new TypeToken<ArrayList<TopLevel>>() {
				}.getType();
				List<Commentor> listFav = gson.fromJson(fav, Type);
				// Toast.makeText(this, gson.toJson(listFav),
				// Toast.LENGTH_SHORT).show();
				/*
				 * if(internet.isConnectedToInternet()==false){ String
				 * favReply=in.readLine(); Type Reply = new
				 * TypeToken<ArrayList<Reply>>() { }.getType(); List<Commentor>
				 * favReplies=gson.fromJson(favReply, Reply); }
				 */
				/*
				 * for (Commentor c : listFav) { for (Commentor
				 * c1:commentList.getList()){ if (c1.getID().equals(c.getID())){
				 * c1.setFavourite(true); } } }
				 */
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
			favourites.updateGeo(commentList.getList());
			;
			try {
				// TopLevelList replies=new TopLevelList();
				fos = openFileOutput(Resource.FAVOURITE_FILE,
						Context.MODE_PRIVATE);

				String fav = gson.toJson(favourites.returnFav()) + "\n";
				// Toast.makeText(this, fav, Toast.LENGTH_SHORT).show();
				fos.write(fav.getBytes());
				/*
				 * for(Commentor c:commentList.getFavList()){
				 * ElasticSearchOperations.searchReplies(replies,
				 * GeoCommentActivity.this, c.getID()); }
				 */
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
		startActivityForResult(intent, Resource.COMMENT_EDITED);
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
			// cacheSave();
			// cacheLoad();
		} else if (parent.getItemAtPosition(pos).equals("Favourites")) {
			favourites.updateGeo(commentList.getList());
			adapter = new CommentAdapter(getApplicationContext(),
					R.layout.comment_row, favourites.returnFav());
			commentListView.setAdapter(adapter);
			commentList.setAdapter(adapter);

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
			//THIS BEGIN//
			//EDIT COMMENT CAN BE OPTIMIZED AND BY DE-SERIALIZING THE EXTRA BELOW//
			//AND USING IT THROUGHOUT FROM ITS ACCESSOR METHODS//
			Commentor topLevel = commentList.getComment(info.position);
			Intent intent = new Intent(GeoCommentActivity.this,
					EditCommentActivity.class);
			intent.putExtra("comment", new Gson().toJson(topLevel));
			// THIS END//

			// COMMENTED CODE BELOW CAN BE DELETED BEGIN //
			//Log.d("comment", new Gson().toJson(topLevel));
			/*String text = ((TextView) info.targetView
					.findViewById(R.id.comment)).getText().toString();
			String id = ((TextView) info.targetView
					.findViewById(R.id.commentId)).getText().toString();
			String user_name = ((TextView) info.targetView
					.findViewById(R.id.tvtitle)).getText().toString();
			int likes = Integer.parseInt(((TextView) info.targetView
					.findViewById(R.id.likes_button)).getText().toString()
					.split(": ")[1]);
			Bitmap photo = ((BitmapDrawable) ((ImageView) info.targetView
					.findViewById(R.id.pic_image_view)).getDrawable())
					.getBitmap();
			Intent intent = new Intent(GeoCommentActivity.this,
					EditCommentActivity.class);
			intent.putExtra("commentId", id);
			intent.putExtra("text", text);
			intent.putExtra("likes", likes);
			Bundle bundle = new Bundle();
			bundle.putParcelable(Resource.USER_INFO, user);
			bundle.putParcelable(Resource.USER_LOCATION_HISTORY,
					locationHistory);
			bundle.putParcelable("photo", photo);
			bundle.putInt(Resource.TOP_LEVEL_COMMENT, Resource.TYPE_TOP_LEVEL);
			intent.putExtras(bundle);*/
			// COMMENTED CODE BELOW CAN BE DELETED ENDS//

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
		case R.id.viewProfile:
			int i = info.position;
			List<Commentor> list = commentList.getList();
			String userID = list.get(i).getUserID();
			// Log.e("userID", userID);
			// Toast.makeText(getApplicationContext(), userID,
			// Toast.LENGTH_SHORT).show();
			viewProfile(userID);
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

	private void viewProfile(String aUser) {
		Toast.makeText(getApplicationContext(), "LOL", Toast.LENGTH_SHORT)
		.show();
	}

}

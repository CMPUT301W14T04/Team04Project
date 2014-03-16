package com.example.team04project;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.team04project.model.Author;
import com.example.team04project.model.Comments;
import com.example.team04project.model.Reader;
import com.example.team04project.model.TopLevel;

public class MainActivity extends Activity implements OnItemClickListener {

	public static final String SEND_DATA = "send";
	public static final int REQUEST_DATA = 1;

	// essential variables for start the APP
	GPSLocation userLocation;
	Author author;
	Reader reader;
	TopLevel comment;
	Internet internet;

	// data that is store to late be display in the Listview.
	ArrayList<Comments> commentList;

	// Views in shown in the app
	ListView commentListView;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		internet = new Internet(this);
		// this look for the location of the user
		userLocation = new GPSLocation(MainActivity.this);

		commentList = new ArrayList<Comments>();
		commentListView = (ListView) findViewById(R.id.commentListView);
		
		//testing parceaable
		author = new Author(userLocation.getLocation(), "guillermo");
		comment = new TopLevel("haola", author, null, "tittle");
		commentList.add(comment);

		/*
		 * this set the listview with the content on commentList (arraylist) and
		 * set every element clickable to get more datials .
		 */
		commentListView.setAdapter(new ArrayAdapter<Comments>(this,
				android.R.layout.simple_list_item_1, commentList));

		commentListView.setOnItemClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main_action, menu);
		return true;
	}

	/**
	 * this handle al the events in the action bar, that is buttons and menus.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.creat_comment:
			creatNewComment();
			return true;
		case R.id.setting:
			openSetting();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}
	
	/**
	 * testing data it should be remote before realease.
	 * @param view
	 */

	public void testB(View view) {
		boolean isOn = internet.isConnectedToInternet();
		Toast.makeText(this, "lol", Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * Start a new activity that create a new TopLevel Comment.
	 * 
	 * TODO: pass user info to activity..
	 */
	public void creatNewComment() {
		Intent intent = new Intent(MainActivity.this,
				CreateCommentActivity.class);
		startActivity(intent);

	}

	/**
	 * Testing setting activity, it should be modified for the actual function.
	 */
	public void openSetting() {
		Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
		startActivity(intent);

	}

	/**
	 * this handle click on the listView and start a new activity that show more 
	 * details about that top level comment, that is it starts a new activity that 
	 * shows the tittle, username, date and location, and all the replies in the top level
	 * comment.
	 */
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long id) {
		
		TopLevel topLevel = (TopLevel) commentList.get(position);
		Intent intent= new Intent(getApplicationContext(),ShowCommentActivity.class);
		intent.putExtra("testing", topLevel);
		startActivity(intent);
		

	}

}

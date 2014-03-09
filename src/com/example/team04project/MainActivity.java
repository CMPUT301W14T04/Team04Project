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

import com.google.gson.Gson;

public class MainActivity extends Activity implements OnItemClickListener
{

	public static final String CREATE_NEW_COMMENT = "new comment";
	public static final int SUCCESS_CREATE_COMMENT = 1;

	// essential variables for start the APP
	GPSLocation userLocation;
	Internet internet;
	Author author;

	// data that is store to late be display in the Listview.
	ArrayList<TopLevel> commentList;

	// Views in shown in the app
	ListView commentListView;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		internet = new Internet(this);
		userLocation = new GPSLocation(MainActivity.this);

		commentList = new ArrayList<TopLevel>();
		userLocation = new GPSLocation(MainActivity.this);

		commentListView = (ListView) findViewById(R.id.commentListView);
		commentListView.setAdapter(new ArrayAdapter(this,
				android.R.layout.simple_list_item_1, commentList));

		commentListView.setOnItemClickListener(this);
		

	}

	protected void onResume()
	{

		super.onResume();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * this handle al the events in the action bar, that is buttons and menus.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{

		switch (item.getItemId())
		{
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
	 * 
	 * @param view
	 */

	public void testB(View view)
	{

	}

	/**
	 * Start a new activity that create a new TopLevel Comment.
	 * 
	 * TODO: pass user info to activity..
	 */
	public void creatNewComment()
	{

		Intent intent = new Intent(MainActivity.this,
				CreateCommentView.class);
		intent.putExtra(CREATE_NEW_COMMENT, author);
		intent.putExtra("TYPE", "TOP_LEVEL");
		startActivityForResult(intent, SUCCESS_CREATE_COMMENT);

	}

	/**
	 * Testing setting activity, it should be modified for the actual function.
	 */
	public void openSetting()
	{

		/**
		 * this is settings 
		 */
		Intent intent = new Intent(MainActivity.this, OptionsView.class);
		startActivity(intent);

	}

	/**
	 * this handle click on the listView and start a new activity that show more
	 * details about that top level comment, that is it starts a new activity
	 * that shows the tittle, username, date and location, and all the replies
	 * in the top level comment.
	 */
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long id)
	{

		// todo: change ShowCommentActivity ...
		
		
//		TopLevel topLevel = commentList.get(position);
//		Intent intent = new Intent(getApplicationContext(),
//				ShowCommentActivity.class);
//		intent.putExtra("testing", topLevel);
//		startActivity(intent);

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{

		super.onActivityResult(requestCode, resultCode, data);

		switch (resultCode)
		{
			case RESULT_OK:
				Gson gson = new Gson();
				String json=data.getExtras().getString("Class");
				TopLevel newComment = gson.fromJson(json, TopLevel.class);
				commentList.add((TopLevel) newComment);
				commentListView.invalidateViews();

				break;

			default:

				Toast.makeText(this, "someting were wrong", Toast.LENGTH_SHORT)
						.show();
				break;
		}

	}

}
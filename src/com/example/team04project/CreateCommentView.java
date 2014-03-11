package com.example.team04project;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

public class CreateCommentView extends Activity {
	protected static final int CAMERA_REQUEST = 0;
	protected static final int GALLARY_REQUEST = 0;
	Author author = new Author();
	private static String saveFile = "username.sav";
	/*
Gets the user name from internal storage
then retrieves it and sets the username for the username class
	 */
	public void loadUser(){
		try{
			FileInputStream fis;
			fis = openFileInput(saveFile);
			BufferedReader in = new BufferedReader(new InputStreamReader(fis));
			String authorInfo = in.readLine();
			String split[] = authorInfo.split(",");
			author.setUserName(split[0]);
			fis.close();
		}catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void postComment(View view){
		//Gets the date,user and other things from their classes and changes them to strings and makes a new comment
		Intent i =getIntent();
		String type=i.getExtras().getString("TYPE");
		String uniqueKey = UUID.randomUUID().toString();
		EditText text = (EditText) findViewById(R.id.comment);
		String commentText = text.getText().toString();
		String currDate = Dates.getDate();
		loadUser();


		/*
		 * If no user name is set then the program tells the user
		 * that they need a user name to make a comment otherwise
		 * it will make the comment then turn it into a json string and
		 * send it through an intent back to the mainactivity.java
		 */
		if (author.getUserName()==null){
			Toast.makeText(CreateCommentView.this, "You need to make a username to post comments", Toast.LENGTH_SHORT).show();
			finish();
		}
		else{
			String theUser=author.getUserName();
			if(type.equals("TOP_LEVEL")){
				TopLevel newComment = new TopLevel(commentText, theUser, null, currDate);
				Gson gson = new Gson();
				Intent intent = new Intent();
				String json= gson.toJson(newComment);
				intent.putExtra("Class", json);
				setResult(RESULT_OK, intent);
				finish();
			}
			//CommentList.addComment(newComment);//Problems here

			/*Gson gson = new Gson();
			Intent intent = new Intent();
			String json= gson.toJson(newComment);
			intent.putExtra("Class", json);
			setResult(RESULT_OK, intent);
			finish();
			 */		}
	}



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_comment_view);

		Intent intent = this.getIntent();
		Author author = intent.getParcelableExtra(MainActivity.CREATE_NEW_COMMENT);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_comment_view, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_new_picture:
			dialog();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void dialog() {
		AlertDialog.Builder builder = new Builder(CreateCommentView.this);
		builder.setTitle("Attach a picture from:");
		builder.setPositiveButton("Camera", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
				startActivityForResult(cameraIntent, CAMERA_REQUEST); 
			}
		});
		
		builder.setNeutralButton("Gallary", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(intent, GALLARY_REQUEST);
			}
		});

		builder.create().show();
	}

}


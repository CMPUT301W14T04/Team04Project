package com.example.team04project;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class OptionsView extends Activity {
	Author author = new Author();

	/*
Gets the user name from internal storage
then retrieves it and sets the username for the username class
	 */
	private static String saveFile = "username.sav";
	public void loadUser(){
		try{
			FileInputStream fis;
			fis = openFileInput(saveFile);
			BufferedReader in = new BufferedReader(new InputStreamReader(fis));
			author.setUserName(in.readLine());
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



	/*
	 * Saves the username to internal storage so it can be retrieved for future uses
	 */
	public void saveUser(String username){
		FileOutputStream fos;

		try {
			fos = openFileOutput(saveFile, Context.MODE_PRIVATE);
			String string = username+"\n";
			fos.write(string.getBytes());
			fos.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/*
	 * Button that takes the Users new user name and sets it
	 */
	public void setUsername(View view){
		EditText text = (EditText) findViewById(R.id.editText1);
		String username = text.getText().toString();
		author.setUserName(username);
		saveUser(username);
		Toast.makeText(OptionsView.this, "New Username made", Toast.LENGTH_SHORT).show();
	}

	/*
	 * pressing this button makes the edit text visible for the user to
	 * make a new user name.
	 */

	public void changeUser(View view){
		View edit = findViewById(R.id.editText1);
		edit.setVisibility(View.VISIBLE);
		View button =findViewById(R.id.button1);
		button.setVisibility(View.VISIBLE);
	}



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options_view);

		loadUser();
		String username = author.getUserName();
		TextView message = (TextView)findViewById(R.id.textView1);
		Button button = (Button)findViewById(R.id.button2);
		if (username==null){
			message.setText("You have not set a username yet");
			button.setText("Create");
		}
		else{
			message.setText("Your username is\n"+"--"+username);
			button.setText("Change");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.options_view, menu);
		return true;
	}

}


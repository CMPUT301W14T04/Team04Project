package com.example.geocomment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class OptionActivity extends Activity {

	String username;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_option);
		// Show the Up button in the action bar.
		setupActionBar();
		
		Intent intent = getIntent();
		username = intent.getStringExtra("Uusername");
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.option, menu);
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
		}
		return super.onOptionsItemSelected(item);
	}

	/*
	 * Button that takes the Users new user name and sets it
	 */
	public void setUsername(View view){
		EditText text = (EditText) findViewById(R.id.editText1);
		String username = text.getText().toString();
		this.username= username;
		Toast.makeText(OptionActivity.this, "New Username made", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent();
		intent.putExtra("setUsername", username);
		setResult(90,intent);
		finish();
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
	
}

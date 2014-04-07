package com.example.geocomment;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.geocomment.model.UserProfile;

public class ProfileActivity extends Activity {
	
	UserProfile profile;
	
	ImageView userPic;
	TextView username;
	TextView quote;
	TextView bio;
	ListView contact;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		userPic = (ImageView) findViewById(R.id.profilePic);
		username = (TextView) findViewById(R.id.profileUsername);
		quote = (TextView) findViewById(R.id.profileQuote);
		bio = (TextView) findViewById(R.id.profileBio);
		contact = (ListView) findViewById(R.id.profileContact);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

}

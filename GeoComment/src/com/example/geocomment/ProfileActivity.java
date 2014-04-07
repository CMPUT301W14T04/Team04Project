package com.example.geocomment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geocomment.model.User;
import com.example.geocomment.model.UserProfile;

public class ProfileActivity extends Activity {
	
	UserProfile profile;
	boolean isOtherUser = false;
	User user;
	
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
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		profile = (UserProfile) bundle.getParcelable("profile");
		user = (User) bundle.getParcelable("user");
		isOtherUser = bundle.getBoolean("online?");
		
		Toast.makeText(this, "" + isOtherUser, Toast.LENGTH_SHORT).show();
		if(!isOtherUser){
			username.setText(profile.getUsername());}
		else 
		{
			username.setText(user.getUserName());
			
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}
	
	private  void ownProfile()
	{
	}
	

}

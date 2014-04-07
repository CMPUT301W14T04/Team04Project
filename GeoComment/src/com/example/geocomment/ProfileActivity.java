package com.example.geocomment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.geocomment.elasticsearch.ElasticSearchOpertationUser;
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
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
		
//		Toast.makeText(this, "" + isOtherUser, Toast.LENGTH_SHORT).show();
		if(!isOtherUser){
			username.setText(profile.getUsername());}
		else 
		{
			UserProfile guest = ElasticSearchOpertationUser.searchProfile(this, user.getID());
			username.setText(user.getUserName());
			
		}
		if(profile!=null){
		if(profile.getQuote()!=null)
			quote.setText("\""+profile.getQuote()+ "\"");
		else
			quote.setText("NO QUOTE YET");
		if(profile.getPic()!=null)
			userPic.setImageBitmap(profile.getPic());
		if(profile.getBiography()!=null)
			bio.setText(profile.getBiography());
		else
			bio.setText("No Bio yet");
//		contact.setAdapter(new ArrayAdapter(this,
//				android.R.layout.simple_list_item_1, profile.getSocial()));
		}
		
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}
	

}

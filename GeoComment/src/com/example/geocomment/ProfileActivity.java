/*
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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.geocomment.model.User;
import com.example.geocomment.model.UserProfile;

/**
 * This class contains all the information needed
 * to create a profile for a user
 * 
 * @author nadine
 *
 */
public class ProfileActivity extends Activity {
	
	UserProfile profile;
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
		
		if(profile==null)
		{
			username.setText(user.getUserName());
		}
		else
		{
			username.setText(profile.getUsername());
			if(!profile.getBiography().isEmpty())
				bio.setText(profile.getBiography());
			else
				bio.setText("Not bio added yets");
			if(profile.getPic()!=null)
				userPic.setImageBitmap(profile.getPic());
			if(!profile.getQuote().isEmpty())
				quote.setText("\""+profile.getQuote()+"\"");
			else
				quote.setText("Not Quote added Yet");
		
			contact.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1,profile.getSocial()));
		}
		
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}
	

}

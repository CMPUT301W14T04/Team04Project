package com.example.geocomment.test;

import java.util.ArrayList;

import com.example.geocomment.OptionActivity;
import com.example.geocomment.model.User;
import com.example.geocomment.model.UserProfile;

import android.test.ActivityInstrumentationTestCase2;

public class OptionsActivityTest extends
		ActivityInstrumentationTestCase2<OptionActivity> {
	User user = new User(null, "elbohtim", "1");

	public OptionsActivityTest() {
		super(OptionActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		
	}

	/*
	 * Test that the user name
	 * is set and test that set ID
	 * is returned
	 */
	public void testUser(){
		User user= new User();
		user.setUserName("elbohtim");
		assertEquals("elbohtim",user.getUserName());
		user.setID("010101");
		assertEquals("010101",user.getID());
	}
	/*
	 * This is testing the user profiles making
	 * sure that they return the correct information
	 */
	public void testUserProfile(){
		UserProfile profile= new UserProfile(user);
		ArrayList<String> social = new ArrayList<String>();
		social.add("facebook.com");
		social.add("Twitter.com");
		social.add("myspace.com");
		profile.setBiography("This is a biography");
		profile.setQuote("To program or not to program");
		profile.setSocial(social);
		
		assertEquals("elbohtim",profile.getUsername());
		assertEquals(social,profile.getSocial());
		assertEquals("This is a biography",profile.getBiography());
		assertEquals("To program or not to program",profile.getQuote());
	}
}

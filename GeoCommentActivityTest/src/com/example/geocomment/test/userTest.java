package com.example.geocomment.test;

import com.example.geocomment.GeoCommentActivity;
import com.example.geocomment.OptionActivity;
import com.example.geocomment.model.User;

import android.test.ActivityInstrumentationTestCase2;

public class userTest extends ActivityInstrumentationTestCase2<OptionActivity> {
	


	public userTest() {
		super(OptionActivity.class);
		// TODO Auto-generated constructor stub
	}
	

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	public void testUser(){
		User user= new User();
		user.setUserName("elbohtim");
		assertEquals("elbohtim",user.getUserName());
	}

}

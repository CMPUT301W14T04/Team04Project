package com.example.geocomment.test;

import com.example.geocomment.OptionActivity;
import com.example.geocomment.model.User;

import android.test.ActivityInstrumentationTestCase2;

public class OptionsActivityTest extends
		ActivityInstrumentationTestCase2<OptionActivity> {

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
}

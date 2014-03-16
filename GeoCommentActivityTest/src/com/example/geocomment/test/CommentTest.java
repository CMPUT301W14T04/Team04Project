package com.example.geocomment.test;

import java.util.Calendar;


import com.example.geocomment.GeoCommentActivity;
import com.example.geocomment.model.TopLevel;
import com.example.geocomment.model.User;
import com.example.geocomment.util.GPSLocation;
import com.example.geocomment.util.Internet;

import android.app.Activity;
import android.location.Location;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;



public class CommentTest extends ActivityInstrumentationTestCase2<GeoCommentActivity>
{
	Calendar timeStamp = Calendar.getInstance();
	Calendar timeStamp1 = Calendar.getInstance();
	Activity activity;

	TopLevel comment;
	TopLevel comment1;
	TopLevel comment2;
	User user = new User(null, "elbohtim", "1");
	
	public CommentTest() {
		super(GeoCommentActivity.class);
	
		comment=new TopLevel(null, null, null, "hi", null, null);
		comment1= new TopLevel(user,null,null,null,null, null);
		comment2= new TopLevel(null,timeStamp,null,null,null,null);
	}	
	protected void setUp() throws Exception {
		super.setUp();
		
		activity = getActivity();

	}
	/*
	 * Testing the comment class
	 */
	public void testComment() {

		assertEquals("hi", comment.getTextComment());
		assertEquals("elbohtim",comment1.getUserName());
		assertEquals(timeStamp1,comment2.getDate());
	}
	
	
	/*
	 * Testing whether or not an internet connection exists
	 */
	public void testInternet(){
		Internet internet = new Internet(activity);
		assertEquals(true, internet.isConnectedToInternet() );
	}
	 
	public void testGPSLocation(){
		GPSLocation location = new GPSLocation(activity);
		assertEquals(-122, (int)location.getLongitude());//numbers are too specific to get to test double
		assertEquals(37,(int)location.getLatitude());//numbers are too specific to get to test double
		//Latitude 37.422006
		//Longitutde -122.084095
	}
	
	
}

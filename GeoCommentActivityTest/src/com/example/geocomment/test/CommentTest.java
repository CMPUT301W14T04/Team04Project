package com.example.geocomment.test;

import java.util.Calendar;


import com.example.geocomment.GeoCommentActivity;
import com.example.geocomment.model.TopLevel;
import com.example.geocomment.model.User;
import com.example.geocomment.util.Internet;

import android.location.Location;

import android.test.ActivityInstrumentationTestCase2;



public class CommentTest extends ActivityInstrumentationTestCase2<GeoCommentActivity>
{
	Calendar timeStamp = Calendar.getInstance();
	Calendar timeStamp1 = Calendar.getInstance();
	Location location;
	Double location1;
	TopLevel comment;
	TopLevel comment1;
	TopLevel comment2;
	User user = new User(null, "elbohtim", "1");
	Internet internet = new Internet(getActivityContext);
	public CommentTest() {
		super(GeoCommentActivity.class);
	
		comment=new TopLevel(null, null, null, "hi", null, null);
		comment1= new TopLevel(user,null,null,null,null, null);
		comment2= new TopLevel(null,timeStamp,null,null,null,null);
	}	
	
	
	public void testComment() {

		assertEquals("hi", comment.getTextComment());
		assertEquals("elbohtim",comment1.getUserName());
		assertEquals(timeStamp1,comment2.getDate());
	}
	
	public void testInternet(){
		assertEquals(false, internet.isConnectedToInternet() );
	}
}

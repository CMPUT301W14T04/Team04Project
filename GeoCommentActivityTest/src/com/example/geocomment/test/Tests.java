package com.example.geocomment.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import com.example.geocomment.GeoCommentActivity;
import com.example.geocomment.model.Commentor;
import com.example.geocomment.model.TopLevel;
import com.example.geocomment.model.TopLevelList;
import com.example.geocomment.model.User;
import com.example.geocomment.util.GPSLocation;
import com.example.geocomment.util.Internet;
import com.example.geocomment.model.favourites;

import android.app.Activity;
import android.location.Location;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ArrayAdapter;
import android.widget.EditText;



public class Tests extends ActivityInstrumentationTestCase2<GeoCommentActivity>
{
	Calendar timeStamp = Calendar.getInstance();
	Calendar timeStamp1 = Calendar.getInstance();
	Activity activity;

	TopLevel comment;
	TopLevel comment1;
	TopLevel comment2;
	TopLevel comment3;
	TopLevel comment4;
	TopLevel comment5;
	TopLevel comment6;
	TopLevelList commentList= new TopLevelList();
	User user = new User(null, "elbohtim", "1");
	
	List<Commentor> list = new ArrayList <Commentor>() ;
	
	public Tests() {
		super(GeoCommentActivity.class);
	
		comment=new TopLevel(null, null, null, "hi", null, null);
		comment1= new TopLevel(user,null,null,null,null, null);
		comment2= new TopLevel(null,timeStamp,null,null,null,null);
		comment3= new TopLevel(user, timeStamp, null, "hi", null, null);
		comment4= new TopLevel(user,timeStamp,null,"chuck",null, null);
		comment5= new TopLevel(user,timeStamp,null,"duck",null,null);
		comment6= new TopLevel(user,timeStamp,null,"truck",null,null);
		
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
		assertEquals(-113, (int)location.getLongitude());//numbers are too specific to get to test double
		assertEquals(53,(int)location.getLatitude());//numbers are too specific to get to test double
		//Latitude 37.422006
		//Longitutde -122.084095
		//
		//53.5244° N, 113.5244° W
	}
		
	//TEST USER MODEL/ID
		//TWEET LIST MODEL
		//ALL PUBLIC METHODS
	public void testTopLevelList(){
		//this.adapter.notifyDataSetChanged(); THIS LINE IN TOPLEVEL CAUSES
		//TEST TO FAIL CREATES A NULL POINTER EXCEPTION
		commentList.AddTopLevel(comment3,1);
		commentList.AddTopLevel(comment4,1);
		commentList.AddTopLevel(comment5,1);
		commentList.AddTopLevel(comment6,1);
		list=commentList.getList();
		assertEquals(list.get(3),comment3);
		assertEquals(list.get(2),comment4);
		assertEquals(list.get(1),comment5);
		assertEquals(list.get(0),comment6);
		commentList.clear();
		//this.adapter.notifyDataSetChanged(); THIS LINE IN TOPLEVEL CAUSES
		//TEST TO FAIL CREATES A NULL POINTER EXCEPTION
		list=commentList.getList();
		assertEquals(0,list.size());
		}
	public void testFavourites(){
		comment1.setFavourite(true);//CHECK FOR THIS IN THE TEST
		assertEquals(true,comment1.isFavourite());
		ArrayList<Commentor> test = new ArrayList<Commentor>();
		test.add(comment1);
		test.add(comment2);
		test.add(comment3);
		test.add(comment4);
		test.add(comment5);
		test.add(comment6);
		for(int i=5;i>=3;i++){
			test.get(i).setFavourite(true);
		}
		favourites.updateGeo(test);
		assertEquals(favourites.getFavouriteGeo().get(0),comment);
		assertEquals(favourites.getFavouriteGeo().get(1),comment);
		assertEquals(favourites.getFavouriteGeo().get(2),comment);
	}
	
	
	
}

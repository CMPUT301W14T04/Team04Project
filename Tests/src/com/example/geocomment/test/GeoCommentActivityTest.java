package com.example.geocomment.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import com.example.geocomment.GeoCommentActivity;
import com.example.geocomment.elasticsearch.ElasticSearchOperations;
import com.example.geocomment.model.Commentor;
import com.example.geocomment.model.TopLevel;
import com.example.geocomment.model.TopLevelList;
import com.example.geocomment.model.User;
import com.example.geocomment.model.favourites;
import com.example.geocomment.util.GPSLocation;
import com.example.geocomment.util.Internet;


public class GeoCommentActivityTest extends ActivityInstrumentationTestCase2<GeoCommentActivity>
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
	public GeoCommentActivityTest(){
		super(GeoCommentActivity.class);
	}
	protected void setUp() throws Exception{
		super.setUp();
		activity = getActivity();
		comment=new TopLevel(user, timeStamp, null, "hi", null, "0", 0);
		comment1= new TopLevel(user,timeStamp,null,"test",null, "1", 0);
		comment2= new TopLevel(user,timeStamp,null,"another",null,"2", 0);
		comment3= new TopLevel(user, timeStamp, null, "hi", null, "3", 0);
		comment4= new TopLevel(user,timeStamp,null,"chuck",null, "4", 0);
		comment5= new TopLevel(user,timeStamp,null,"duck",null,"5", 0);
		comment6= new TopLevel(user,timeStamp,null,"truck",null,"6", 0);
	}
	
	/*
	 * Checks if there is an internet connection present
	 */
	
	public void testInternet(){
		Internet internet = new Internet(activity);
		assertEquals(true, internet.isConnectedToInternet() );
	}
	
	/*
	 * Tests that the gpslocation is the correct gps location
	 */
	 
	public void testGPSLocation(){
		GPSLocation location = new GPSLocation(activity);
		assertEquals(-113, (int)location.getLongitude());//numbers are too specific to get to test double
		assertEquals(53,(int)location.getLatitude());//numbers are too specific to get to test double
	}
	
	/*
	 * Tests that the comments are returning the correct texts
	 */
	public void testComments(){
		
		assertEquals("hi", comment.getTextComment());
		assertEquals("elbohtim",comment.getUserName());
		assertEquals(timeStamp1,comment.getDate());
	}
	
	/*
	 * Test that the toplevellist model is taking in new comments and
	 * putting them at the beginning of the list
	 * and then clears the list and checks that it is empty
	 */
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
	
	
	/*
	 * Tests that a comment has been set to favourite
	 * then sets more comments to be favourited then
	 * returns a list of favourite comments
	 */
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
		comment2.setFavourite(true);
		comment3.setFavourite(true);
		favourites.updateGeo(test);
		assertEquals(favourites.getFavouriteGeo().get(0),comment1);
		assertEquals(favourites.getFavouriteGeo().get(1),comment2);
		assertEquals(favourites.getFavouriteGeo().get(2),comment3);
		assertEquals(favourites.returnFav().get(0),comment1);
		assertEquals(favourites.returnFav().get(1),comment2);
		assertEquals(favourites.returnFav().get(2),comment3);
	}
	
	
	/*
	 * Tests that the comment text can be changed
	 */
	public void testEditComment(){
		comment1.setTextComment("This has been changed");
		assertEquals("This has been changed",comment1.getTextComment());
		
	}
	
	
}
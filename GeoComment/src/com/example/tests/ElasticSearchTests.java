/*package com.example.tests;

import java.util.Calendar;

import android.location.Location;
import android.test.ActivityInstrumentationTestCase2;

import com.example.geocomment.GeoCommentActivity;
import com.example.geocomment.model.TopLevel;
import com.example.geocomment.model.TopLevelList;
import com.example.geocomment.model.User;
import com.example.geocomment.elasticsearch.*;

public class ElasticSearchTests extends ActivityInstrumentationTestCase2<GeoCommentActivity> {
	
	Calendar timeStamp = Calendar.getInstance();
	Location location;
	TopLevel comment;
	User user;
	TopLevelList list;
	
	public ElasticSearchTests() {
		
		super(GeoCommentActivity.class);
		user = new User(null, "nyushko", "1");
		comment = new TopLevel(null, null, null, "testing elastic search", null, null);
		
	}
	
	public void testElasticSearch() {
		//first, post the comment to the server
		//ElasticSearchOperations.pushComment(comment);
		try {
			String query = 	"{\"query\": {\"testing elastic search\": {}}}";
			//ElasticSearchOperations.searchALL(model, activity);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Comment not posted to server.");
		};
	}}
		
		
		//fail("Comment not posted to server.");
		//try {
			//String query = 	"{\"query\": {\"testing elastic search\": {}}}";
			//ElasticSearchOperations.searchALL(query, );
		//} catch (Exception e) {
			//e.printStackTrace();
			//fail("Comment not posted to server.");
*/
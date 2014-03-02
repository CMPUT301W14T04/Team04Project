package com.example.team04project;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

public class MainActivity extends Activity {
	ArrayList<Comments> browseComment = new ArrayList<Comments>();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		getMenuInflater().inflate(R.menu.create_menu, menu);
		getMenuInflater().inflate(R.menu.options_menu, menu);
		return true;
	}
	/*
	 Selects on option that will take you to the appropriate view
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
        	case R.id.createComment://CREATE A MENU XML
        		Intent intent = new Intent(MainActivity.this, CreateCommentView.class);
        		startActivityForResult(intent,0);
        		break;
        	case R.id.options://CREATE A MENU XML
        		Intent intent1 = new Intent(this,OptionsView.class);
        		startActivity(intent1);
        		break;
        	default:
        		return super.onOptionsItemSelected(item);
        	}
			return true;
		}
	@Override
	/*
	 Gets a json string from the CreateCommentView.class and turns it back to a class of comments then the comment class is 
	 added to the ArrayList of Comments and properly formatted for viewing.
	 */
	public void onActivityResult(int requestCode,int resultCode, Intent data){	
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK){
			Gson gson = new Gson();
			String json=data.getExtras().getString("Class");
			Comments newComment = gson.fromJson(json, Comments.class);
			browseComment.add(0, newComment);
			
			
			//Dynamically display the comments
			LinearLayout ll = (LinearLayout)findViewById(R.id.browseComment);
			TextView comments= new TextView(this);
			comments.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
			comments.setText("--------------------\n"+newComment.getComment()+"\n"+newComment.getCommentUser()+" "+newComment.getCommentDate()+"\n");
			ll.addView(comments);
			ElasticSearch.pushComment(newComment);
		}
	}
	
}

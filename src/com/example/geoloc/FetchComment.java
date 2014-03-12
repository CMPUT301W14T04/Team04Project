package com.example.geoloc;

import java.util.ArrayList;
import java.util.List;

import android.widget.ArrayAdapter;



public class FetchComment{

	private List<CommentMetaData> list;
	private ArrayAdapter<CommentMetaData> adapter;
	
	public FetchComment(){
		this.list = new ArrayList<CommentMetaData>();
	}
	
	
}

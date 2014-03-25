package com.example.geocomment.model;

import com.example.geocomment.GeoCommentActivity;


public class CommentController
{
	
	public static final int MAX_BITMAP_DIMENSIONS = 50;
	
	private TopLevelList model;
	private GeoCommentActivity activity;
	
	
	public CommentController(TopLevelList model, GeoCommentActivity activity)
	{
		this.model = model;
		this.activity= activity;
	}

}

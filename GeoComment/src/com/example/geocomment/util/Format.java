package com.example.geocomment.util;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@SuppressLint("SimpleDateFormat")
public class Format {
	
	private final static int START_COMMENT = 0;
	private final static int END_COMMENT = 140;
	
	/*
	 * This function takes a Calendar timestamp
	 * and uses the SimpleDatFormat class
	 * to convert the timeStamp into a string
	 */
	public static String dateFormat(Calendar timeStamp)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy-HH:mm");
		return sdf.format(timeStamp.getTime());
	}
	
	public static String subTextComment(String textComment)
	{
		String previewComment = textComment.substring(START_COMMENT, END_COMMENT) + "..."; 
		return previewComment; 
	}

}

package com.example.team04project;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Dates
{
	//Gets the current date in the format of Month day, Year-Hour:Minute
	static Calendar date = Calendar.getInstance();
	@SuppressLint("SimpleDateFormat")
	static
	SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy-HH:mm");
	static String strDate = sdf.format(date.getTime());
	//Returns a string of date
	public static String getDate(){
		return strDate;
	}
}

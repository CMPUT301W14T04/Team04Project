package com.example.team04project.model;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Dates
{
	//Gets the current date in the format of Month day, Year-Hour:Minute
	Calendar date = Calendar.getInstance();
	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy-HH:mm");
	String strDate = sdf.format(date.getTime());
	//Returns a string of date
	public String getDate(){
		return strDate;
	}
}

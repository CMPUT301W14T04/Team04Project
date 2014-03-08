package com.example.team04project;

import android.graphics.Bitmap;
import android.os.Parcel;



public class Reply extends Comments
{
	public Reply(String comment, String date,Bitmap aPicture, String user)
	{

		super(comment, date, aPicture, user, type);//Changes
		// TODO Auto-generated constructor stub
	}

	public void addReply(){
		
	}

	@Override
	public int describeContents()
	{

		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{

		// TODO Auto-generated method stub
		
	}
}

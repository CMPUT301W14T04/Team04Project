package com.example.team04project.model;

import android.graphics.Bitmap;
import android.os.Parcel;


public class Reply extends Comments 
{

	public Reply(String textComment, Author aUser,
			Bitmap aPicture) {
		super(textComment, aUser, aPicture);
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}
	
}

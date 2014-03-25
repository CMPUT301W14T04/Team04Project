package com.example.geocomment.model;

import java.util.Calendar;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.JsonElement;

public class Reply extends Comment {
	
	private String parentID;


	/**
	 * initialize all parameters of a reply
	 * @param aUser
	 * @param timeStamp
	 * @param aPicture
	 * @param textComment
	 * @param aLocation
	 */

	public Reply(User aUser, Calendar timeStamp, JsonElement aPicture,
			String textComment, double[] aLocation, String parentID)
	{

		super(aUser,timeStamp,aPicture,textComment, aLocation);
		this.parentID=parentID;
	}

	public Reply()
	{

		super();
		this.aPicture = null;
		this.textComment = null;
		this.aUser = null;
		this.timeStamp = null;
		this.parentID=null;
	}

	/**
	 * @param source
	 */
	private Reply(Parcel source)
	{

		Log.d("Parcealbe",
				"ParcelData(Parcel source): time to put back parcel data");
		textComment = source.readString();
		aUser = (User) source.readValue(getClass().getClassLoader());
		aPicture = (String) source.readValue(getClass().getClassLoader());
		timeStamp = (Calendar) source.readSerializable();
		parentID = source.readString();
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

		Log.e("parceable", "TopLevel" + flags);
		dest.writeString(textComment);
		dest.writeValue(aUser);
		dest.writeValue(aPicture);
		dest.writeSerializable(timeStamp);
		dest.writeString(parentID);
	}

	/**
	 * creates a new reply
	 */
	public static final Parcelable.Creator<Reply> CREATOR = new Creator<Reply>()
			{

		@Override
		public Reply[] newArray(int size)
		{

			// TODO Auto-generated method stub
			return new Reply[size];
		}

		@Override
		public Reply createFromParcel(Parcel source)
		{

			return new Reply(source);
		}
			};


	@Override
	public void setaPicture(String aPicture) {
		// TODO Auto-generated method stub
		
	}

}

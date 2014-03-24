package com.example.geocomment.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.google.gson.JsonElement;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class TopLevel extends Comment {

	private String ID;
	private List <Reply> replies;

	/**
	 * initialize all parameters of a top level comment
	 * @param aUser
	 * @param timeStamp
	 * @param aPicture
	 * @param textComment
	 * @param aLocation
	 * @param ID
	 */
	public TopLevel(User aUser, Calendar timeStamp, JsonElement aPicture,
			String textComment, double[] aLocation,String ID)
	{

		super(aUser,timeStamp,aPicture,textComment, aLocation);
		this.ID=ID;
		this.replies=null;
	}

	public TopLevel()
	{

		super();
		this.aPicture = null;
		this.textComment = null;
		this.aUser = null;
		this.timeStamp = null;
		this.replies=null;
	}

	/**
	 * @param source
	 */
	@SuppressWarnings("unchecked")
	private TopLevel(Parcel source)
	{

		Log.d("Parcealbe",
				"ParcelData(Parcel source): time to put back parcel data");
		textComment = source.readString();
		aUser = (User) source.readValue(getClass().getClassLoader());
		aPicture = (JsonElement) source.readValue(getClass().getClassLoader());
		timeStamp = (Calendar) source.readSerializable();
		ID = source.readString();
		replies = (ArrayList<Reply>) source.readArrayList(getClass().getClassLoader());
	}

	public String getID()
	{
		return ID;
	}

	/**
	 * @return A replies list.
	 * @return null if the list is empty.
	 */
	public List<Reply> getReplies()
	{
		if(this.replies.isEmpty())
			return null;
		return this.replies;
	}

	public void setReplies (List<Reply> replies)
	{
		this.replies= replies;
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
		dest.writeString(ID);
		dest.writeList(replies);
	}

	/**
	 * creates a new top level comment
	 */
	public static final Parcelable.Creator<TopLevel> CREATOR = new Creator<TopLevel>()
			{

		@Override
		public TopLevel[] newArray(int size)
		{

			// TODO Auto-generated method stub
			return new TopLevel[size];
		}

		@Override
		public TopLevel createFromParcel(Parcel source)
		{

			return new TopLevel(source);
		}
			};

}

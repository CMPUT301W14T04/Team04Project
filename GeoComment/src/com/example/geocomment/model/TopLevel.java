package com.example.geocomment.model;

import java.util.Calendar;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class TopLevel extends Comment {

	private String ID;

	/**
	 * initialize all parameters of a top level comment
	 * 
	 * @param aUser
	 * @param timeStamp
	 * @param aPicture
	 * @param textComment
	 * @param aLocation
	 * @param ID
*/
	public TopLevel(User aUser, Calendar timeStamp, Bitmap aPicture,
			String textComment, double[] aLocation,String ID)
	{

		super(aUser, timeStamp, aPicture, textComment, aLocation);
		this.ID = ID;
	}

	public TopLevel() {

		super();
		this.aPicture = null;
		this.textComment = null;
		this.aUser = null;
		this.timeStamp = null;
	}

	/**
	 * @param source
	 */
	private TopLevel(Parcel source) {

		Log.d("Parcealbe",
				"ParcelData(Parcel source): time to put back parcel data");
		textComment = source.readString();
		aUser = (User) source.readValue(getClass().getClassLoader());
		aPicture = (Bitmap) source.readValue(getClass().getClassLoader());
		timeStamp = (Calendar) source.readSerializable();
		ID = source.readString();
		aLocation= (double[]) source.readSerializable();
	}

	public String getID() {
		return ID;
	}

	@Override
	public int describeContents() {

		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

		Log.e("parceable", "TopLevel" + flags);
		dest.writeString(textComment);
		dest.writeValue(aUser);
		dest.writeValue(aPicture);
		dest.writeSerializable(timeStamp);
		dest.writeString(ID);
		dest.writeSerializable(aLocation);
	}

	/**
	 * creates a new top level comment
	 */
	public static final Parcelable.Creator<TopLevel> CREATOR = new Creator<TopLevel>() {

		@Override
		public TopLevel[] newArray(int size) {

			// TODO Auto-generated method stub
			return new TopLevel[size];
		}

		@Override
		public TopLevel createFromParcel(Parcel source) {

			return new TopLevel(source);
		}
	};

	@Override
	public void setaPicture(String aPicture) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * build a new topLevel commment
	 * @author Guillermo Ramirez
	 *
	 */
	

}

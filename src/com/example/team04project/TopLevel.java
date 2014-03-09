package com.example.team04project;

import java.util.UUID;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class TopLevel extends Comments
{

	private final String ID = UUID.randomUUID().toString();

	public TopLevel(String textComment, String aUser, Bitmap aPicture,
			String timeStamp)
	{

		super(textComment, aUser, aPicture, timeStamp);
	}

	public TopLevel()
	{

		super();
		this.aPicture = null;
		this.textComment = null;
		this.aUser = null;
		this.timeStamp = null;
	}

	private TopLevel(Parcel source)
	{

		Log.e("Parcealbe",
				"ParcelData(Parcel source): time to put back parcel data");
		textComment = source.readString();
		aUser = source.readString();
		aPicture = (Bitmap) source.readValue(getClass().getClassLoader());
		timeStamp = source.readString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{

		return "\n " + getTextComment() + "\n ------------------------------- "
				+ "\n " + getUserName() + " Date " + getDate();
	}

	public String getID()
	{

		return ID;
	}

	// testing parceable interface. hopelly it works.
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
		dest.writeString(aUser);
		dest.writeValue(aPicture);
		dest.writeString(timeStamp);
	}

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
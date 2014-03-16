package com.example.geocomment.model;

import java.util.Calendar;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Reply extends Comment {


	public Reply(User aUser, Calendar timeStamp, Bitmap aPicture,
			String textComment, double[] aLocation)
	{

		super(aUser,timeStamp,aPicture,textComment, aLocation);
	}

	public Reply()
	{

		super();
		this.aPicture = null;
		this.textComment = null;
		this.aUser = null;
		this.timeStamp = null;
	}

	private Reply(Parcel source)
	{

		Log.d("Parcealbe",
				"ParcelData(Parcel source): time to put back parcel data");
		textComment = source.readString();
		aUser = (User) source.readValue(getClass().getClassLoader());
		aPicture = (Bitmap) source.readValue(getClass().getClassLoader());
		timeStamp = (Calendar) source.readSerializable();
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
	}

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

}

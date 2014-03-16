package com.example.team04project.model;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class TopLevel extends Comments {

	private String tittle;
	@SuppressWarnings("unused")
	private List<Reply> replyList;

	public TopLevel(String textComment, Author aUser, Bitmap aPicture,
			String tittle) {
		super(textComment, aUser, aPicture);
		this.tittle = tittle;
		replyList = new ArrayList<Reply>();
	}

	private TopLevel(Parcel source) {
		Log.e("Parcealbe",
				"ParcelData(Parcel source): time to put back parcel data");
		textComment = source.readString();
		aUser = (Author) source.readValue(getClass().getClassLoader());
		aPicture = (Bitmap) source.readValue(getClass().getClassLoader());
		tittle = source.readString();
	}

	/**
	 * @return the tittle
	 */
	public String getTittle() {
		return tittle;
	}

	/**
	 * @param tittle
	 *            the tittle to set
	 */
	public void setTittle(String tittle) {
		this.tittle = tittle;
	}	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getTittle() +
				"\n" + getTextComment() +
				"\n" + getUserName();
	}

	//testing parceable interface. hopelly it works.
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		Log.e("parceable", "" + flags);
		dest.writeString(textComment);
		dest.writeValue(aUser);
		dest.writeValue(aPicture);
		dest.writeString(tittle);
	}

	public static final Parcelable.Creator<TopLevel> CREATOR = new Creator<TopLevel>() {

		@Override
		public TopLevel[] newArray(int size) {
			// TODO Auto-generated method stub
			return new TopLevel [size];
		}

		@Override
		public TopLevel createFromParcel(Parcel source) {
			return new TopLevel(source);
		}
	};

}

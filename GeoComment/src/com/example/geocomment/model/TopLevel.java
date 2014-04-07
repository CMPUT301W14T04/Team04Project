/*
Copyright (c) 2013, Guillermo Ramirez, Nadine Yushko, Tarek El Bohtimy, Yang Wang
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright notice,
this list of conditions and the following disclaimer in the documentation
and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

The views and conclusions contained in the software and documentation are those
of the authors and should not be interpreted as representing official policies,
either expressed or implied, of the FreeBSD Project.
*/

package com.example.geocomment.model;

import java.util.Calendar;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class TopLevel extends Comment {

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
			String textComment, double[] aLocation, String ID, int likes) {
		super(aUser, timeStamp, aPicture, textComment, aLocation, ID, likes);
		this.ID = ID;
	}

	public TopLevel(String textComment, String ID){
		super(textComment,ID);
	}
	
	public TopLevel() {

		super();
		this.aPicture = null;
		this.textComment = null;
		this.aUser = null;
		this.timeStamp = null;
		this.likes = 0;
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
		aLocation = (double[]) source.readSerializable();
		likes = source.readInt();
	}

	@Override
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
		dest.writeInt(likes);
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
	 * 
	 * @author Guillermo Ramirez
	 * 
	 */

}

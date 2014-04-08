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

import android.os.Parcel;
import android.os.Parcelable;

/**
 * The user class
 *
 */
public class User implements Parcelable {

	private String userName;
	private String ID;
	private double [] userLocation;

	/**
	 * @param userLocation
	 * @param userName
	 * @param ID
	 */
	public User(double[] userLocation, String userName, String ID) {
		this.userName = userName;
		this.ID = ID;
		this.userLocation = userLocation;
	}
	public User(){

	}

	/**
	 * @param source
	 */
	private User(Parcel source) {

		userLocation = source.createDoubleArray();
		userName = source.readString();
		this.ID = source.readString();
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {

		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName1) {

		userName = userName1;
	}

	/**
	 * @return the iD
	 */
	public String getID() {

		return ID;
	}

	public void setID(String ID)
	{
		this.ID=ID;
	}


	/**
	 * @return the userLocation
	 */
	public double[] getUserLocation() {
		return userLocation;
	}
	// testing Parceable
	@Override
	public int describeContents() {

		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

		// TODO Auto-generated method stub
		dest.writeDoubleArray(userLocation);
		dest.writeString(userName);
		dest.writeString(ID);
	}

	/**
	 * creates a new user
	 */
	public static final Parcelable.Creator<User> CREATOR = new Creator<User>() {

		@Override
		public User[] newArray(int size) {

			// TODO Auto-generated method stub
			return new User[size];
		}

		@Override
		public User createFromParcel(Parcel source) {

			// TODO Auto-generated method stub
			return new User(source);
		}
	};

}

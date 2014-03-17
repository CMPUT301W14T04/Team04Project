package com.example.geocomment.model;

import android.os.Parcel;
import android.os.Parcelable;

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

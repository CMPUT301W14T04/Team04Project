package com.example.team04project.model;

import java.util.UUID;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * 
 * This class take all the necesary data to create a author. It also create a ID for the user
 * so that multiples author can have same username, yet still be unique in the system.
 * if username it is not define by the user, he/she username will be Anonymous.
 * @author Guillermo Ramirez
 * 
 */
public class Author extends User{

	private String userName;
	private final String ID;

	public Author(Location userLocation, String userName)
	{
		super(userLocation);
		this.userName=userName;
		this.ID=generateID();
	}
	
	private Author (Parcel source)
	{
		userLocation=(Location) source.readValue(getClass().getClassLoader());
		userName = source.readString();
		this.ID=source.readString();
	}

	/**
	 * 
	 * @return A unique ID for each user.
	 */

	private String generateID() {
		return UUID.randomUUID().toString();
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
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the iD
	 */
	public String getID() {
		return ID;
	}

	//testing Parceable
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeValue(userLocation);
		dest.writeString(userName);
		dest.writeString(ID);
	}
	
	public static final Parcelable.Creator<Author> CREATOR = new Creator<Author>() {
		
		@Override
		public Author[] newArray(int size) {
			// TODO Auto-generated method stub
			return new Author [size];
		}
		
		@Override
		public Author createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new Author(source);
		}
	};

}

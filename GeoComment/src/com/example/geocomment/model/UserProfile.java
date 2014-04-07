package com.example.geocomment.model;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class UserProfile implements Parcelable {
	
	private User aUser;
	private String biography;
	private List<String> social;
	private Bitmap pic;
	private String quote;
	
	@SuppressWarnings("unchecked")
	private UserProfile(Parcel source)
	{
		Log.i("User profile", "parcel");
		this.biography= source.readString();
		this.aUser = (User) source.readValue(User.class.getClassLoader());
		this.social=source.readArrayList(getClass().getClassLoader());
		this.pic= (Bitmap) source.readValue(getClass().getClassLoader());
		this.quote = source.readString();
	}
	
	public UserProfile(User aUser)
	{
		this.aUser=aUser;
		this.biography=null;
		this.pic=null;
		this.quote=null;
		this.social = new ArrayList<String>();
	}
	/**
	 * @return the biography
	 */
	public String getBiography() {
		return biography;
	}
	/**
	 * @param biography the biography to set
	 */
	public void setBiography(String biography) {
		this.biography = biography;
	}
	/**
	 * @return the social
	 */
	public List<String> getSocial() {
		return social;
	}
	/**
	 * @param social the social to set
	 */
	public void setSocial(List<String> social) {
		this.social = social;
	}
	
	public void addSocial (String social)
	{
		this.social.add(social);
	}
	/**
	 * @return the pic
	 */
	public Bitmap getPic() {
		return pic;
	}
	/**
	 * @param pic the pic to set
	 */
	public void setPic(Bitmap pic) {
		this.pic = pic;
	}
	/**
	 * @return the quote
	 */
	public String getQuote() {
		return quote;
	}
	/**
	 * @param quote the quote to set
	 */
	public void setQuote(String quote) {
		this.quote = quote;
	}
	/**
	 * @return the userID
	 */
	public String getUserID() {
		return aUser.getID();
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return aUser.getUserName();
	}
	
	public void setUsername(String name)
	{
		this.aUser.setUserName(name);
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {

		Log.e("parceable", "UserProfile" + flags);
		dest.writeString(biography);
		dest.writeValue(aUser);
		dest.writeList(social);
		dest.writeValue(pic);
		dest.writeString(quote);
	}

	/**
	 * creates a new top level comment
	 */
	public static final Parcelable.Creator<UserProfile> CREATOR = new Creator<UserProfile>() {

		@Override
		public UserProfile[] newArray(int size) {

			// TODO Auto-generated method stub
			return new UserProfile[size];
		}

		@Override
		public UserProfile createFromParcel(Parcel source) {

			return new UserProfile(source);
		}
	};
	
}

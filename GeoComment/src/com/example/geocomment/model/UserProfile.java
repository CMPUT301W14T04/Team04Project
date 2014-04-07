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

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class UserProfile implements Parcelable {
	
	private User aUser;
	private String biography;
//	private List<String> social;
	private Bitmap pic;
	private String quote;
	
	@SuppressWarnings("unchecked")
	private UserProfile(Parcel source)
	{
		Log.i("User profile", "parcel");
		this.biography= source.readString();
		this.aUser = (User) source.readValue(User.class.getClassLoader());
//		this.social=source.readArrayList(getClass().getClassLoader());
		this.pic= (Bitmap) source.readValue(getClass().getClassLoader());
		this.quote = source.readString();
	}
	
	public UserProfile(User aUser)
	{
		this.aUser=aUser;
		this.biography=null;
		this.pic=null;
		this.quote=null;
//		this.social = new ArrayList<String>();
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
//	/**
//	 * @return the social
//	 */
//	public List<String> getSocial() {
//		return social;
//	}
//	/**
//	 * @param social the social to set
//	 */
//	public void setSocial(List<String> social) {
//		this.social = social;
//	}
//	
//	public void addSocial (String social)
//	{
//		this.social.add(social);
//	}
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
	
	public User getUser()
	{
		return this.aUser;
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
//		dest.writeList(social);
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

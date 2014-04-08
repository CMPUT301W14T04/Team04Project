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

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Comment class creates a comment, and all the 
 * inforamtion that comes with the comment, such as 
 * the picture, date, location, number of likes and the 
 * ID's. All the getters and setters are in this classS
 *
 */
@SuppressLint("ParcelCreator")
public abstract class Comment implements Parcelable, Commentor {

	protected User aUser;
	protected String textComment;
	public Bitmap aPicture;
	protected Calendar timeStamp;
	protected double [] aLocation;
	protected boolean favourite =false;
	protected String ID;
	protected int likes;


	/**
	 * initialize all the parameters of a comment
	 * @param aUser
	 * @param timeStamp
	 * @param aPicture2
	 * @param textComment
	 * @param aLocation
	 */
	public Comment(User aUser, Calendar timeStamp, Bitmap aPicture,
			String textComment, double [] aLocation,String ID, int likes) {

		this.aUser = aUser;
		this.timeStamp = timeStamp;
		this.aPicture = aPicture;
		this.textComment = textComment;
		this.aLocation = aLocation;
		this.ID=ID;
		this.likes=likes;
	}

	public Comment() {

	}

	public Comment(String textComment, String ID) {
		// TODO Auto-generated constructor stub
		this.textComment=textComment;
		this.ID=ID;
	}
	
	

	/* (non-Javadoc)
	 * @see com.example.geocomment.model.Commentor#getUser()
	 */
	@Override
	public User getUser() {
		// TODO Auto-generated method stub
		return aUser;
	}

	/* (non-Javadoc)
	 * @see com.example.geocomment.model.Commentor#setUser(com.example.geocomment.model.User)
	 */
	@Override
	public void setUser(User aUser) {
		// TODO Auto-generated method stub
		this.aUser = aUser;
	}

	/* (non-Javadoc)
	 * @see android.os.Parcelable#describeContents()
	 */
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @return User Username
	 */
	@Override
	public String getUserName() {
		return aUser.getUserName();
	}

	/**
	 * @return User ID
	 */
	@Override
	public String getUserID() {
		return aUser.getID();
	}

	/**
	 * @return the textComment
	 */
	@Override
	public String getTextComment() {
		return textComment;
	}

	/**
	 * @param textComment
	 *            the textComment to set
	 */
	@Override
	public void setTextComment(String textComment) {
		this.textComment = textComment;
	}

	/**
	 * @return the aPicture
	 */
	@Override
	public Bitmap getaPicture() {
		return aPicture;
	}

	/**
	 * @param aPicture
	 *            the aPicture to set
	 */
	public void setaPicture(Bitmap aPicture) {
		this.aPicture = aPicture;
	}

	/**
	 * @param timeStamp
	 */
	@Override
	public void setTimeStamp(Calendar timeStamp) {
		Log.d("Date", this.timeStamp.toString());
		this.timeStamp = timeStamp;
	}

	/**
	 * @return
	 */
	@Override
	public Calendar getDate() {
		return timeStamp;
	}

	/**
	 * @return the aLocation
	 */
	@Override
	public double[] getaLocation() {
		return aLocation;
	}
	
	@Override
	public String getID()
	{
		return this.ID;
	}
	
	
	/*
	 * Set favourite
	 */
	@Override
	public boolean isFavourite() {
		return favourite;
	}

	/*
	 * Get favourite
	 */
	@Override
	public void setFavourite(boolean favourite) {
		this.favourite = favourite;
	}

	/* (non-Javadoc)
	 * @see com.example.geocomment.model.Commentor#getLikes()
	 */
	@Override
	public int getLikes() {
		// TODO Auto-generated method stub
		return likes;
	}

	/* (non-Javadoc)
	 * @see com.example.geocomment.model.Commentor#setLikes(int)
	 */
	@Override
	public void setLikes(int likes) {
		// TODO Auto-generated method stub
		this.likes = likes;
	}
	
}

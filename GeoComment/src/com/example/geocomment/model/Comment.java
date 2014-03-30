package com.example.geocomment.model; 
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.util.Log;

@SuppressLint("ParcelCreator")
public abstract class Comment implements Parcelable, Commentor {

	protected User aUser;
	protected String textComment;
	public Bitmap aPicture;
	protected Calendar timeStamp;
	protected double [] aLocation;
	protected boolean favourite =false;
	protected String ID;


	/**
	 * initialize all the parameters of a comment
	 * @param aUser
	 * @param timeStamp
	 * @param aPicture2
	 * @param textComment
	 * @param aLocation
	 */
	public Comment(User aUser, Calendar timeStamp, Bitmap aPicture,
			String textComment, double [] aLocation,String ID) {

		this.aUser = aUser;
		this.timeStamp = timeStamp;
		this.aPicture = aPicture;
		this.textComment = textComment;
		this.aLocation = aLocation;
		this.ID=ID;
	}

	public Comment() {

	}

	/**
	 * @return User Username
	 */
	public String getUserName() {
		return aUser.getUserName();
	}

	/**
	 * @return User ID
	 */
	public String getUserID() {
		return aUser.getID();
	}

	/**
	 * @return the textComment
	 */
	public String getTextComment() {
		return textComment;
	}

	/**
	 * @param textComment
	 *            the textComment to set
	 */
	public void setTextComment(String textComment) {
		this.textComment = textComment;
	}

	/**
	 * @return the aPicture
	 */
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
	public void setTimeStamp(Calendar timeStamp) {
		Log.d("Date", this.timeStamp.toString());
		this.timeStamp = timeStamp;
	}

	/**
	 * @return
	 */
	public Calendar getDate() {
		return timeStamp;
	}

	/**
	 * @return the aLocation
	 */
	public double[] getaLocation() {
		return aLocation;
	}
	
	public String getID()
	{
		return this.ID;
	}
	
	
	/*
	 * Set favourite
	 */
	public boolean isFavourite() {
		return favourite;
	}

	/*
	 * Get favourite
	 */
	public void setFavourite(boolean favourite) {
		this.favourite = favourite;
	}

}

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
	protected String Likes;


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
		this.Likes=Likes;
	}

	public Comment() {

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

}

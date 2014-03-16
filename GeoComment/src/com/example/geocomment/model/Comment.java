package com.example.geocomment.model;

import java.util.Calendar;

import android.graphics.Bitmap;
import android.os.Parcelable;

public abstract class Comment implements Parcelable {

	protected User aUser;
	protected String textComment;
	protected Bitmap aPicture;
	protected Calendar timeStamp;
	protected double [] aLocation;

	public Comment(User aUser, Calendar timeStamp, Bitmap aPicture,
			String textComment, double [] aLocation) {

		this.aUser = aUser;
		this.timeStamp = timeStamp;
		this.aPicture = aPicture;
		this.textComment = textComment;
		this.aLocation = aLocation;
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
	 * 
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

	public void setTimeStamp(Calendar timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Calendar getDate() {
		return timeStamp;
	}

	/**
	 * @return the aLocation
	 */
	public double[] getaLocation() {
		return aLocation;
	}
	

}

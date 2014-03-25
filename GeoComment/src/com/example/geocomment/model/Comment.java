package com.example.geocomment.model; 
import java.util.Calendar;

import android.os.Parcelable;

import com.google.gson.JsonElement;

public abstract class Comment implements Parcelable, Commentor {

	protected User aUser;
	protected String textComment;
	public String aPicture;
	protected Calendar timeStamp;
	protected double [] aLocation;

	/**
	 * initialize all the parameters of a comment
	 * @param aUser
	 * @param timeStamp
	 * @param aPicture2
	 * @param textComment
	 * @param aLocation
	 */
	public Comment(User aUser, Calendar timeStamp, String aPicture2,
			String textComment, double [] aLocation) {

		this.aUser = aUser;
		this.timeStamp = timeStamp;
		this.aPicture = aPicture2;
		this.textComment = textComment;
		this.aLocation = aLocation;
	}

	public Comment() {

	}

	public Comment(User aUser2, Calendar timeStamp2, JsonElement aPicture2,
			String textComment2, double[] aLocation2) {
		// TODO Auto-generated constructor stub
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
	public String getaPicture() {
		return aPicture;
	}

	/**
	 * @param aPicture
	 *            the aPicture to set
	 */
	public void setaPicture(String aPicture) {
		this.aPicture = aPicture;
	}

	/**
	 * @param timeStamp
	 */
	public void setTimeStamp(Calendar timeStamp) {
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


}

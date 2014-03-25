package com.example.geocomment.model;

import java.util.Calendar;

import com.google.gson.JsonElement;

public interface Commentor {
	
	public String getUserName(); 

	/**
	 * @return User ID
	 */
	public String getUserID() ;

	/**
	 * @return the textComment
	 */
	public String getTextComment() ;
	/**
	 * @param textComment
	 *            the textComment to set
	 */
	public void setTextComment(String textComment) ;

	/**
	 * @return the aPicture
	 */
	public String getaPicture();

	/**
	 * @param aPicture
	 *            the aPicture to set
	 */
	public void setaPicture(String aPicture) ;

	/**
	 * @param timeStamp
	 */
	public void setTimeStamp(Calendar timeStamp) ;

	/**
	 * @return
	 */
	public Calendar getDate() ;

	/**
	 * @return the aLocation
	 */
	public double[] getaLocation();



}

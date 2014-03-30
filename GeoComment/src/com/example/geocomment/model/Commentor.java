package com.example.geocomment.model;

import java.util.Calendar;

import android.graphics.Bitmap;

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
	public Bitmap getaPicture();

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
	
	public String getID();

	/**
	 * @return the aLocation
	 */
	public double[] getaLocation();

	public boolean isFavourite();
	
	public void setFavourite(boolean favourite);

}

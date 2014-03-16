package com.example.team04project.model;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.Parcelable;

/**
 * This class gather al the information need it for a comment.
 * 
 * 
 */
public abstract class Comments implements Parcelable{


	protected Author aUser;
	protected Location aLocation;//not sure if comment store userLocatin.
	protected String textComment;
	protected Bitmap aPicture;

	public Comments(String textComment, Author aUser, Bitmap aPicture) {

		this.textComment = textComment;
		this.aUser=aUser;
		this.aPicture=aPicture;
	}
	public Comments()
	{
		
	}

	/**
	 * @return the aUser
	 */
	public String getUserName() {
		return aUser.getUserName();
	}

	/**
	 * @return the aLocation
	 */
	public Location getaLocation() {
		return aLocation;
	}

	/**
	 * @param aLocation the aLocation to set
	 */
	public void setaLocation(Location aLocation) {
		this.aLocation=aLocation;
	}

	/**
	 * @return the textComment
	 */
	public String getTextComment() {
		return textComment;
	}

	/**
	 * @param textComment the textComment to set
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
	 * @param aPicture the aPicture to set
	 */
	public void setaPicture(Bitmap aPicture) {
		this.aPicture = aPicture;
	}
	/**
	 * @param aUser the aUser to set
	 */
	public void setUsername(String username) {
		this.aUser.setUserName(username);
	}	

}

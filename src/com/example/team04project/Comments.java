package com.example.team04project;

import android.graphics.Bitmap;
import android.os.Parcelable;

/**
 * This class gather al the information need it for a comment.
 *
 *
 */
public abstract class Comments implements Parcelable{


	protected String aUser;
	protected String textComment;
	protected Bitmap aPicture;
	protected String timeStamp;

	public Comments(String textComment, String aUser, Bitmap aPicture,String timeStamp) {

		this.textComment = textComment;
		this.aUser=aUser;
		this.aPicture=aPicture;
		this.timeStamp=timeStamp;
	}
	public Comments()
	{

	}

	/**
	 * @return the aUser
	 */
	public String getUserName() {
		return aUser;
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
		this.aUser= username;
	}	

	public void setTimeStamp (String timeStamp)
	{
		this.timeStamp=timeStamp;
	}

	public String getDate()
	{
		return timeStamp;
	}

}
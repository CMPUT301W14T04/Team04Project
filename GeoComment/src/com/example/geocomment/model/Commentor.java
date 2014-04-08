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

import android.graphics.Bitmap;

/**
 * Getters and setters for the comments
 *
 */
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
	
	public int getLikes();
	
	public void setLikes(int likes);
	
	public User getUser();
	
	public void setUser(User aUser);
}

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

package com.example.geocomment.util;

import java.util.UUID;

/**
 * This class containt constants use in this APP, an some general method such as
 *  {@link #generateID()}
 * @author Guillermo Ramirez
 *
 */
public class Resource {

	public final static String FAVOURITE_FILE = "favourite.json";
	public final static String GENERAL_INFO = "info.json";
	public final static String LIST_STORE = "list.json";
	public final static String CACHE_STORE = "cache.json";
	public final static String FAVOURITE_REPLIES = "replies.json";

	
	
	//integer that represent save and load of files.
	public final static int FAVOURITE_SAVE = 2;
	public final static int FAVOURITE_LOAD = 3;
	public final static int GENERAL_INFO_LOAD = 4;
	public final static int GENERAL_INFO_SAVE = 5;
	
	//passing data between activities
	public final static String USER_INFO = "user info";
	public final static String USER_LOCATION_HISTORY = "user location history";
	public final static String TOP_LEVEL_COMMENT = " top level";
	public final static String TYPE_COMMENT = "type comment";
	
	//Type for creating comments
	public final static int TYPE_TOP_LEVEL = 10;
	public final static int TYPE_REPLY = 11;
	
	//getting resquest code from activities
	public final static int RESQUEST_NEW_TOP_LEVEL = 20;
	public final static int COMMENT_EDITED = 21;

	
	
	/**
	 * 
	 * @return A random ID for User or Comments.
	 */
	
	public static String generateID()
	{
		return UUID.randomUUID().toString();
	}

}

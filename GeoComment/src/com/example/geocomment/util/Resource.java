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

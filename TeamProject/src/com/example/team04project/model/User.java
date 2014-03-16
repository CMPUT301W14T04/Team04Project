package com.example.team04project.model;

import android.location.Location;
import android.os.Parcelable;


/**
 * @author Guillermo Ramirez
 * 
 */
public abstract class User implements Parcelable{

	protected Location userLocation;

	public User(Location userLocation)
	{
		this.userLocation = userLocation;
	}
	public User()
	{
		
	}
}

package com.example.team04project;

import java.util.UUID;

import android.location.Location;

/**
 * @author Guillermo Ramirez
 *
 */
public abstract class User {
	protected Location userLocation;
	protected String userName = null;
	protected final static String ID = generateID();

	public User(Location userLocation) {
		this.userLocation = userLocation;
	}

	public User(Location userLocation, String userName) {
		this.userLocation = userLocation;
		this.userName = userName;
	}

	private static String generateID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * @return the userLocation
	 */
	public Location getUserLocation() {
		return userLocation;
	}

	/**
	 * @param userLocation
	 *            the userLocation to set
	 */
	public void setUserLocation(Location userLocation) {
		this.userLocation = userLocation;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the id
	 */
	public static String getId() {
		return ID;
	}

}

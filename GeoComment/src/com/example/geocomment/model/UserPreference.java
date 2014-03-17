package com.example.geocomment.model;


public class UserPreference {
	private String userName;
	private String id;
	private LocationList locationList;

	public UserPreference(String userName, String id,
			LocationList locationList) {

		this.userName=userName;
		this.id=id;
		this.locationList=locationList;
	}
	public UserPreference()
	{

	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the locationList
	 */
	public LocationList getLocationList() {
		return locationList;
	}
	
	@Override
	public String toString() {
		return "UserPreference [userName=" + userName + ", id=" + id
				+ ", locationList=" + locationList + "]";
	}



}

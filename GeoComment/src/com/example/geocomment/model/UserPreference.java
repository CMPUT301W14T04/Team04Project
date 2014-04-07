package com.example.geocomment.model;


public class UserPreference {
	private String userName;
	private String id;
	private LocationList locationList;
	private UserProfile profile;

	public UserPreference(String userName, String id,
			LocationList locationList, UserProfile profile) {

		this.userName=userName;
		this.id=id;
		this.locationList=locationList;
		this.profile=profile;
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
	/**
	 * @return the profile
	 */
	public UserProfile getProfile() {
		return profile;
	}
	/**
	 * @param profile the profile to set
	 */
	public void setProfile(UserProfile profile) {
		this.profile = profile;
	}


}

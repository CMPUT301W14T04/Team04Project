package com.example.team04project.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.location.Location;


public class LocationModelList {

	private List <Location> locationList;
	
	public LocationModelList ()
	{
		locationList= new ArrayList<Location>();
	}
	
	
	public void addLocationToList(Location aLocation)
	{
		locationList.add(aLocation);
	}
	
	public void clear()
	{
		locationList.clear();
	}
	
	public List<Location> getList()
	{
		return Collections.unmodifiableList(this.locationList);
	}
	
	public Location getLastLocation()
	{
		int lastLocationIndex = locationList.size() - 1;
		Location lastLocation = locationList.get(lastLocationIndex);
		return lastLocation;
	}
	
	public Location getOneLocationFromList (int i)
	{
		return locationList.get(i);
	}
	
	

}

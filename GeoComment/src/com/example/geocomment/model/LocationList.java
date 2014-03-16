package com.example.geocomment.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

public class LocationList implements Parcelable {

	private List<double[]> locationHistory;

	public LocationList() {
		this.locationHistory = new ArrayList<double[]>();
	}
	
	@SuppressWarnings("unchecked")
	private LocationList(Parcel source)
	{
		this.locationHistory= (ArrayList<double []>)source.readArrayList(getClass().getClassLoader());
	}

	private int getListSize() {
		return locationHistory.size();
	}

	public void addLocation (Location location)
	{
		double lat = location.getLatitude();
		double lon = location.getLongitude();
		
		double [] lo = {lat,lon};
		if(getListSize()<=4)
			locationHistory.add(lo);
		else{
			locationHistory.remove(getListSize());
			locationHistory.add(0, lo);
		}

	}
	
	public List<double[]> getLocationList()
	{
		return Collections.unmodifiableList(locationHistory);
	}
	
	/**
	 * @precondition i should be less than 4.
	 * @param the index of one location
	 * @return A location from the list or null.
	 */
	public double[] getLocationFromList(int i)
	{
		return locationHistory.get(i);
	}
	
	public void setLocationList(List<double[]> locationHistory)
	{
		this.locationHistory=locationHistory;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeList(locationHistory);
		
	}
	public static final Parcelable.Creator<LocationList> CREATOR = new Creator<LocationList>()
			{

				@Override
				public LocationList[] newArray(int size)
				{

					// TODO Auto-generated method stub
					return new LocationList[size];
				}

				@Override
				public LocationList createFromParcel(Parcel source)
				{

					return new LocationList(source);
				}
			};
}

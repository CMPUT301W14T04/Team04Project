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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

public class LocationList implements Parcelable {

	private List<double[]> locationHistory;

	/**
	 * returns a list of locations
	 */
	public LocationList() {
		this.locationHistory = new ArrayList<double[]>();
	} 

	/**
	 * @param source
	 */
	@SuppressWarnings("unchecked")
	private LocationList(Parcel source)
	{
		this.locationHistory= source.readArrayList(getClass().getClassLoader());
	}

	private int getListSize() {
		return locationHistory.size();
	}

	/**
	 * asks user to modify location
	 * @param location
	 */
	public void addLocation (Location location)
	{
		double lat = 0;
		double lon = 0;
		try {
			lat = location.getLatitude();
			lon = location.getLongitude();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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

	/**
	 * @param locationHistory
	 */
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

	/**
	 * creates a location
	 */
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

package com.example.team04project.model;

import android.location.Location;
import android.os.Parcel;



public class Reader extends User
{
	
	//private FavouritesModelList favouritesList;

	public Reader(Location aLocation, FavouritesModelList favouritesList) {
		super(aLocation);
		//this.favouritesList = favouritesList;
		
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}
	


}

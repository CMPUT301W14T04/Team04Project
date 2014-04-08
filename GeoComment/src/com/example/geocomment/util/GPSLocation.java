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

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * Gets the location of the user using Google
 * This is later used for sorting an also
 * to display the location of the user when they make
 * a comment
 *
 */
public class GPSLocation extends Service implements LocationListener
{

	private final Context aContext;

	private boolean isGPSEnabled = false;
	private boolean isNetworkEnabled = false;
	private boolean canGetLocation = false;

	private Location aLocation;

	// Declaring a Location Manager
	protected LocationManager locationManager;

	private double latitude;

	private double longitude;

	public GPSLocation(Context context)
	{

		this.aContext = context;
		getLocation();
	}

	public Location getLocation()
	{

		try
		{
			locationManager = (LocationManager) aContext
					.getSystemService(LOCATION_SERVICE);

			// getting GPS status
			isGPSEnabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);

			// getting network status
			isNetworkEnabled = locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			if (!isGPSEnabled && !isNetworkEnabled)
			{
				Toast.makeText(aContext, "Unable to find your location"
						+ "\n Check your settings", Toast.LENGTH_SHORT).show();
			} else
			{
				this.canGetLocation = true;
				if (isNetworkEnabled)
				{
					locationManager.requestLocationUpdates(
							LocationManager.NETWORK_PROVIDER, 0, 0, this);
					Log.d("Network", "Network");
					if (locationManager != null)
					{
						aLocation = locationManager
								.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						if (aLocation != null)
						{
							latitude = aLocation.getLatitude();
							longitude = aLocation.getLongitude();
						}
					}
				}
				// if GPS Enabled get lat/long using GPS Services
				if (isGPSEnabled)
				{
					if (aLocation == null)
					{
						locationManager.requestLocationUpdates(
								LocationManager.GPS_PROVIDER, 0, 0, this);
						Log.d("GPS Enabled", "GPS Enabled");
						if (locationManager != null)
						{
							aLocation = locationManager
									.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							if (aLocation != null)
							{
								latitude = aLocation.getLatitude();
								longitude = aLocation.getLongitude();
							}
						}
					}
				}
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return aLocation;
	}

	/**
	 * Stop using GPS listener
	 * */
	public void stopUsingGPS()
	{

		if (locationManager != null)
		{
			locationManager.removeUpdates(GPSLocation.this);
		}
	}

	/**
	 * get latitude
	 * */
	public double getLatitude()
	{

		if (aLocation != null)
		{
			latitude = aLocation.getLatitude();
		}

		// return latitude
		return latitude;
	}

	/**
	 * get longitude
	 * */
	public double getLongitude()
	{

		if (aLocation != null)
		{
			longitude = aLocation.getLongitude();
		}

		// return longitude
		return longitude;
	}

	/**
	 * check if GPS/wifi is enabled
	 * 
	 * @return boolean
	 * */
	public boolean canGetLocation()
	{

		return this.canGetLocation;
	}

	@Override
	public void onLocationChanged(Location location)
	{

	}

	@Override
	public void onProviderDisabled(String provider)
	{

	}

	@Override
	public void onProviderEnabled(String provider)
	{

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras)
	{

	}

	@Override
	public IBinder onBind(Intent arg0)
	{

		return null;
	}

}
package com.example.team04project;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class GPSLocation extends Service implements LocationListener {

	private final Context aContext;

	private boolean isGPSEnabled = false;
	private boolean isNetworkEnabled = false;
	private boolean canGetLocation = false;

	private Location aLocation;

	// Declaring a Location Manager
	protected LocationManager locationManager;

	private double latitude;

	private double longitude;

	public GPSLocation(Context context) {
		this.aContext = context;
		getLocation();
	}

	public Location getLocation() {
		try {
			locationManager = (LocationManager) aContext
					.getSystemService(LOCATION_SERVICE);

			// getting GPS status
			isGPSEnabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);

			// getting network status
			isNetworkEnabled = locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			if (!isGPSEnabled && !isNetworkEnabled) {
				// no network provider is enabled
			} else {
				this.canGetLocation = true;
				if (isNetworkEnabled) {
					locationManager.requestLocationUpdates(
							LocationManager.NETWORK_PROVIDER, 0, 0, this);
					Log.d("Network", "Network");
					if (locationManager != null) {
						aLocation = locationManager
								.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						if (aLocation != null) {
							latitude = aLocation.getLatitude();
							longitude = aLocation.getLongitude();
						}
					}
				}
				// if GPS Enabled get lat/long using GPS Services
				if (isGPSEnabled) {
					if (aLocation == null) {
						locationManager.requestLocationUpdates(
								LocationManager.GPS_PROVIDER, 0, 0, this);
						Log.d("GPS Enabled", "GPS Enabled");
						if (locationManager != null) {
							aLocation = locationManager
									.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							if (aLocation != null) {
								latitude = aLocation.getLatitude();
								longitude = aLocation.getLongitude();
							}
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return aLocation;
	}

	/**
	 * Stop using GPS listener
	 * */
	public void stopUsingGPS() {
		if (locationManager != null) {
			locationManager.removeUpdates(GPSLocation.this);
		}
	}

	/**
	 * get latitude
	 * */
	public double getLatitude() {
		if (aLocation != null) {
			latitude = aLocation.getLatitude();
		}

		// return latitude
		return latitude;
	}

	/**
	 * get longitude
	 * */
	public double getLongitude() {
		if (aLocation != null) {
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
	public boolean canGetLocation() {
		return this.canGetLocation;
	}

	@Override
	public void onLocationChanged(Location location) {
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

}

package com.example.geocomment.util;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;


public class Internet
{
	private final String NOT_CONNECTION = "Unable to connect";

	private Context aContext;
	public Internet (Context aContext)
	{
		this.aContext = aContext;

	}
	
	/*
	 * This function uses the ConnectivityManager class
	 * to check if a connection exists and checks all
	 * network information to make sure it is connected.
	 */
	public boolean isConnectedToInternet()
	{
		ConnectivityManager connectivity = (ConnectivityManager) aContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		if(connectivity != null)
		{
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED)
					{
						return true;
					}
		}
		else
		{
			Toast.makeText(aContext, NOT_CONNECTION, Toast.LENGTH_SHORT).show();
		}
		return false;
	}
}
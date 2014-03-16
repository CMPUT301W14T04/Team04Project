package com.example.team04project.model;

import java.util.ArrayList;



public class FavouritesModelList
{
	private ArrayList <TopLevel> favouritesList;
	public FavouritesModelList()
	{
		favouritesList= new ArrayList<TopLevel>();
	}
	
	public void addFavourites (TopLevel aComment)
	{
		favouritesList.add(aComment);
	}
	
	public ArrayList<TopLevel>getList()
	{
		return favouritesList;
	}
	

}

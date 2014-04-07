package com.example.geocomment.model;

import java.util.ArrayList;
import java.util.List;

import android.widget.ArrayAdapter;

import com.example.geocomment.CommentBrowseActivity;
import com.example.geocomment.GeoCommentActivity;

public class favourites {
	GeoCommentActivity activity;
	CommentBrowseActivity activity1;
	static List<Commentor> favouriteGeo = new ArrayList<Commentor>();
	static List<Commentor> favouriteBrowse = new ArrayList<Commentor>();
	static List<Commentor> favouriteStore = new ArrayList<Commentor>();
	static List<Commentor> favourites = new ArrayList<Commentor>();
	
	
	/*
	 * Takes the favourited comments retrieved from
	 * elastic search and stores them in a list
	 * to show when requested
	 */
	public static void updateGeo(List<Commentor> commentList){
		favouriteGeo.clear();
		favourites.clear();
		for(Commentor c: commentList){
			if(c.isFavourite()==true){
				if(!favouriteGeo.contains(c)){
					favouriteGeo.add(c);
				}
			}
		}
	}
	
	/*
	 * Takes the favourited replies and stores them
	 * to be shown upon request
	 */
	public static void updateBrowse(List<Commentor> commentList){
		for(Commentor c: favouriteBrowse){
			if(c.isFavourite()==false){
				favouriteBrowse.remove(c);
			}
		}
		for(Commentor c: commentList){
			if(c.isFavourite()==true){
				favouriteBrowse.add(c);
			}
		}
	}
	
	
	/*
	 * Returns the list of favourited replies and comments
	 * to show to the user on the adapter
	 */
	public static List<Commentor> returnFav(){
		favourites.clear();
		favourites.addAll(favouriteBrowse);
		favourites.addAll(favouriteGeo);
		return favourites;
	}
	
	public static void load(List<Commentor> list){
		favouriteGeo.addAll(list);
	}

	public static List<Commentor> getFavouriteGeo() {
		return favouriteGeo;
	}
}

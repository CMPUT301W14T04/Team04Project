package com.example.geocomment.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.example.geocomment.elasticsearch.ElasticSearchOperations;

import android.widget.ArrayAdapter;

public class TopLevelList {

	private List<TopLevel> topLevelList;
	private static List<TopLevel> favourites;
	private static ArrayAdapter<TopLevel> adapter;

	
	public TopLevelList() {
		this.topLevelList = new ArrayList<TopLevel>();
		favourites = new ArrayList<TopLevel>();
	}
	
	public static void AddFavourite(TopLevel fav){
		favourites.add(fav);
		adapter.notifyDataSetChanged();
	}
	
	public static void RemoveFavourite(TopLevel fav){
		for (TopLevel t: favourites){
			if(t.getID().equals(fav.getID())){
				favourites.remove(t);
			}
		}
	}
	public void favClear() {
		this.favourites.clear();
		this.adapter.notifyDataSetChanged();
	}

	public List<TopLevel> getFavList() {
		return Collections.unmodifiableList(favourites);
	}
	
	/**
	 * call pushComment in ElasticSearchOperations to 
	 * push a top level comment to the server
	 * @param topLevel
	 */
	public void AddTopLevel(TopLevel topLevel) {
		this.topLevelList.add(0,topLevel);
		ElasticSearchOperations.pushComment(topLevel);
		this.adapter.notifyDataSetChanged();
	}

	/**
	 * add a top level comment to top level comment collection
	 * @param pots
	 */
	public void addTopLevelCollection(Collection<TopLevel> pots) {
		this.topLevelList.addAll(pots);
		this.adapter.notifyDataSetChanged();
	}

	/**
	 * clear top level comment collection
	 */
	public void clear() {
		this.topLevelList.clear();
		this.adapter.notifyDataSetChanged();
	}

	public List<TopLevel> getList() {
		return Collections.unmodifiableList(topLevelList);
	}

	public void setAdapter(ArrayAdapter<TopLevel> adapter) {
		this.adapter = adapter;
	}

}

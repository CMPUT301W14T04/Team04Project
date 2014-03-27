package com.example.geocomment.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.geocomment.elasticsearch.ElasticSearchOperations;

public class TopLevelList {

	private List<Commentor> topLevelList;
	private ArrayAdapter<Commentor> adapter;
	private List<Commentor> favourite;
	private List<Commentor> picture = null;
	private List<Commentor> socre;
	private List<Commentor> proxiMe;
	private List<Commentor> proxiLoc;

	public TopLevelList() {
		this.topLevelList = new ArrayList<Commentor>();
		this.favourite= new ArrayList<Commentor>();
		this.picture = new ArrayList<Commentor>();
		this.socre = new ArrayList<Commentor>();
		this.proxiMe = new ArrayList<Commentor>();
		this.proxiLoc = new ArrayList<Commentor>();
	}
	
	/**
	 * call pushComment in ElasticSearchOperations to 
	 * push a top level comment to the server
	 * @param comment
	 */
	public void AddTopLevel(Commentor comment) {
		this.topLevelList.add(0,comment);
		if(comment.getaPicture()==null)
		{
			Log.e("Picture is null", "is null" );
		}
		ElasticSearchOperations.pushComment(comment,1);
		this.adapter.notifyDataSetChanged();
	}

	/**
	 * add a top level comment to top level comment collection
	 * @param pots
	 */
	public void addTopLevelCollection(Collection<Commentor> pots) {
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

	public List<Commentor> getList() {
		return Collections.unmodifiableList(topLevelList);
	}
	public List<Commentor> getFavList() {
		return Collections.unmodifiableList(favourite);
	}
	
	public List<Commentor> getPictureList() {
		// TODO Auto-generated method stub
		return Collections.unmodifiableList(picture);
	}
	
	public List<Commentor> getScoreList() {
		// TODO Auto-generated method stub
		return Collections.unmodifiableList(socre);
	}

	public List<Commentor> getProxiMeList() {
		// TODO Auto-generated method stub
		return Collections.unmodifiableList(proxiMe);
	}

	public List<Commentor> getProxiLocList() {
		// TODO Auto-generated method stub
		return Collections.unmodifiableList(proxiLoc);
	}

	public void setAdapter(ArrayAdapter<Commentor> adapter) {
		this.adapter = adapter;
	}
	
	public Commentor getComment(int i)
	{
		return topLevelList.get(i);
	}
	
	
	public void update(){
		for (Commentor c:topLevelList){
			if (c.isFavourite()==true){
				favourite.add(c);
			}
		}
		this.adapter.notifyDataSetChanged();
	}
	
	public void updatePicture() {
		for (Commentor c: topLevelList) {
			if (c.getaPicture() != null & picture.contains(c) == false) {
				picture.add(c);
			}
		}
		for (Commentor c: topLevelList) {
			if (c.getaPicture() == null & picture.contains(c) == false) {
				picture.add(c);
			}
		}
		this.adapter.notifyDataSetChanged();
	}
	//Implement need comments to have ID's
	/*public void removeFav(Commentor comment){
		for (Commentor c: favourite){
			if c.ge
		}
	}*/
	
}

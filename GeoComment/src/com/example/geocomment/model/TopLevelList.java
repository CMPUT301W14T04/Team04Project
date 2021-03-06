package com.example.geocomment.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.location.Location;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.geocomment.GeoCommentActivity;
import com.example.geocomment.elasticsearch.ElasticSearchOperations;
import com.example.geocomment.util.GPSLocation;

public class TopLevelList {

	private List<Commentor> topLevelList;
	private ArrayAdapter<Commentor> adapter;
	private List<Commentor> picture;
	private List<Commentor> score;
	private List<Commentor> proxiMe;
	private List<Commentor> proxiLoc;
	private List<Commentor> nonPicture;
	private List<Commentor> dateList;
	private List<Commentor> likesList;
	GeoCommentActivity main;
	GPSLocation gpsLocation;

	public TopLevelList() {
		this.topLevelList = new ArrayList<Commentor>();
		this.picture = new ArrayList<Commentor>();
		this.nonPicture = new ArrayList<Commentor>();
		this.score = new ArrayList<Commentor>();
		this.proxiMe = new ArrayList<Commentor>();
		this.proxiLoc = new ArrayList<Commentor>();
		this.dateList = new ArrayList<Commentor>();
		this.likesList = new ArrayList<Commentor>();
	}

	/**
	 * call pushComment in ElasticSearchOperations to push a top level comment
	 * to the server
	 * 
	 * @param comment
	 */
	public void AddTopLevel(Commentor comment, int type) {
		this.topLevelList.add(0, comment);
		if (comment.getaPicture() == null) {
			Log.e("Picture is null", "is null");
		}
		if (type == 1)
			ElasticSearchOperations.pushComment(comment, 1);
		if(type==1)
			ElasticSearchOperations.pushComment(comment,1);
		else
			ElasticSearchOperations.pushComment(comment, 2);
		//this.adapter.notifyDataSetChanged();
	}

	/**
	 * add a top level comment to top level comment collection
	 * 
	 * @param pots
	 */
	public void addTopLevelCollection(Collection<Commentor> pots) {
		this.topLevelList.addAll(pots);
			Collections.sort(topLevelList, new Comparator<Commentor>() {
			@Override
			public int compare(Commentor comment1, Commentor comment2) {
				return comment2.getDate().compareTo(comment1.getDate());
			}
		});
		if (adapter != null){
			this.adapter.notifyDataSetChanged();
		}
	}

	/**
	 * clear top level comment collection
	 */
	public void addOne(Commentor c){
		topLevelList.add(c);
	}
	public void clear() {
		this.topLevelList.clear();
		if (adapter != null){
			this.adapter.notifyDataSetChanged();
		}
	}

	public List<Commentor> getList() {		
		return Collections.unmodifiableList(topLevelList);
	}

	public List<Commentor> getPictureList() {
		// TODO Auto-generated method stub
		return Collections.unmodifiableList(picture);
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

	public Commentor getComment(int i) {
		return topLevelList.get(i);
	}
	
	/**
	 * sort comments by picture
	 */
	public void updatePicture() {
		for (Commentor c: topLevelList) {
			if (c.getaPicture() != null & !picture.contains(c)) {
				picture.add(c);
				Collections.sort(picture, new Comparator<Commentor>() {
					@Override
					public int compare(Commentor comment1, Commentor comment2) {
						return comment2.getDate().compareTo(comment1.getDate());
					}
				});
			} else if (c.getaPicture() == null
					& !nonPicture.contains(c)) {
				nonPicture.add(c);
				Collections.sort(nonPicture, new Comparator<Commentor>() {
					@Override
					public int compare(Commentor comment1, Commentor comment2) {
						return comment2.getDate().compareTo(comment1.getDate());
					}
				});
			}
		}
		if (picture.contains(nonPicture.get(0)) == false) {
			picture.addAll(nonPicture);
		}
		this.adapter.notifyDataSetChanged();
	}

	/**
	 * sort comments by proximity to me
	 */
	public void updateProxiMe() {
		// TODO Auto-generated method stub
		Collections.sort(topLevelList, new Comparator<Commentor>() {
			public int compare(Commentor comment1, Commentor comment2) {
				Location currentLocation = gpsLocation.getLocation();
				if(currentLocation == null){
					return 0;
				}

				Location location1 = new Location("Location");
				if (comment1.getaLocation() != null) {
					location1.setLatitude(comment1.getaLocation()[1]);
					location1.setLongitude(comment1.getaLocation()[0]);
				} else {
					location1.setLatitude(0);
					location1.setLongitude(0);
				}

				Location location2 = new Location("Location");
				if (comment1.getaLocation() != null) {
					location2.setLatitude(comment2.getaLocation()[1]);
					location2.setLongitude(comment2.getaLocation()[0]);
				} else {
					location2.setLatitude(0);
					location2.setLongitude(0);
				}
				
				double difference = (location2.distanceTo(currentLocation) - location1.distanceTo(currentLocation));
				if(difference < 0)
					difference = Math.floor(difference);
				else if(difference > 0)
					difference = Math.ceil(difference);
				return (int) difference;
			}
		});
		for (Commentor c: topLevelList) {
			if (!proxiMe.contains(c)) {
				proxiMe.add(c);
			}
		}
		this.adapter.notifyDataSetChanged();
	}

	/**
	 * sort comments by proximity to another location
	 */
	public void updateProxiLoc() {
		// TODO Auto-generated method stub
		Collections.sort(topLevelList, new Comparator<Commentor>() {
			public int compare(Commentor comment1, Commentor comment2) {
				Location currentLocation = new Location("Location");
				currentLocation.setLatitude(main.modifiedLocation[1]);
				currentLocation.setLongitude(main.modifiedLocation[0]);

				Location location1 = new Location("Location");
				if (comment1.getaLocation() != null) {
					location1.setLatitude(comment1.getaLocation()[1]);
					location1.setLongitude(comment1.getaLocation()[0]);
				} else {
					location1.setLatitude(0);
					location1.setLongitude(0);
				}

				Location location2 = new Location("Location");
				if (comment1.getaLocation() != null) {
					location2.setLatitude(comment2.getaLocation()[1]);
					location2.setLongitude(comment2.getaLocation()[0]);
				} else {
					location2.setLatitude(0);
					location2.setLongitude(0);
				}

				double difference = (location2.distanceTo(currentLocation) - location1.distanceTo(currentLocation));
				if(difference < 0)
					difference = Math.floor(difference);
				else if(difference > 0)
					difference = Math.ceil(difference);
				return (int) difference;
			}
		});
		for (Commentor c: topLevelList) {
			if (!proxiLoc.contains(c)) {
				proxiLoc.add(c);
			}
		}
		this.adapter.notifyDataSetChanged();
	}
	
}

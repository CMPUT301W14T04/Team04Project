package com.example.geocomment.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.geocomment.GeoCommentActivity;
import com.example.geocomment.elasticsearch.ElasticSearchOperations;

public class TopLevelList {

	private List<Commentor> topLevelList;
	private ArrayAdapter<Commentor> adapter;
	private List<Commentor> favourite;
	private List<Commentor> picture;
	private List<Commentor> score;
	private List<Commentor> proxiMe;
	private List<Commentor> proxiLoc;
	private List<Commentor> nonPicture;
	private List<Commentor> dateList;

	public TopLevelList() {
		this.topLevelList = new ArrayList<Commentor>();
		this.favourite = new ArrayList<Commentor>();
		this.picture = new ArrayList<Commentor>();
		this.nonPicture = new ArrayList<Commentor>();
		this.score = new ArrayList<Commentor>();
		this.proxiMe = new ArrayList<Commentor>();
		this.proxiLoc = new ArrayList<Commentor>();
		this.dateList = new ArrayList<Commentor>();
	}

<<<<<<< HEAD
=======

>>>>>>> e67df05ed45982a7412e59f25c30ecb9422f25b3
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
<<<<<<< HEAD
		if (type == 1)
			ElasticSearchOperations.pushComment(comment, 1);
=======
		if(type==1)
			ElasticSearchOperations.pushComment(comment,1);
>>>>>>> e67df05ed45982a7412e59f25c30ecb9422f25b3
		else
			ElasticSearchOperations.pushComment(comment, 2);
		this.adapter.notifyDataSetChanged();
	}

	/**
	 * add a top level comment to top level comment collection
	 * 
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
<<<<<<< HEAD

=======
	
	public List<Commentor> getDateList() {
		return Collections.unmodifiableList(dateList);
	}
	
>>>>>>> e67df05ed45982a7412e59f25c30ecb9422f25b3
	public List<Commentor> getFavList() {
		return Collections.unmodifiableList(favourite);
	}

	public List<Commentor> getPictureList() {
		// TODO Auto-generated method stub
		return Collections.unmodifiableList(picture);
	}

	public List<Commentor> getScoreList() {
		// TODO Auto-generated method stub
		return Collections.unmodifiableList(score);
	}

	public List<Commentor> getProxiMeList() {
		// TODO Auto-generated method stub
		return Collections.unmodifiableList(proxiMe);
	}

	public List<Commentor> getProxiLocList() {
		// TODO Auto-generated method stub
		return Collections.unmodifiableList(proxiLoc);
	}

<<<<<<< HEAD
	public static Comparator<Commentor> dateCompare = new Comparator<Commentor>() {
		public int compare(Commentor comment1, Commentor comment2) {
			Calendar date1 = comment1.getDate();
			Calendar date2 = comment2.getDate();
			return date1.compareTo(date2);
		}
	};

=======
>>>>>>> e67df05ed45982a7412e59f25c30ecb9422f25b3
	public void setAdapter(ArrayAdapter<Commentor> adapter) {
		this.adapter = adapter;
	}

	public Commentor getComment(int i) {
		return topLevelList.get(i);
	}
<<<<<<< HEAD

	public void updatePicture() {
		// Comparator<Commentor> compare = dateCompare;
		for (Commentor c : topLevelList) {
=======
	
	public void updateDate() {
		// TODO Auto-generated method stub
		
	}

	public void update(){
		for (Commentor c:topLevelList){
			if (c.isFavourite()==true){
				if(!favourite.contains(c)){
					favourite.add(c);
				}
			}
		}
		this.adapter.notifyDataSetChanged();
	}

	public void updatePicture() {
		for (Commentor c: topLevelList) {
>>>>>>> e67df05ed45982a7412e59f25c30ecb9422f25b3
			if (c.getaPicture() != null & picture.contains(c) == false) {
				picture.add(c);
			} else if (c.getaPicture() == null
					& nonPicture.contains(c) == false) {
				nonPicture.add(c);
			}
		}
		if (picture.contains(nonPicture.get(0)) == false) {
			picture.addAll(nonPicture);
		}
		this.adapter.notifyDataSetChanged();
	}

	public void updateSocre() {
		// TODO Auto-generated method stub
		Collections.sort(topLevelList, new Comparator<Commentor>() {
			public int compare(Commentor comment1, Commentor comment2) {
				return comment2.getDate().compareTo(comment1.getDate());
			}
		});
		for (Commentor c: topLevelList) {
			if (!score.contains(c)) {
				score.add(c);
			}
		}
		this.adapter.notifyDataSetChanged();
	}

	public void updateProxiMe() {
		// TODO Auto-generated method stub

	}

<<<<<<< HEAD
	public void setFavourite(List<Commentor> favourite) {
		this.favourite = favourite;
	}

	public void updateFav() {
		for (Commentor c : topLevelList) {
			if (c.isFavourite() == true) {
				if (!favourite.contains(c)) {
					favourite.add(c);
				}
			} else {
				if (favourite.contains(c)) {
					favourite.remove(c);
				}
			}
		}

	}

	public void addFav(Commentor c) {
		favourite.add(c);
	}
=======

	public void setFavourite(List<Commentor> favourite) {
		this.favourite = favourite;
	}
>>>>>>> e67df05ed45982a7412e59f25c30ecb9422f25b3

}

package com.example.geocomment.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.example.geocomment.elasticsearch.ElasticSearchOperations;

import android.widget.ArrayAdapter;

public class TopLevelList {

	private List<TopLevel> topLevelList;
	private ArrayAdapter<TopLevel> adapter;

	public TopLevelList() {
		this.topLevelList = new ArrayList<TopLevel>();
	}

	public void AddTopLevel(TopLevel topLevel) {
		this.topLevelList.add(0,topLevel);
		ElasticSearchOperations.pushComment(topLevel);
		this.adapter.notifyDataSetChanged();
	}

	public void addTopLevelCollection(Collection<TopLevel> pots) {
		this.topLevelList.addAll(pots);
		this.adapter.notifyDataSetChanged();
	}

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

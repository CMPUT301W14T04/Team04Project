package com.example.geocomment.elasticsearch;

import java.util.Collection;

/**
 * Represents part of a response from ElasticSearch.
 * Taken from https://github.com/rayzhangcl/ESDemo
 */
public class Hits<T> {
    int total;
    double max_score;
    Collection<ElasticSearchResponse<T>> hits;
    public Collection<ElasticSearchResponse<T>> getHits() {
        return hits;
    }
    @Override
	public String toString() {
        return (super.toString()+","+total+","+max_score+","+hits);
    }
}
/*
Copyright (c) 2013, Guillermo Ramirez, Nadine Yushko, Tarek El Bohtimy, Yang Wang
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright notice,
this list of conditions and the following disclaimer in the documentation
and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

The views and conclusions contained in the software and documentation are those
of the authors and should not be interpreted as representing official policies,
either expressed or implied, of the FreeBSD Project.
*/

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

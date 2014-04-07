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

package com.example.geocomment;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.geocomment.elasticsearch.ElasticSearchOperations;
import com.example.geocomment.model.Commentor;
import com.example.geocomment.model.TopLevel;
import com.example.geocomment.util.Format;

/**
 * 
 * this class sets a custom adapter to the lisvtiew base on the list of comments
 * 
 * @author CMPUT 301 Team 04
 * 
 */
public class CommentAdapter extends ArrayAdapter<Commentor> {

	// private byte[] decodedString = null;
	// private Bitmap decodedByte = null;
	Context mContext;

	public CommentAdapter(Context context, int resource, List<Commentor> list) {
		super(context, resource, list);
		// TODO Auto-generated constructor stub
		mContext = context;
	}

	@Override
	/**
	 * This part creates the main view.
	 * If the user decided to add a picture, the picture is 
	 * converted to a bitmap so it can be added to the server 
	 * with the rest of the comment information.
	 */
	public View getView(int position, View convertView, final ViewGroup parent) {

		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(this.getContext());
			convertView = inflater.inflate(R.layout.comment_row, null);
		}

		final Commentor comment_meta = this.getItem(position);

		/*
		 * This creates a link to the favourite button and implements an on
		 * clicklistner
		 */
		final ImageButton image = (ImageButton) convertView
				.findViewById(R.id.imageButton1);
		image.setFocusable(false);
		if (comment_meta.isFavourite() == true) {
			image.setImageResource(R.drawable.ic_favheart);
		}
		image.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (comment_meta.isFavourite() == false) {
					comment_meta.setFavourite(true);
					image.setImageResource(R.drawable.ic_favheart);
				} else {
					comment_meta.setFavourite(false);
					image.setImageResource(R.drawable.ic_heart);
				}
			}
		});

		if (comment_meta != null) {
			TextView username = (TextView) convertView
					.findViewById(R.id.tvtitle);
			if (username != null)
				username.setText(comment_meta.getUserName());

			TextView comment = (TextView) convertView
					.findViewById(R.id.comment);
			if (comment != null)
				comment.setText(comment_meta.getTextComment());

			/** Set comment id into a hidden textview BEGIN **/
			TextView commentId = (TextView) convertView
					.findViewById(R.id.commentId);
			if (commentId != null) {
				commentId.setText(comment_meta.getID());
			}
			/** Set comment id into a hidden textview ENDs **/

			ImageView picImageView = (ImageView) convertView
					.findViewById(R.id.pic_image_view);
			if (picImageView != null) {
				picImageView.setImageBitmap(comment_meta.getaPicture());
			}

			TextView date_added = (TextView) convertView
					.findViewById(R.id.date_added);
			if (date_added != null)
				date_added.setText(Format.dateFormat(comment_meta.getDate()));

			if (comment_meta.isFavourite() == false) {
				image.setImageResource(R.drawable.ic_heart);
			} else {
				image.setImageResource(R.drawable.ic_favheart);
			}

			Button likes = (Button) convertView
					.findViewById(R.id.likes_button);
			//test
			if (likes != null){
				likes.setText("Likes: " + Integer.toString(comment_meta.getLikes()));
				likes.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Button likes_button = (Button)v;
						int current_likes = comment_meta.getLikes();
						comment_meta.setLikes(current_likes + 1);
						likes_button.setText("Likes: " + comment_meta.getLikes());
						ElasticSearchOperations.pushComment(comment_meta, 3);
					}
				});
			}
		}

		return convertView;

	}
}

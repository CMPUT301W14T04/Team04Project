package com.example.utils;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.geoloc.CommentMetaData;
import com.example.geoloc.R;

public class ListCommentAdapter extends ArrayAdapter<CommentMetaData> {

	public ListCommentAdapter(Context context, int resourse,
			List<CommentMetaData> model) {
		// TODO Auto-generated constructor stub
		super(context, resourse, model);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(this.getContext());
			convertView = inflater.inflate(R.layout.comment_row, null);
		}

		CommentMetaData comment_meta = this.getItem(position);
		if (comment_meta != null) {

			// ImageView picImageView =
			// (ImageView)convertView.findViewById(R.id.pic_image_view);
			// if (picImageView != null)
			// picImageView.setImageBitmap(picPostModel.getPicture());

			TextView comment = (TextView) convertView
					.findViewById(R.id.comment);
			if (comment != null)
				comment.setText(comment_meta.getComment());

			TextView date_added = (TextView) convertView
					.findViewById(R.id.date_added);
			if (date_added != null)
				date_added.setText(comment_meta.getDate());
		}

		return convertView;

	}
}

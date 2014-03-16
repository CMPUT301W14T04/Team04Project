package com.example.geocomment;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.geocomment.model.TopLevel;
import com.example.geocomment.util.Format;

public class CommentAdapter extends ArrayAdapter<TopLevel> {

	private ImageView picImageView = null;
	private byte[] decodedString = null;
	private Bitmap decodedByte = null;

	public CommentAdapter(Context context, int resource, List<TopLevel> list) {
		super(context, resource, list);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(this.getContext());
			convertView = inflater.inflate(R.layout.comment_row, null);
		}

		TopLevel comment_meta = this.getItem(position);
		if (comment_meta != null) {

			picImageView  = (ImageView) convertView.findViewById(R.id.pic_image_view);
			if (comment_meta.aPicture != null) {
				decodedString = Base64.decode(comment_meta.aPicture, Base64.DEFAULT);
				decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
				picImageView.setImageBitmap(decodedByte);
			}

			TextView comment = (TextView) convertView
					.findViewById(R.id.comment);
			if (comment != null)
				comment.setText(comment_meta.getTextComment());

			TextView date_added = (TextView) convertView
					.findViewById(R.id.date_added);
			if (date_added != null)
				date_added.setText(Format.dateFormat(comment_meta.getDate()));

			TextView username = (TextView) convertView
					.findViewById(R.id.tvtitle);
			if(username!=null)
				username.setText(comment_meta.getUserName());
		}

		return convertView;

	}
}

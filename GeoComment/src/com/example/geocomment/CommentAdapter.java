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

import com.example.geocomment.model.Commentor;
import com.example.geocomment.util.Format;

public class CommentAdapter extends ArrayAdapter<Commentor> {

	private byte[] decodedString = null;
	private Bitmap decodedByte = null;

	public CommentAdapter(Context context, int resource, List<Commentor> list) {
		super(context, resource, list);
		// TODO Auto-generated constructor stub
	}

	@Override
	/**
	 * This part creates the main view.
	 * If the user decided to add a picture, the picture is 
	 * converted to a bitmap so it can be added to the server 
	 * with the rest of the comment information.
	 */
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(this.getContext());
			convertView = inflater.inflate(R.layout.comment_row, null);
		}

		Commentor comment_meta = this.getItem(position);
		if (comment_meta != null) {


			ImageView picImageView  = (ImageView) convertView
					.findViewById(R.id.pic_image_view);
			if (comment_meta.getaPicture() != null) {
				decodedString = Base64.decode(comment_meta.getaPicture(), Base64.DEFAULT);
//				String base64Encoded = comment_meta.getaPicture().getAsJsonPrimitive().getAsString();
//				decodedString = Base64.decode(base64Encoded, Base64.NO_WRAP);
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

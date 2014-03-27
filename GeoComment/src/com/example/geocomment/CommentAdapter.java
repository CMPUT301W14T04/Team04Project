package com.example.geocomment;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geocomment.model.Commentor;
import com.example.geocomment.model.TopLevelList;
import com.example.geocomment.util.Format;

/**
 * 
 * this class set a custom adapter to the lisvtiew base on the list of comments
 * @author CMPUT 301 Team 04
 *
 */
public class CommentAdapter extends ArrayAdapter<Commentor> {

//	private byte[] decodedString = null;
//	private Bitmap decodedByte = null;

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
	public View getView(int position, View convertView, final ViewGroup parent) {

		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(this.getContext());
			convertView = inflater.inflate(R.layout.comment_row, null);
		}

		final Commentor comment_meta = this.getItem(position);
		
		/*
		 * This creates a link to the favourite button and implements 
		 * an on clicklistner
		 */
		final ImageButton image = (ImageButton)convertView.findViewById(R.id.imageButton1);
		image.setFocusable(false);
		image.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (comment_meta.isFavourite()==false){
					comment_meta.setFavourite(true);
					image.setImageResource(R.drawable.ic_favheart);
					//top.addFav(comment_meta);
				}
				else{
					comment_meta.setFavourite(false);
					image.setImageResource(R.drawable.ic_heart);
				}
				Toast.makeText(parent.getContext(), comment_meta.getTextComment()+" "+comment_meta.isFavourite() , Toast.LENGTH_SHORT).show();
				//DELETE TOAST
			}
		});
		if (comment_meta != null) {
			ImageView picImageView  = (ImageView) convertView
					.findViewById(R.id.pic_image_view);
			if (picImageView != null) {

				picImageView.setImageBitmap(comment_meta.getaPicture());
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
			if (comment_meta.isFavourite()==false){
				image.setImageResource(R.drawable.ic_heart);
			}
			else{
				image.setImageResource(R.drawable.ic_favheart);
			}
		}

		return convertView;

	}

}

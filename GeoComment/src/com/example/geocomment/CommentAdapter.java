package com.example.geocomment;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geocomment.model.Comment;
import com.example.geocomment.model.TopLevel;
import com.example.geocomment.model.TopLevelList;
import com.example.geocomment.util.Format;


public class CommentAdapter extends ArrayAdapter<TopLevel> {

	private ImageView picImageView = null;
	private byte[] decodedString = null;
	private Bitmap decodedByte = null;
	
	public CommentAdapter(Context context, int resource, List<TopLevel> list) {
		super(context, resource, list);
		// TODO Auto-generated constructor stub
	}
	
	@SuppressLint("ShowToast")
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
		
		final TopLevel comment_meta = this.getItem(position);
	
		//BUTTON WORKS BY ITSELF SOMETIMES AND ITS REALLY STRANGE
		//ONLY TOP LEVELS
		final ImageButton image = (ImageButton)convertView.findViewById(R.id.imageButton1);
		image.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				//if (comment_meta.isFavourite()==false){
					//image.setBackgroundColor(Color.RED);
					//Comment.setFavourite(true);
					//TopLevelList.AddFavourite(comment_meta);
				//}
				//else{
					//image.setBackgroundColor(Color.GRAY);
					//Comment.setFavourite(false);
					//TopLevelList.RemoveFavourite(comment_meta);

				//}
				Toast.makeText(parent.getContext(), comment_meta.getTextComment()+" "+comment_meta.isFavourite() , Toast.LENGTH_SHORT).show();

			}

		});
		
		
		
		
		if (comment_meta != null) {
			
			//TAREK
			/*if(comment_meta.isFavourite()==true){
				image.setBackgroundColor(Color.RED);
			}
			else if (comment_meta.isFavourite()==false){
				image.setBackgroundColor(Color.GRAY);
			}*/
			//TAREK
			
			
			
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

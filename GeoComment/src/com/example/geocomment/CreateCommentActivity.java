package com.example.geocomment;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.geocomment.model.LocationList;
import com.example.geocomment.model.Reply;
import com.example.geocomment.model.TopLevel;
import com.example.geocomment.model.User;
import com.example.geocomment.util.Resource;

/**
 * This class creates the comment activity for the application.
 * Each comment has text, username, reply, possibly a picture, and 
 * the location associated with it.
 */
public class CreateCommentActivity extends Activity {

	Button pushButton;
	EditText textComment;

	TopLevel topLevel;
	Reply reply;
	User user;
	LocationList locationList;
	Location location;

	int type;

	protected static final int CAMERA_REQUEST = 0;
	protected static final int GALLARY_REQUEST = 1;
	private ImageView imageView = null;
	private Bitmap photo = null;
	String encodedImage = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_comment);
		/**
		 *  Show the Up button in the action bar.
		 */
		setupActionBar();

		pushButton = (Button) findViewById(R.id.submit_comment);
		textComment = (EditText) findViewById(R.id.comment_box);

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();

		user = (User) bundle.getParcelable(Resource.USER_INFO);
		locationList = (LocationList) bundle
				.getParcelable(Resource.USER_LOCATION_HISTORY);
		type = bundle.getInt(Resource.TOP_LEVEL_COMMENT);

		imageView = (ImageView)findViewById(R.id.imageView1);
		imageView.setImageBitmap(null);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		/**
		 * Inflate the menu; this adds items to the action bar if it is present.
		 */
		getMenuInflater().inflate(R.menu.create_comment, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			/**
			 * This ID represents the Home or Up button. In the case of this
			 * activity, the Up button is shown. Use NavUtils to allow users
			 * to navigate up one level in the application structure. For
			 * more details, see the Navigation pattern on Android Design:
			 * http://developer.android.com/design/patterns/navigation.html#up-vs-back
			 *http://developer.android.com/design/patterns/navigation.html#up-vs-back
			*/
			NavUtils.navigateUpFromSameTask(this);
			return true;			
		}
		if (item.getItemId() == R.id.action_new_picture) {
			dialog();
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * dialog pops up after clicking action_new_picture icon in action bar opens camera or gallery
	 */
	public void dialog() {
		AlertDialog.Builder builder = new Builder(CreateCommentActivity.this);
		builder.setTitle("Attach a picture from:");
		builder.setPositiveButton("Camera", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
				startActivityForResult(cameraIntent, CAMERA_REQUEST); 
			}
		});

		builder.setNeutralButton("Gallary", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(intent, GALLARY_REQUEST);
			}
		});

		builder.create().show();
	}

	@SuppressWarnings("static-access")
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
			photo = (Bitmap)data.getExtras().get("data");
			//resize chosen photo
			photo = photo.createScaledBitmap(photo, 600, 600, false);
			//set photo for preview
			imageView.setImageBitmap(photo);
			getStringFromBitmap(photo);
		}
		if (requestCode == GALLARY_REQUEST && resultCode == RESULT_OK) {  
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };
			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();
			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();
			photo = (BitmapFactory.decodeFile(picturePath));
			//set photo for preview
			imageView.setImageBitmap(photo);
			getStringFromBitmap(photo);
		}  
	}

	/**
	 * this function encodes the photo to string so the picture
	 * can be stored on the server with the rest of the comment 
	 * information, and so it can later be retrieved by all users
	 * of the application
	 * @param photo
	 * @return
	 */
	public String getStringFromBitmap(Bitmap photo) {
		final int COMPRESSION_QUALITY = 100;
		ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
		photo.compress(Bitmap.CompressFormat.PNG, COMPRESSION_QUALITY, byteArrayBitmapStream);
		byte[] b = byteArrayBitmapStream.toByteArray();
		encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
		return encodedImage;
	}

	/**
	 * submitComment puts the comment into a top level array so it
	 * can later be uploaded to the server. If the comment that the 
	 * user is trying to add is empty, the user will get an error and
	 * will be prompted to type something in the comment box. 
	 * If the user enters a comment, the comment (and possibly the picture)
	 * will get all the 
	 * associated metadata to go with it automatically, such as the 
	 * date, the ID, and the location.
	 * @param view
	 */
	public void submitComment(View view) {
		if (type == Resource.TYPE_TOP_LEVEL) {
			Calendar timeStamp = Calendar.getInstance();
			String text = textComment.getText().toString();
			String ID = Resource.generateID();
			double[] location = user.getUserLocation();
			reply = new Reply(user, timeStamp, encodedImage, "hollalala", location);
			List<Reply> replies = new ArrayList<Reply>();
			replies.add(reply);

			if (text.isEmpty()) {
				Toast.makeText(this, "You can't submit an empty text",
						Toast.LENGTH_LONG).show();
			} else {

				topLevel = new TopLevel(user, timeStamp, encodedImage, text,
						location, ID);
				topLevel.setReplies(replies);

				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putParcelable(Resource.TOP_LEVEL_COMMENT, topLevel);
				intent.putExtras(bundle);
				setResult(Resource.RESQUEST_NEW_TOP_LEVEL, intent);
				Log.e("Comment ID:", topLevel.getID());

				finish();
			}

		}

	}
}

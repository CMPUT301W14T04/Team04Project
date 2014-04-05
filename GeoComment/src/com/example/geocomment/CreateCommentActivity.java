package com.example.geocomment;

import java.util.Calendar;

import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geocomment.model.Commentor;
import com.example.geocomment.model.LocationList;
import com.example.geocomment.model.Reply;
import com.example.geocomment.model.TopLevel;
import com.example.geocomment.model.User;
import com.example.geocomment.util.Resource;

/**
 * This class creates the comment activity for the application. Each comment has
 * text, username, reply, possibly a picture, and the location associated with
 * it.
 * 
 * @author CMPUT 301 Team 04
 */
public class CreateCommentActivity extends Activity {

	final Context context = this;
	Button pushButton;
	EditText textComment;

	Commentor Comment;
	Reply reply;
	User user;
	LocationList locationList;
	Location location;
	String parentID;
	double[] locationComment;

	int type;
	int likes = 0;

	protected static final int CAMERA_REQUEST = 0;
	protected static final int GALLARY_REQUEST = 1;
	// private static final int MAX_BITMAP_DIMENSIONS = 50;
	private ImageView imageView = null;
	Bitmap photo = null;

	// String encodedImage = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_comment);
		/**
		 * Show the Up button in the action bar.
		 */
		setupActionBar();

		locationComment = null;
		pushButton = (Button) findViewById(R.id.submit_comment);
		textComment = (EditText) findViewById(R.id.comment_box);

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();

		user = (User) bundle.getParcelable(Resource.USER_INFO);
		locationList = (LocationList) bundle
				.getParcelable(Resource.USER_LOCATION_HISTORY);
		type = bundle.getInt(Resource.TOP_LEVEL_COMMENT);
		parentID = bundle.getString("parentID");

		imageView = (ImageView) findViewById(R.id.imageView1);
		imageView.setImageBitmap(null);
		locationComment = user.getUserLocation();
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
			 * activity, the Up button is shown. Use NavUtils to allow users to
			 * navigate up one level in the application structure. For more
			 * details, see the Navigation pattern on Android Design:
			 * http://developer
			 * .android.com/design/patterns/navigation.html#up-vs-back
			 * http://developer
			 * .android.com/design/patterns/navigation.html#up-vs-back
			 */
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.location_change:
			openDialogLocation();

		}
		if (item.getItemId() == R.id.action_new_picture) {
			Intent cameraIntent = new Intent(
					android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(cameraIntent, CAMERA_REQUEST);
		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * dialog pops up after clicking action_new_picture icon in action bar opens
	 * camera or gallery
	 */

	@Override
	@SuppressWarnings("static-access")
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
			photo = (Bitmap) data.getExtras().get("data");
			// resize chosen photo
			photo = Bitmap.createScaledBitmap(photo, 100, 100, false);
			// set photo for preview
			imageView.setImageBitmap(photo);
		}
	}

	/**
	 * this function encodes the photo to string so the picture can be stored on
	 * the server with the rest of the comment information, and so it can later
	 * be retrieved by all users of the application
	 * 
	 * @param photo
	 * @return
	 */
	// public JsonElement getStringFromBitmap(Bitmap photo) {
	// final int COMPRESSION_QUALITY = 100;
	// ByteArrayOutputStream byteArrayBitmapStream = new
	// ByteArrayOutputStream();
	// photo.compress(Bitmap.CompressFormat.PNG, COMPRESSION_QUALITY,
	// byteArrayBitmapStream);
	// byte[] b = byteArrayBitmapStream.toByteArray();
	// encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
	// return new JsonPrimitive(encodedImage);
	// }

	/**
	 * submitComment puts the comment into a top level array so it can later be
	 * uploaded to the server. If the comment that the user is trying to add is
	 * empty, the user will get an error and will be prompted to type something
	 * in the comment box. If the user enters a comment, the comment (and
	 * possibly the picture) will get all the associated metadata to go with it
	 * automatically, such as the date, the ID, and the location.
	 * 
	 * @param view
	 */
	public void submitComment(View view) {
		if (type == Resource.TYPE_TOP_LEVEL) {
			Calendar timeStamp = Calendar.getInstance();
			String text = textComment.getText().toString();
			String ID = Resource.generateID();
			double[] location = user.getUserLocation();

			if (text.isEmpty()) {
				Toast.makeText(this, "You can't submit an empty text",
						Toast.LENGTH_LONG).show();
			} else {
				Comment = new TopLevel(user, timeStamp, photo, text, location,
						ID, likes);
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putParcelable(Resource.TOP_LEVEL_COMMENT,
						(TopLevel) Comment);
				intent.putExtras(bundle);
				setResult(Resource.RESQUEST_NEW_TOP_LEVEL, intent);

				finish();
			}

		}

		if (type == Resource.TYPE_REPLY) {
			Calendar timeStamp = Calendar.getInstance();
			String text = textComment.getText().toString();
			String ID = Resource.generateID();
			double[] location = user.getUserLocation();

			if (text.isEmpty()) {
				Toast.makeText(this, "You can't submit an empty text",
						Toast.LENGTH_LONG).show();
			} else {

				Comment = new Reply(user, timeStamp, photo, text, location,
						parentID, ID, likes);

				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putParcelable(Resource.TOP_LEVEL_COMMENT,
						(Reply) Comment);
				intent.putExtras(bundle);
				setResult(100, intent);

				finish();
			}

		}

	}

	private void openDialogLocation() {
		LayoutInflater li = LayoutInflater.from(context);
		View locationChange = li.inflate(R.layout.location_dialog, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
		alertDialogBuilder.setTitle("Change Location");
		alertDialogBuilder.setView(locationChange);

		final EditText editLat = (EditText) locationChange
				.findViewById(R.id.LatitudeChange);
		final EditText editLog = (EditText) locationChange
				.findViewById(R.id.longitudeChange);

		alertDialogBuilder.setCancelable(false);
		alertDialogBuilder.setNeutralButton("Change", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				String stringLat = editLat.getText().toString();
				String stringLong = editLog.getText().toString();
				if(!stringLat.isEmpty() && !stringLong.isEmpty()){
				double lat = Double.parseDouble(stringLat);
				double log = Double.parseDouble(stringLong);

					locationComment[0] = log;
					locationComment[1] = lat;
					Log.e("Change Latitude", ""+locationComment[0] + "-" + locationComment[1]);}
				else
					Toast.makeText(getApplicationContext(), "error",
							Toast.LENGTH_SHORT).show();	

			}
		});

		alertDialogBuilder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();

	}

}

package com.example.geocomment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.geocomment.elasticsearch.ElasticSearchOpertationUser;
import com.example.geocomment.model.UserProfile;
import com.google.gson.Gson;

public class EditProfile extends Activity {
	protected static final int CAMERA_REQUEST = 0;

	UserProfile profile;
	Bitmap photo;

	ImageView pic;
	Button editPic;
	Button editBio;
	Button editQuote;
	Button editContact;
	Button ok;
	Button cancel;
	
	Gson gson;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_profile);

		editPic = (Button) findViewById(R.id.editChangePic);
		editBio = (Button) findViewById(R.id.editNewBio);
		editQuote = (Button) findViewById(R.id.editQuote);
		editContact = (Button) findViewById(R.id.editContact);
		ok = (Button) findViewById(R.id.editAccept);
		cancel = (Button) findViewById(R.id.editCancel);
		pic = (ImageView) findViewById(R.id.editProfilePic);

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();

		profile = bundle.getParcelable("edit profile");

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_profile, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void editPic(View view) {
		Intent cameraIntent = new Intent(
				android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(cameraIntent, CAMERA_REQUEST);
	}

	public void editBio(View view) {
		LayoutInflater li = LayoutInflater.from(this);
		View locationChange = li.inflate(R.layout.text_dialog, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setTitle("Change Bio");
		alertDialogBuilder.setView(locationChange);

		final EditText bio = (EditText) locationChange
				.findViewById(R.id.editText);
		if (profile.getBiography()!=null)
			bio.setText(profile.getBiography());
		else
			bio.setHint(profile.getUsername());

		alertDialogBuilder.setCancelable(false);
		alertDialogBuilder.setNeutralButton("Change", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				String newbio = bio.getText().toString();
				if (!newbio.isEmpty()) {
					profile.setBiography(newbio);
					Toast.makeText(getApplicationContext(), "New Bio",
							Toast.LENGTH_SHORT).show();
				} else
					Toast.makeText(getApplicationContext(), "Error",
							Toast.LENGTH_SHORT).show();

			}

		});
		alertDialogBuilder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();

	}

//	public void editContact(View view) {
//		LayoutInflater li = LayoutInflater.from(this);
//		View locationChange = li.inflate(R.layout.text_dialog, null);
//
//		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//		alertDialogBuilder.setTitle("Add contact info");
//		alertDialogBuilder.setView(locationChange);
//
//		final EditText bio = (EditText) locationChange
//				.findViewById(R.id.editText);
//		bio.setInputType(InputType.TYPE_CLASS_TEXT
//				| InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
//				| InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
//		bio.setHint("New contact info");
//
//		alertDialogBuilder.setCancelable(false);
//		alertDialogBuilder.setNeutralButton("Add", new OnClickListener() {
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				String newbio = bio.getText().toString();
//				if (!newbio.isEmpty()) {
//					profile.addSocial(newbio);
//					Toast.makeText(getApplicationContext(), "New Social added",
//							Toast.LENGTH_SHORT).show();
//				} else
//					Toast.makeText(getApplicationContext(), "Error",
//							Toast.LENGTH_SHORT).show();
//
//			}
//
//		});
//		alertDialogBuilder.setNegativeButton("Cancel",
//				new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface dialog, int id) {
//						dialog.cancel();
//					}
//				});
//		// create alert dialog
//		AlertDialog alertDialog = alertDialogBuilder.create();
//
//		// show it
//		alertDialog.show();
//	}
	
	public void editQuote (View view)
	{
		
		LayoutInflater li = LayoutInflater.from(this);
		View locationChange = li.inflate(R.layout.text_dialog, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setTitle("Edit Quote");
		alertDialogBuilder.setView(locationChange);

		final EditText bio = (EditText) locationChange
				.findViewById(R.id.editText);
		bio.setHint("New Quote");

		alertDialogBuilder.setCancelable(false);
		alertDialogBuilder.setNeutralButton("Add", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				String newbio = bio.getText().toString();
				if (!newbio.isEmpty()) {
					profile.setQuote(newbio);
					Toast.makeText(getApplicationContext(), "New Quote",
							Toast.LENGTH_SHORT).show();
				} else
					Toast.makeText(getApplicationContext(), "Error",
							Toast.LENGTH_SHORT).show();

			}

		});
		alertDialogBuilder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
		
	}
	
	public void editOk(View view)
	{
		
//		ElasticSearchOpertationUser.pushUserProfile(profile);
		Intent intent = new Intent();
		intent.putExtra("profile1", profile);
		setResult(1, intent);
		finish();
		
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
			Toast.makeText(getApplicationContext(), "Pic taken",
					Toast.LENGTH_SHORT).show();
			photo = (Bitmap) data.getExtras().getParcelable("data");
			// resize chosen photo
			photo = Bitmap.createScaledBitmap(photo, 100, 100, false);
			// set photo for preview
			// pic.setImageBitmap(photo);
			profile.setPic(photo);
			if (pic != null)
				pic.setImageBitmap(profile.getPic());

			if (pic == null) {
				Log.e("It is null", "null");
			}
		}
	}

}

package com.example.geocomment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.geocomment.model.UserProfile;

/**
 * Option Activity method opens up the options menu in the application. It gets
 * the username and stores that username on your phone so the user does not have
 * to enter a username everytime said user wants to makes either a top level
 * comment or a reply comment.
 * 
 * @author CMPUT 301 Team 04
 */
public class OptionActivity extends Activity implements OnItemClickListener {

	private final int CHANGE_USERNAME_OPTION = 0;
	private final int VIEW_PROFILE = 1;
	private final int EDIT_PROFILE = 2;
	
	String username;
	UserProfile profile;

	List<String> options;
	ListView settings;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_settings);
		// Show the Up button in the action bar.
		setupActionBar();

		options = new ArrayList<String>();
		options.add("Change Usernae");
		options.add("View profile");
		options.add("Edit profile");

		settings = (ListView) findViewById(R.id.ListViewSettings);
		settings.setAdapter(new ArrayAdapter(this,
				android.R.layout.simple_list_item_1, options));

		settings.setOnItemClickListener(this);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		profile = (UserProfile) bundle.getParcelable("Profile");
		
		Toast.makeText(this, profile.getUsername(), Toast.LENGTH_SHORT).show();
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.option, menu);
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
			 */
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Button that takes the Users new user name and sets it in the users phone
	 * so the user does not have to enter in a username every time said user
	 * wants to make a comment
	 */
	public void setUsername(View view) {
		EditText text = (EditText) findViewById(R.id.editText1);
		String username = text.getText().toString();
		this.username = username;
		Toast.makeText(OptionActivity.this, "New Username made",
				Toast.LENGTH_SHORT).show();
		Intent intent = new Intent();
		intent.putExtra("setUsername", username);
		setResult(90, intent);
		finish();
	}

	/**
	 * pressing this button makes the edit text visible for the user to make a
	 * new user name.
	 */
	public void changeUser(View view) {
		View edit = findViewById(R.id.editText1);
		edit.setVisibility(View.VISIBLE);
		View button = findViewById(R.id.button1);
		button.setVisibility(View.VISIBLE);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		switch (position) {
		case CHANGE_USERNAME_OPTION:
			openDialogChangeUsername();
			break;
		case VIEW_PROFILE:
			openProfile();
			break;
		case EDIT_PROFILE:
			break;
		default:
			Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();

		}

	}

	private void openDialogChangeUsername() {
		LayoutInflater li = LayoutInflater.from(this);
		View locationChange = li.inflate(R.layout.change_username, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setTitle("Change username");
		alertDialogBuilder.setView(locationChange);

		final EditText newusername = (EditText) locationChange
				.findViewById(R.id.newUsernameSettings);

		alertDialogBuilder.setCancelable(false);
		alertDialogBuilder.setNeutralButton("Change", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				String stringuser = newusername.getText().toString();
				if (!stringuser.isEmpty()) {
					profile.setUsername(stringuser);
					Toast.makeText(OptionActivity.this, "New Username made",
							Toast.LENGTH_SHORT).show();
					Intent intent = new Intent();
					Bundle bundle = new Bundle();
					bundle.putParcelable("profile", profile);
					intent.putExtras(bundle);
					setResult(90, intent);
					finish();
				} else
					Toast.makeText(getApplicationContext(), "error",
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
	
	private void openProfile(){
		
		Intent intent = new Intent(OptionActivity.this,ProfileActivity.class); 
		Bundle bundle = new Bundle();
	
		bundle.putParcelable("profile", profile);
		bundle.putBoolean("online?", false);
		
		intent.putExtras(bundle);
		
		startActivity(intent);
		
	}
}

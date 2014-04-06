package com.example.geocomment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class AlertDialogSettings extends AlertDialog {

	Context context;
	String userName;
	protected AlertDialogSettings(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context=context;
	}
	
	public String optionSelected (int position, String userName)
	{
		switch(position)
		{
		case 0:
			changeUsername(userName);
			break;
		}
		
		return userName;
	}
	
	private String changeUsername(String user)
	{
		userName = user;
		LayoutInflater li = LayoutInflater.from(context);
		View usernameChange = li.inflate(R.layout.change_username, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
		alertDialogBuilder.setTitle("Change user name");
		alertDialogBuilder.setView(usernameChange);

		final EditText newUsername= (EditText) findViewById(R.id.newUsernameSettings);
		
		alertDialogBuilder.setCancelable(false);
		alertDialogBuilder.setNeutralButton("Change", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				String newUser;
				newUser = newUsername.getText().toString();
				userName=newUser;
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
		return user;
	}

}

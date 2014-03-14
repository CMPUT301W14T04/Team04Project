package com.example.geoloc;

import java.util.Collection;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

public class Picture {
	
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	public static final int MEDIA_TYPE_IMAGE = 1;
	private Uri fileUri;

	private static final int PICK_FROM_GALLERY = 200;
	Bitmap thumbnail = null;
	
	/**
	 * @uml.property name="comments"
	 * @uml.associationEnd multiplicity="(0 -1)" inverse="picture:com.example.team04project.Comments"
	 */
	private Collection<CommentMetaData> comments;

	/**
	 * Getter of the property <tt>comments</tt>
	 * @return Returns the comments.
	 * @uml.property name="comments"
	 */
	public Collection<CommentMetaData> getComments(){
		return comments;
	}
	
	public void takePicture(){
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		//fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
		//startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch(requestCode) {
			case PICK_FROM_GALLERY:
				/*Uri selectedImage = imageReturnedIntent.getData();
				String[] filePathColumn = {MediaStore.Images.Media.DATA};
				Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
				cursor.moveToFirst();
				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				String filePath = cursor.getString(columnIndex);
				cursor.close();
				Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);*/
			case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE:
				/*if (resultCode == RESULT_OK) {
					// Image captured and saved to fileUri specified in the Intent
					Toast.makeText(this, "Image saved to:\n" +
										data.getData(), Toast.LENGTH_LONG).show();
				} else if (resultCode == RESULT_CANCELED) {
					// User cancelled the image capture
				} else {
					// Image capture failed, advise user
				}*/
		}
	}

}

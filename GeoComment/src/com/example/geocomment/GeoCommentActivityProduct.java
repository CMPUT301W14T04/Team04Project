/**
 * Copyright (c) 2013, Guillermo Ramirez, Nadine Yushko, Tarek El Bohtimy, Yang Wang All rights reserved. Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met: 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. The views and conclusions contained in the software and documentation are those of the authors and should not be interpreted as representing official policies, either expressed or implied, of the FreeBSD Project.
 */
package com.example.geocomment;


import android.content.Intent;
import android.os.Bundle;
import com.example.geocomment.util.Resource;
import com.example.geocomment.model.User;
import com.example.geocomment.model.LocationList;

public class GeoCommentActivityProduct {
	/**
	 * This pases all the data from one activity to another This includes the username, the ID, and the location of the comment
	 */
	public void creatNewComment(User user, LocationList locationHistory,
			GeoCommentActivity geoCommentActivity) {
		Intent intent = new Intent(geoCommentActivity,
				CreateCommentActivity.class);
		Bundle bundle = new Bundle();
		bundle.putParcelable(Resource.USER_INFO, user);
		bundle.putParcelable(Resource.USER_LOCATION_HISTORY, locationHistory);
		bundle.putInt(Resource.TOP_LEVEL_COMMENT, Resource.TYPE_TOP_LEVEL);
		intent.putExtras(bundle);
		geoCommentActivity.startActivityForResult(intent,
				Resource.RESQUEST_NEW_TOP_LEVEL);
	}

	public void editComment(User user, LocationList locationHistory,
			GeoCommentActivity geoCommentActivity) {
		Intent intent = new Intent(geoCommentActivity,
				EditCommentActivity.class);
		Bundle bundle = new Bundle();
		bundle.putParcelable(Resource.USER_INFO, user);
		bundle.putParcelable(Resource.USER_LOCATION_HISTORY, locationHistory);
		bundle.putInt(Resource.TOP_LEVEL_COMMENT, Resource.TYPE_TOP_LEVEL);
		intent.putExtras(bundle);
		geoCommentActivity.startActivityForResult(intent,
				Resource.COMMENT_EDITED);
	}
}
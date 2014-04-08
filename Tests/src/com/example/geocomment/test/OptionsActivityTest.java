/**
Copyright (c) 2013, Guillermo Ramirez, Nadine Yushko, Tarek El Bohtimy, Yang Wang
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright notice,
this list of conditions and the following disclaimer in the documentation
and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

The views and conclusions contained in the software and documentation are those
of the authors and should not be interpreted as representing official policies,
either expressed or implied, of the FreeBSD Project.
*/
package com.example.geocomment.test;

import java.util.ArrayList;

import com.example.geocomment.OptionActivity;
import com.example.geocomment.model.User;
import com.example.geocomment.model.UserProfile;

import android.test.ActivityInstrumentationTestCase2;

public class OptionsActivityTest extends
		ActivityInstrumentationTestCase2<OptionActivity> {
	User user = new User(null, "elbohtim", "1");

	public OptionsActivityTest() {
		super(OptionActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		
	}

	/**
	 * Test that the user name
	 * is set and test that set ID
	 * is returned
	 */
	public void testUser(){
		User user= new User();
		user.setUserName("elbohtim");
		assertEquals("elbohtim",user.getUserName());
		user.setID("010101");
		assertEquals("010101",user.getID());
	}
	/**
	 * This is testing the user profiles making
	 * sure that they return the correct information
	 */
	public void testUserProfile(){
		UserProfile profile= new UserProfile(user);
		ArrayList<String> social = new ArrayList<String>();
		social.add("facebook.com");
		social.add("Twitter.com");
		social.add("myspace.com");
		profile.setBiography("This is a biography");
		profile.setQuote("To program or not to program");
		profile.setSocial(social);
		
		assertEquals("elbohtim",profile.getUsername());
		assertEquals(social,profile.getSocial());
		assertEquals("This is a biography",profile.getBiography());
		assertEquals("To program or not to program",profile.getQuote());
	}
}

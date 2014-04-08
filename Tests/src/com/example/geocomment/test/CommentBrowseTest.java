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

import java.util.Calendar;

import com.example.geocomment.CommentBrowseActivity;
import com.example.geocomment.model.Reply;
import com.example.geocomment.model.TopLevel;
import com.example.geocomment.model.User;

import android.test.ActivityInstrumentationTestCase2;

public class CommentBrowseTest extends
		ActivityInstrumentationTestCase2<CommentBrowseActivity> {
	User user = new User(null, "elbohtim", "1");
	User user2= new User(null,"tarek","2");
	User user3= new User(null,"bobby","3");
	Calendar timeStamp = Calendar.getInstance();
	Reply reply1;
	Reply reply2;
	Reply reply3;
	Reply reply4;


	public CommentBrowseTest() {
		super(CommentBrowseActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		TopLevel toplevel = new TopLevel(user, timeStamp, null, "hi", null, "0", 0);
		reply1 = new Reply(user2,timeStamp,null, "Reply man",null,toplevel.getID(),"1",0);
		reply2 = new Reply(user3,timeStamp,null, "More replies",null,toplevel.getID(),"2",0);
		reply3 = new Reply(user2,timeStamp,null, "Rebuttle",null,toplevel.getID(),"3",0);
		reply4 = new Reply(user2,timeStamp,null, "Autocorrect",null,toplevel.getID(),"4",0);

		
	}
	/**
	 * Testing various things to do with replies
	 * such as who their parent comment is
	 * and whether you can edit them and change other things 
	 * with them
	 */
	public void testReply(){
		assertEquals(reply1.getParentID(),"0");
		assertEquals(reply2.getParentID(),"0");
		assertEquals(reply3.getParentID(),"0");
		assertEquals(reply4.getParentID(),"0");
		assertEquals(reply1.getID(),"1");
		
		reply1.setTextComment("Changed");
		assertEquals("Changed",reply1.getTextComment());
	}

}

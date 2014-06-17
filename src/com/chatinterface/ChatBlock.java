/*
 * The Chat class is an abstract class that represents 
 * all the different chats that will be made.  
 * This class will hold all the common information: 
 * chatId, eDate, userId, username, and color.
 */
package com.chatinterface;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class ChatBlock {
	private int mChatId;
	private long mEDate;
	private int mUserId;
	private String mUsername;
	private int mColor;
	private Date mDate;

	// Here is a constructor. You should only need color as parseJSON will fill
	// in the information.
	public ChatBlock() {
		mChatId = 0;
		mEDate = 0;
		mUserId = 0;
		mUsername = "";
		mColor = 0xFFFFFFFF;
	}

	// Here are the setters and getters.
	public void setChatId(int _chatId) {
		mChatId = _chatId;
	}

	public void setEDate(int _eDate) {
		mEDate = _eDate;
	}

	public void setUserId(int _userId) {
		mUserId = _userId;
	}

	public void setUsername(String _username) {
		mUsername = _username;
	}

	public void setColor(int _color) {
		mColor = _color;
	}

	public int getChatId() {
		return mChatId;
	}

	public long getEDate() {
		return mEDate;
	}

	public Date getDate() {
		return mDate;
	}

	public int getUserId() {
		return mUserId;
	}

	public String getUsername() {
		return mUsername;
	}

	public int getColor() {
		return mColor;
	}

	// Parse the JSON object and extract the ChatID and eDate
	public void parseJson(JSONObject json) {
		try {
			mChatId = json.getInt("eventchatid");
			mEDate = json.getLong("edatecreated");
			mUserId = json.getInt("userid");
			mUsername = json.getString("username");
			mDate = new Date(mEDate); // Here we translate the eDate into a
										// regular date.
		} catch (JSONException e) {
			System.err.println("Caught JSONException: " + e.getMessage());
		}

	}
}

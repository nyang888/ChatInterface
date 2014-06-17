/*
 * This class fills out the information and handles the main_chat_fragment.
 * That includes handling the ListView.
 */
package com.chatinterface;

import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ChatFragment extends Fragment {
	private ArrayList<ChatBlock> mChatList = new ArrayList<ChatBlock>();
	private ArrayList<Integer> mUserIdList = new ArrayList<Integer>();
	private int[] mColorList = new int[5];

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.main_chat_fragment, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// Assign test colors to the array to test whether background colors are
		// working properly.
		mColorList[0] = 0x77700101;
		mColorList[1] = 0x77704F01;
		mColorList[2] = 0x775A7001;
		mColorList[3] = 0x7701705E;
		mColorList[4] = 0x77470170;

		try {
			// Read the sample JSON file and place the information into chatList
			// as chatBlocks.
			JSONArray mTest = readTestJson();

			for (int index = 0; index < mTest.length(); index++) {
				addJson(mTest.getJSONObject(index));
			}
		} catch (Exception e) {
			System.err.println("Caught JSON Exception: " + e.getMessage());
		}

		// Set up the adapters that will display the information to the user.
		ChatAdapter mAdapter = new ChatAdapter(getActivity(), mChatList);
		ListView mListView = (ListView) getActivity().findViewById(R.id.chat);
		mListView.setAdapter(mAdapter);
	}

	// TODO: Replace readTestJson() when you get a better understanding on how
	// JSON will be given readTestJSON will read the sample JSON file to test
	// the program.
	private JSONArray readTestJson() {
		JSONArray mTestMessages = new JSONArray();
		String mJsonString;
		try {
			InputStream is = getActivity().getAssets().open("examplechat.json");
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			mJsonString = new String(buffer, "UTF-8");

			mTestMessages = new JSONArray(mJsonString);
		} catch (Exception e) {
			System.err.println("Caught Exception: " + e.getMessage());
		}

		return mTestMessages;
	}

	// addJSON will add JSON objects to chatList and handle assignment of
	// coloring to each new user.
	public void addJson(JSONObject json) {

		if (json != null) {
			TextBlock tempText = new TextBlock();

			tempText.parseJson(json);

			if (tempText.getUserId() == 0) {
				// System messages are always gray colored.
				tempText.setColor(0x00ffffff);
			} else {
				for (int i = 0; i <= mUserIdList.size(); i++) {
					if (i == mUserIdList.size()) {
						// In case user is not yet assigned a color, give
						// the next color to the user

						mUserIdList.add(Integer.valueOf(tempText.getUserId()));
						tempText.setColor(mColorList[i % 5]);

						break;
					} else if (mUserIdList.get(i).intValue() == mChatList.get(
							mChatList.size() - 1).getUserId()) {
						// If the user is already assigned a color, just add
						// the new chatBlock with the color assigned.

						tempText.setColor(mColorList[i % 5]);
						break;
					}
				}
			}

			mChatList.add(tempText);

		}
	}
}

/*
 * This class is the adapter for the listView.  
 * It takes information from the chatBlock and places it in a View.
 * The view is then sent to the listView in the getView method.
 */
package com.chatinterface;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ChatAdapter extends ArrayAdapter<ChatBlock> {
	private Context mContext;
	private ArrayList<ChatBlock> mChatBlocks;

	// Here is the constructor.
	public ChatAdapter(Context _context, ArrayList<ChatBlock> _chatBlocks) {
		super(_context, R.layout.chat_block, _chatBlocks);
		mContext = _context;
		mChatBlocks = _chatBlocks;
	}

	// getView returns the views that will then be put into the ListView.
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// Create the inflater that will allow us to edit the view.
		LayoutInflater mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// Use the inflater to store the layout of the chat_blocks.
		View mChatView = mInflater.inflate(R.layout.chat_block, parent, false);

		// Here we instantiate the variables and assign them to each part of the
		// RelativeLayout
		TextView mNameView = (TextView) mChatView
				.findViewById(R.id.profile_name);
		TextView mDateView = (TextView) mChatView.findViewById(R.id.time_stamp);
		TextView mMessageView = (TextView) mChatView
				.findViewById(R.id.chat_message);
		ImageView mProfilePictureView = (ImageView) mChatView
				.findViewById(R.id.profile_picture);

		// Here, depending on the type of message, we will fill in the
		// information.
		if (mChatBlocks.get(position) instanceof TextBlock) {
			// In the case its a regular text message:
			if ((position > 0)
					&& (mChatBlocks.get(position - 1).getUserId() == mChatBlocks
							.get(position).getUserId())) {
				// If the person sent consecutive messages, the profile
				// information will not show.

				mMessageView.setText(((TextBlock) mChatBlocks.get(position))
						.getText());
				mProfilePictureView.setVisibility(View.GONE);
				mNameView.setVisibility(View.INVISIBLE);
				mDateView.setVisibility(View.INVISIBLE);
				mChatView.setBackgroundColor(mChatBlocks.get(position)
						.getColor());

			} else {
				// Otherwise, the profile information will show.

				mNameView.setText(mChatBlocks.get(position).getUsername());
				mDateView.setText(mChatBlocks.get(position).getDate()
						.toString());
				mMessageView.setText(((TextBlock) mChatBlocks.get(position))
						.getText());
				mChatView.setBackgroundColor(mChatBlocks.get(position)
						.getColor());
			}
		}

		return mChatView;
	}
}
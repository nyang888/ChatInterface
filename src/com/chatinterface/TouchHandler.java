/*
 * This class handles the touch events that occur.
 * Specifically, it will allow the user to drag the button up and down
 * to lower or raise the chat box.
 */
package com.chatinterface;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class TouchHandler implements OnTouchListener {
	private float mActionBarHeight;
	private Button mButton;
	private View mFrameView;
	private View mMainActivityView;
	private EditText mEditText;

	public TouchHandler(MainActivity _activity) {
		// Here we get the height of the action bar and the button to calculate
		// a proper offset.
		mActionBarHeight = _activity.getActionBarHeight();
		mButton = (Button) _activity.findViewById(R.id.slider);
		mFrameView = (View) _activity.findViewById(R.id.fragment_container);
		mMainActivityView = (View) _activity.findViewById(R.id.container);
		mEditText = (EditText) _activity.findViewById(R.id.input_message);
	}

	@Override
	public boolean onTouch(View view, MotionEvent event) {
		// Don't need to track currentX since the X value should not change.
		float currentY;
		int action = event.getAction();

		switch (action) {
		case MotionEvent.ACTION_DOWN: {
			break;
		}
		// When ACTION_MOVE the button should update location each time.
		// This should bring the chat with it do to its RelativeLayout.
		case MotionEvent.ACTION_MOVE: {
			currentY = event.getRawY();
			// Here we make sure that the ListView is always above the EditText.
			if ((mMainActivityView.getHeight() - currentY + mActionBarHeight) >= 0) {
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
						mFrameView.getLayoutParams());
				params.height = (int) (mMainActivityView.getHeight() - currentY + mActionBarHeight);

				// Here we make sure that the chat does not go past the
				// ActionBar.
				if ((params.height + mButton.getHeight() + mEditText
						.getHeight()) > mMainActivityView.getHeight()) {
					// If the chat is too high, the chat will be forced to be
					// the max possible size.
					params.height = mMainActivityView.getHeight()
							- mButton.getHeight() - mEditText.getHeight();
				}

				params.addRule(RelativeLayout.ABOVE, R.id.input_message);
				mFrameView.setLayoutParams(params);
			}
			break;
		}
		}

		return true;

	}
}

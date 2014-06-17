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
import android.widget.RelativeLayout;

public class TouchHandler implements OnTouchListener {
	private float mActionBarHeight;
	private Button mButton;
	private View frameView;

	public TouchHandler(Button _button, MainActivity _activity) {
		// Here we get the height of the action bar and the button to calculate
		// a proper offset.
		mActionBarHeight = _activity.getActionBarHeight();
		mButton = _button;
		frameView = (View) _activity.findViewById(R.id.fragment_container);
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
			// Here we calculate the values with the actionBarHeight and button
			// height. it takes 2 of button heights to be able to get the button
			// to always be above the finger.
			if ((currentY - mActionBarHeight - (2 * mButton.getHeight())) > 0) {
				/*
				 * RelativeLayout.MarginLayoutParams marginLayoutParams = new
				 * RelativeLayout.MarginLayoutParams( view.getLayoutParams());
				 * marginLayoutParams.setMargins(0, (int) (currentY -
				 * mActionBarHeight - (2 * mButton.getHeight())), 0, 0);
				 * RelativeLayout.LayoutParams layoutParams = new
				 * RelativeLayout.LayoutParams( marginLayoutParams);
				 * view.setLayoutParams(layoutParams);
				 */
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
						frameView.getLayoutParams());
				params.height = (int) (currentY - mActionBarHeight - (2 * mButton
						.getHeight()));
				frameView.setLayoutParams(params);
			}
			break;
		}
		}

		return true;

	}
}

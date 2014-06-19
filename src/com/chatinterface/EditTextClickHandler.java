package com.chatinterface;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

public class EditTextClickHandler implements OnClickListener {
	private View mMainActivityView;
	private View mListViewFragment;
	private Button mSliderButton;
	private float mActionBarHeight;

	public EditTextClickHandler(MainActivity _activity) {
		mMainActivityView = (View) _activity.findViewById(R.id.container);
		mActionBarHeight = _activity.getActionBarHeight();
		mListViewFragment = (View) _activity
				.findViewById(R.id.fragment_container);
		mSliderButton = (Button) _activity.findViewById(R.id.slider);
	}

	@Override
	public void onClick(View arg0) {
		// Instantiate the LayoutParams necessary to update values.
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				mListViewFragment.getLayoutParams());

		if (mSliderButton.getTop() <= 0) {
			// If the chat is too high, the chat will be forced to be
			// the max possible size.
			params.height = (int) (mMainActivityView.getHeight()
					- mActionBarHeight - mSliderButton.getHeight());
			params.addRule(RelativeLayout.ABOVE, R.id.input_message);
			mListViewFragment.setLayoutParams(params);
		}

	}
}

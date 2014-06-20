/*
 * This class handles instantiation and creation of the views.
 */
package com.chatinterface;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.TypedValue;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Create a new Fragment to be placed in the activity layout
		ChatFragment mChatFragment = new ChatFragment();
		mChatFragment.setArguments(getIntent().getExtras());

		// Add the fragment to the 'fragment_container' FrameLayout
		getSupportFragmentManager().beginTransaction()
				.add(R.id.fragment_container, mChatFragment).commit();

		// Prepare for the button sliding with the TouchHandler.
		Button chatSlider = (Button) findViewById(R.id.slider);
		TouchHandler touch = new TouchHandler(this);
		chatSlider.setOnTouchListener(touch);

	}

	public float getActionBarHeight() { // Return the height of the action bar
										// for the touch handler to calculate
										// offset.
		TypedValue typedValue = new TypedValue();
		getTheme().resolveAttribute(R.attr.actionBarSize, typedValue, true);
		float mActionBarHeight = getResources().getDimensionPixelSize(
				typedValue.resourceId);

		return mActionBarHeight;
	}

}

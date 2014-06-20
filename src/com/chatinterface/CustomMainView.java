/*
 * This class is made as a RelativeLayout for the Main View.
 * It is needed so that I can override the onDraw method.
 * The class will check for size changes in the onDraw method since it
 * is called every time the view changes.  If the chat is too high, the onDraw 
 * method will move the button down to make sure it fits properly.
 */
package com.chatinterface;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class CustomMainView extends RelativeLayout {
	private View mListViewFragment;
	private View mMainActivityView;
	private Button mSliderButton;
	private EditText mEditText;

	public CustomMainView(Context context) {
		super(context);
	}

	public CustomMainView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomMainView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mListViewFragment = (View) findViewById(R.id.fragment_container);
		mMainActivityView = (View) findViewById(R.id.container);
		mSliderButton = (Button) findViewById(R.id.slider);
		mEditText = (EditText) findViewById(R.id.input_message);

		// Instantiate the LayoutParams necessary to update values.
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				mListViewFragment.getLayoutParams());

		if (mSliderButton.getTop() <= 0) {
			// If the chat is too high, the chat will be forced to be
			// the max possible size. This is done by constantly redrawing the
			// image in decrements of 1. Higher decrements has the possibility
			// that the decrement will be too much after a single draw.
			params.height = mListViewFragment.getHeight() - 1;
			params.addRule(RelativeLayout.ABOVE, R.id.input_message);
			mListViewFragment.setLayoutParams(params);
		}
	}
}

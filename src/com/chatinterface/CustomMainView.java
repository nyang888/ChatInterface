/*
 * This class is made as a RelativeLayout for the Main View.
 * It is needed so that I can override the onDraw method.
 * The class will check for size changes in the onDraw method since it
 * is called every time the view changes.  If the chat is too high, the onDraw 
 * method will move the button down to make sure it fits properly.
 */
package com.chatinterface;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class CustomMainView extends RelativeLayout {
	private View mListViewFragment;
	private View mMainActivityView;
	private Button mSliderButton;

	// Quick Calculation of the ActionBarSize for later calculations.
	private TypedArray styledAttributes = getContext().getTheme()
			.obtainStyledAttributes(new int[] { R.attr.actionBarSize });
	private float mActionBarSize = (int) styledAttributes.getDimension(0, 0);

	// Instantiate the LayoutParams
	private RelativeLayout.LayoutParams params;

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

		// Update the LayoutParams necessary to for the values to be checked
		// later.
		params = (RelativeLayout.LayoutParams) mListViewFragment
				.getLayoutParams();

		if (mSliderButton.getBottom() <= 0) {
			// If the chat is too high, the chat will be forced to be
			// the max possible size. This is done by constantly redrawing the
			// image in decrements of 1. Higher decrements has the possibility
			// that the decrement will be too much after a single draw.
			if (mSliderButton.getTop() == 0) {
				params.height = (int) (mMainActivityView.getHeight()
						- mActionBarSize - mSliderButton.getHeight());
				params.addRule(RelativeLayout.ABOVE, R.id.send_message_block);
				mListViewFragment.setLayoutParams(params);
			}

		}

	}
}

/*
 * This class handles instantiation and creation of the views.
 */
package com.chatinterface;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.TypedValue;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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

		// Configure the google map: No Zoom in/out, add current location
		// button, set map to hybrid satellite map.
		final GoogleMap mGoogleMap = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		mGoogleMap.getUiSettings().setZoomControlsEnabled(false);
		mGoogleMap.setMyLocationEnabled(true);
		mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

		// Setting a click event handler for the map
		mGoogleMap.setOnMapClickListener(new OnMapClickListener() {

			@Override
			public void onMapClick(LatLng latLng) {

				// Creating a marker
				MarkerOptions markerOptions = new MarkerOptions();

				// Setting the position for the marker
				markerOptions.position(latLng);

				// Animating to the touched position
				mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

				// Placing a marker on the touched position
				mGoogleMap.addMarker(markerOptions);
			}
		});
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

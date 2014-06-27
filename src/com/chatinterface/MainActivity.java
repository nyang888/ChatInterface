/*
 * This class handles instantiation and creation of the views.
 */
package com.chatinterface;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
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
	private GoogleMap mGoogleMap;
	private ArrayList<LatLng> mLatLngList = new ArrayList<LatLng>();
	private Route mRoute = new Route();
	private Context mContext;
	private Activity mActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mContext = this;
		mActivity = this;

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
		mGoogleMap = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		mGoogleMap.getUiSettings().setZoomControlsEnabled(false);
		mGoogleMap.setMyLocationEnabled(true);
		mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

		// Here we create a new AddressInputFragment which should handle
		// all the stuff for the Address Inputs
		AddressInputFragment mAddressInputFragment = new AddressInputFragment();
		mAddressInputFragment.setArguments(getIntent().getExtras());
		mAddressInputFragment
				.setValues(mActivity, mGoogleMap, mRoute, mContext);
		getSupportFragmentManager().beginTransaction()
				.add(R.id.address_container, mAddressInputFragment).commit();

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

				// Here we add the latLng to the ArrayList. This will be used
				// for the drawPath function. Each path will stem from one of
				// the markers as if it were a wayPoint.
				mLatLngList.add(latLng);

				if (mLatLngList.size() > 1) {
					// As long as there are 2 or more markers down, a path will
					// be drawn
					for (int i = 1; i < mLatLngList.size(); i++) {
						mRoute.drawRoute(mGoogleMap, mContext,
								mLatLngList.get(i - 1), mLatLngList.get(i));
					}
				}
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

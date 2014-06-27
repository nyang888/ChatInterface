package com.chatinterface;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;

public class AddressInputFragment extends Fragment {
	private Activity mActivity;
	private GoogleMap mGoogleMap;
	private Route mRoute;
	private Context mContext;
	private TextView sourceTitle;
	private TextView destTitle;
	private EditText sourceAddress;
	private EditText destAddress;
	private Button mInputAddressButton;
	private Button mDropDownButton;

	public void setValues(Activity _activity, GoogleMap _map, Route _route,
			Context _context) {
		// Here we set the values of the Fragment. These values are necessary to
		// perform certain actions.
		mActivity = _activity;
		mGoogleMap = _map;
		mRoute = _route;
		mContext = _context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.input_address_fragment, container,
				false);

	}

	@Override
	public void onStart() {
		super.onStart();
		// Here we set all the different Views to variables in order to
		// manipulate them later.
		sourceTitle = (TextView) mActivity.findViewById(R.id.source_title);
		destTitle = (TextView) mActivity.findViewById(R.id.dest_title);
		sourceAddress = (EditText) mActivity.findViewById(R.id.source_address);
		destAddress = (EditText) mActivity.findViewById(R.id.dest_address);
		mInputAddressButton = (Button) mActivity
				.findViewById(R.id.send_address);
		mDropDownButton = (Button) mActivity.findViewById(R.id.address_show);

		closeAddressInput(); // Start off closed.

		mInputAddressButton.setOnClickListener(new OnClickListener() {
			// Here we set mInputAddressButton to send out the address as a
			// request.
			@Override
			public void onClick(View v) {
				mGoogleMap.clear(); // Clear the previous routes

				mRoute.drawRoute(mGoogleMap, mContext, sourceAddress.getText()
						.toString(), destAddress.getText().toString());

				// We close the Text Input area after getting the path.
				closeAddressInput();
			}

		});
		mDropDownButton.setOnClickListener(new OnClickListener() {
			// When you hit the top left button, it will show you the address
			// input screen.
			@Override
			public void onClick(View v) {
				openAddressInput();
			}
		});

	}

	public void closeAddressInput() {
		// Here we set the visibility of all the elements in the address input
		// area to GONE. This will also make sure they do not take up space as
		// in INVISIBLE
		sourceTitle.setVisibility(View.GONE);
		destTitle.setVisibility(View.GONE);
		sourceAddress.setVisibility(View.GONE);
		destAddress.setVisibility(View.GONE);
		mInputAddressButton.setVisibility(View.GONE);
	}

	public void openAddressInput() {
		// Here we open it up by making all the elements visible.
		sourceTitle.setVisibility(View.VISIBLE);
		destTitle.setVisibility(View.VISIBLE);
		sourceAddress.setVisibility(View.VISIBLE);
		destAddress.setVisibility(View.VISIBLE);
		mInputAddressButton.setVisibility(View.VISIBLE);
	}
}

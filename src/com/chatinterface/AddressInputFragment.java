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
	private EditText sourceStreet;
	private EditText sourceState;
	private EditText destStreet;
	private EditText destState;
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
		sourceStreet = (EditText) mActivity.findViewById(R.id.source_street);
		sourceState = (EditText) mActivity.findViewById(R.id.source_state);
		destStreet = (EditText) mActivity.findViewById(R.id.dest_street);
		destState = (EditText) mActivity.findViewById(R.id.dest_state);
		mInputAddressButton = (Button) mActivity
				.findViewById(R.id.send_address);
		mDropDownButton = (Button) mActivity.findViewById(R.id.address_show);

		closeAddressInput(); // Start off closed.

		mInputAddressButton.setOnClickListener(new OnClickListener() {
			// Here we set mInputAddressButton to send out the address as a
			// request.
			@Override
			public void onClick(View v) {
				StringBuilder source = new StringBuilder();
				StringBuilder dest = new StringBuilder();
				mGoogleMap.clear();

				source.append(sourceStreet.getText().toString());
				source.append(sourceState.getText().toString());
				dest.append(destStreet.getText().toString());
				dest.append(destState.getText().toString());
				mRoute.drawRoute(mGoogleMap, mContext, source.toString(),
						dest.toString());

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
		sourceStreet.setVisibility(View.GONE);
		sourceState.setVisibility(View.GONE);
		destStreet.setVisibility(View.GONE);
		destState.setVisibility(View.GONE);
		mInputAddressButton.setVisibility(View.GONE);
	}

	public void openAddressInput() {
		// Here we open it up by making all the elements visible.
		sourceTitle.setVisibility(View.VISIBLE);
		destTitle.setVisibility(View.VISIBLE);
		sourceStreet.setVisibility(View.VISIBLE);
		sourceState.setVisibility(View.VISIBLE);
		destStreet.setVisibility(View.VISIBLE);
		destState.setVisibility(View.VISIBLE);
		mInputAddressButton.setVisibility(View.VISIBLE);
	}
}

package com.chatinterface;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class Route {
	private GoogleMap mGoogleMap;
	private Context mContext;

	// Here we implement the drawing of the path. It will actually call the
	// private class DrawTask to connect to the server and get the directions.
	public void drawRoute(GoogleMap map, Context c, LatLng source, LatLng dest) {
		mGoogleMap = map;
		mContext = c;
		String url = makeURL(source.latitude, source.longitude, dest.latitude,
				dest.longitude);

		new DrawTask(url).execute();

	}

	public void drawRoute(GoogleMap map, Context c, String source, String dest) {
		// This is the drawRoute implementation for two address strings.
		mGoogleMap = map;
		mContext = c;

		String url = makeURL(source, dest);

		new DrawTask(url).execute();

	}

	private String makeURL(double sourcelat, double sourcelog, double destlat,
			double destlog) {
		// Here we use a StringBuilder to create a URL to access directions. The
		// parameters are then appended to the StringBuilder to finish the URL.
		StringBuilder mUrlString = new StringBuilder();
		String toString = "";

		mUrlString
				.append("http://maps.googleapis.com/maps/api/directions/json");
		mUrlString.append("?origin=");// from
		mUrlString.append(Double.toString(sourcelat));
		mUrlString.append(",");
		mUrlString.append(Double.toString(sourcelog));
		mUrlString.append("&destination=");// to
		mUrlString.append(Double.toString(destlat));
		mUrlString.append(",");
		mUrlString.append(Double.toString(destlog));
		mUrlString.append("&sensor=false&mode=walking");
		toString = mUrlString.toString();
		toString = toString.replaceAll("\\s", "+");
		return toString;
	}

	private String makeURL(String source, String dest) {
		// This is the makeURL implementation with addresses.
		StringBuilder mUrlString = new StringBuilder();
		String toString = "";

		mUrlString
				.append("http://maps.googleapis.com/maps/api/directions/json");
		mUrlString.append("?origin=");// from
		mUrlString.append(source);
		mUrlString.append("&destination=");// to
		mUrlString.append(dest);
		mUrlString.append("&sensor=false&mode=walking");
		System.out.println(mUrlString.toString());
		toString = mUrlString.toString();
		toString = toString.replaceAll("\\s", "+");
		return toString;
	}

	private List<LatLng> decodePoly(String encoded) {

		List<LatLng> poly = new ArrayList<LatLng>();
		int index = 0, len = encoded.length();
		int lat = 0, lng = 0;

		while (index < len) {
			int b, shift = 0, result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lat += dlat;

			shift = 0;
			result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lng += dlng;

			LatLng p = new LatLng((((double) lat / 1E5)),
					(((double) lng / 1E5)));
			poly.add(p);
		}

		return poly;
	}

	// Here is the AsyncTask that will run threads in the background without
	// much interruption.
	private class DrawTask extends AsyncTask<Void, Void, String> {
		private ProgressDialog mProgressDialog;
		String mUrl;

		DrawTask(String urlPass) {
			mUrl = urlPass;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Here we show a message that tells the user we are getting the
			// route from Google.
			mProgressDialog = new ProgressDialog(mContext);
			mProgressDialog.setMessage("Fetching route, Please wait...");
			mProgressDialog.setIndeterminate(true);
			mProgressDialog.show();
		}

		@Override
		protected String doInBackground(Void... params) {
			// While the message is shown, we try to get the JSON
			MapJsonParser mMapJsonParser = new MapJsonParser();
			String mJsonString = mMapJsonParser.getJSONFromUrl(mUrl);
			return mJsonString;
		}

		@Override
		protected void onPostExecute(String result) {
			// After execution, just hide the loading message.
			super.onPostExecute(result);
			mProgressDialog.hide();
			if (result != null) {
				drawPath(result);
			}
		}
	}

	private void drawPath(String result) {
		// Here we try to draw the actually PolyLine and add it the the
		// GoogleMap
		try {
			// Here we need to turn the String into a JSONObject
			JSONObject json = new JSONObject(result);
			JSONArray routeArray = json.getJSONArray("routes");
			JSONObject routes = routeArray.getJSONObject(0);
			JSONObject overviewPolylines = routes
					.getJSONObject("overview_polyline");
			String encodedString = overviewPolylines.getString("points");
			List<LatLng> list = decodePoly(encodedString);

			for (int z = 0; z < list.size() - 1; z++) {
				// This list asks to get every LatLng available and add the
				// information into a PolyLine
				LatLng src = list.get(z);
				LatLng dest = list.get(z + 1);
				Polyline line = mGoogleMap.addPolyline(new PolylineOptions()
						.add(new LatLng(src.latitude, src.longitude),
								new LatLng(dest.latitude, dest.longitude))
						.width(30).color(Color.BLUE).geodesic(true));
			}

		} catch (JSONException e) {
			System.out.println(e.toString());
		}
	}

}

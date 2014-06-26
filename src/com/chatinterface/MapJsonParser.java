package com.chatinterface;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

public class MapJsonParser {

	private static InputStream mInputStream = null;
	private static String mJsonString = "";

	public String getJSONFromUrl(String url) {

		// Here we try to make a HTTP request.
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			mInputStream = httpEntity.getContent();

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		// Here we take the filled mInputStream and feed it into a
		// BufferedReader that can later be made into a String
		try {
			BufferedReader mBufferedReader = new BufferedReader(new InputStreamReader(
					mInputStream, "iso-8859-1"), 8);
			StringBuilder mStringBuilder = new StringBuilder();
			String mLine = null;
			while ((mLine = mBufferedReader.readLine()) != null) {
				mStringBuilder.append(mLine + "\n");
			}

			mJsonString = mStringBuilder.toString();
			mInputStream.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return mJsonString;

	}
}

package com.example.youtubemaster;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

public class YoutubeGetter {
	private static String prefix = "https://gdata.youtube.com/feeds/api/videos?q=sugar+ray+robinson&orderby=published&start-index=";

	private static String postfix = "&max-results=20&v=2&alt=jsonc";

	public static List<Item> getItems(int firstindex) {
		String jsonStr = getJSONString(firstindex);
		List<Item> li = new ArrayList<Item>();
		try {
			JSONObject base = new JSONObject(jsonStr);
			JSONObject data = base.getJSONObject("data");
			JSONArray ja = data.getJSONArray("items");
			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				Item item = new Item();
				item.setId(jo.getString("id"));
				item.setCategory(jo.getString("category"));
				item.setDescription(jo.getString("description"));
				item.setTitle(jo.getString("title"));

				JSONObject player = jo.getJSONObject("player");
				item.setUrl(player.getString("default"));

				System.out.println("Player is " + item.getUrl());

				Thumbnail thnail = new Thumbnail();
				JSONObject jThumbnail = jo.getJSONObject("thumbnail");
				thnail.setSqDefault(jThumbnail.getString("sqDefault"));
				thnail.setHqDefault(jThumbnail.getString("hqDefault"));
				item.setThumbnail(thnail);
				li.add(item);
			}
		} catch (Exception e) {
			return li;
		}
		return li;

	}

	private static String getJSONString(int index) {

		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(prefix + index + postfix);
		try {
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
			} else {
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return builder.toString();
	}
}

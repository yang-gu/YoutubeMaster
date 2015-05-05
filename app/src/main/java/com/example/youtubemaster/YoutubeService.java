package com.example.youtubemaster;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

public interface YoutubeService {
	
	 @GET("/feeds/api/videos?q=sugar+ray+robinson&orderby=published&start-index=1&max-results=10&v=2&alt=jsonc")
	 public void items(Callback<List<Item>> response);

}

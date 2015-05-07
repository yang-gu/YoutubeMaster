package com.example.youtubemaster;

import java.io.Serializable;

public class Thumbnail implements Serializable {
	private String sqDefault;
	private String hqDefault;
	public String getSqDefault() {
		return sqDefault;
	}
	public void setSqDefault(String sqDefault) {
		this.sqDefault = sqDefault;
	}
	public String getHqDefault() {
		return hqDefault;
	}
	public void setHqDefault(String hqDefault) {
		this.hqDefault = hqDefault;
	}
}

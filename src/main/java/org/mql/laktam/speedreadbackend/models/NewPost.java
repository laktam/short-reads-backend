package org.mql.laktam.speedreadbackend.models;

public class NewPost {
	private String content;
	private String backgroundUrl;
	
	public NewPost() {
	}

	public NewPost(String content, String backgroundUrl) {
		super();
		this.content = content;
		this.backgroundUrl = backgroundUrl;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getBackgroundUrl() {
		return backgroundUrl;
	}

	public void setBackgroundUrl(String backgroundUrl) {
		this.backgroundUrl = backgroundUrl;
	}
	
	
}

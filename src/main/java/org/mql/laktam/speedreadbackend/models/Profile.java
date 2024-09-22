package org.mql.laktam.speedreadbackend.models;

public class Profile {
	private String profilePictureUrl;
	private String username;
	private String description;
	private int followersCount;
	private String email;
	
	public Profile() {
	}

	public Profile(String username, String description, int followersCount, String email, String profilePictureUrl) {
		super();
		this.username = username;
		this.description = description;
		this.followersCount = followersCount;
		this.email = email;
		this.profilePictureUrl = profilePictureUrl;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getFollowersCount() {
		return followersCount;
	}

	public void setFollowersCount(int followersCount) {
		this.followersCount = followersCount;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setProfilePictureUrl(String profilePictureUrl) {
		this.profilePictureUrl = profilePictureUrl;
	}
	
	public String getProfilePictureUrl() {
		return profilePictureUrl;
	}

	@Override
	public String toString() {
		return "Profile [profilePictureUrl=" + profilePictureUrl + ", username=" + username + ", description="
				+ description + ", followersCount=" + followersCount + ", email=" + email + "]";
	}

	

	
	
	

}

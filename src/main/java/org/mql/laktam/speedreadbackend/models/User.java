package org.mql.laktam.speedreadbackend.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	@Column(unique = true, nullable = false, length = 60)
	private String username;

	@Column(unique = true, nullable = false)
	private String email;

	@Column(name = "password_hash", nullable = false)
	private String passwordHash;

	@Column(name = "profile_picture_url")
	private String profilePictureUrl;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Column(name = "last_login")
	private LocalDateTime lastLogin;
	
	private String description;

	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private Set<Post> posts = new HashSet<>();

	@OneToMany(mappedBy = "user")
	private Set<Like> likes = new HashSet<>();

	@OneToMany(mappedBy = "follower")
	private Set<Follow> following = new HashSet<>();

	@OneToMany(mappedBy = "followed")
	private Set<Follow> followers = new HashSet<>();

	public User() {
	}

	public User(String username, String email, String passwordHash) {
		this.username = username;
		this.email = email;
		this.passwordHash = passwordHash;
		this.createdAt = LocalDateTime.now();
		this.description = "";
		this.profilePictureUrl = "";
	}
	
	public Profile toProfile() {
		return new Profile(username, description, followers.size(), email, profilePictureUrl);
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getProfilePictureUrl() {
		return profilePictureUrl;
	}

	public void setProfilePictureUrl(String profilePictureUrl) {
		this.profilePictureUrl = profilePictureUrl;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(LocalDateTime lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}

	public Set<Like> getLikes() {
		return likes;
	}

	public void setLikes(Set<Like> likes) {
		this.likes = likes;
	}

	public Set<Follow> getFollowing() {
		return following;
	}

	public void setFollowing(Set<Follow> following) {
		this.following = following;
	}

	public Set<Follow> getFollowers() {
		return followers;
	}

	public void setFollowers(Set<Follow> followers) {
		this.followers = followers;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "User{" + "id=" + id + ", username='" + username + '\'' + ", email='" + email + '\'' + ", description='" + description + '\'' + ", createdAt="
				+ createdAt + ", lastLogin=" + lastLogin + '}';
	}
}
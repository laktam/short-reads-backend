package org.mql.laktam.speedreadbackend.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "posts")
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(nullable = false)
	private String content;

	@Column(name = "background_url")
	private String backgroundUrl;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@ManyToMany
	@JoinTable(name = "post_tags", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
	private Set<Tag> tags = new HashSet<>();

	@OneToMany(mappedBy = "post")
	private Set<Like> likes = new HashSet<>();

	public Post() {
	}

	public Post(User user, String content, Set<Tag> tags) {
		this.user = user;
		this.content = content;
		this.createdAt = LocalDateTime.now();
		this.tags = tags;
		this.backgroundUrl = "";
		
	}
	
	public Post(User user, String content, String backgroundUrl) {
		this.user = user;
		this.content = content;
		this.createdAt = LocalDateTime.now();
		this.backgroundUrl = backgroundUrl;
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public Set<Like> getLikes() {
		return likes;
	}

	public void setLikes(Set<Like> likes) {
		this.likes = likes;
	}

	@Override
	public String toString() {
		return "Post{" + "id=" + id + ", user=" + user.getUsername() + ", content='" + content + '\'' + ", createdAt="
				+ createdAt + '}';
	}
}

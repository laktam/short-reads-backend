package org.mql.laktam.speedreadbackend.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "likes")
public class Like {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "like_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;

	@Column(name = "is_like", nullable = false)
	private boolean isLike;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	public Like() {
	}

	public Like(User user, Post post, boolean isLike) {
		this.user = user;
		this.post = post;
		this.isLike = isLike;
		this.createdAt = LocalDateTime.now();
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

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public boolean isLike() {
		return isLike;
	}

	public void setLike(boolean like) {
		isLike = like;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "Like{" + "id=" + id + ", user=" + user.getUsername() + ", post=" + post.getId() + ", isLike=" + isLike
				+ ", createdAt=" + createdAt + '}';
	}
}

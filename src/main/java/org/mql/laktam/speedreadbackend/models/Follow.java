package org.mql.laktam.speedreadbackend.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "follows")
public class Follow {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "follow_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "follower_id", nullable = false)
	private User follower;

	@ManyToOne
	@JoinColumn(name = "followed_id", nullable = false)
	private User followed;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	public Follow() {
	}

	public Follow(User follower, User followed) {
		this.follower = follower;
		this.followed = followed;
		this.createdAt = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getFollower() {
		return follower;
	}

	public void setFollower(User follower) {
		this.follower = follower;
	}

	public User getFollowed() {
		return followed;
	}

	public void setFollowed(User followed) {
		this.followed = followed;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "Follow{" + "id=" + id + ", follower=" + follower.getUsername() + ", followed=" + followed.getUsername()
				+ ", createdAt=" + createdAt + '}';
	}
}
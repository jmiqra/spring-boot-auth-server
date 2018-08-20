package com.asraf.entities;
// Generated May 29, 2018 10:26:07 AM by Hibernate Tools 5.2.10.Final

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * User generated by hbm2java
 */
@Entity
@Table(name = "user", catalog = "mytestdb")
public class User extends BaseEntity implements java.io.Serializable {

	private Long id;
	private String email;
	private String name;
	private UserProfile userProfile;
	private Set<UserVerification> userVerifications = new HashSet<UserVerification>(0);

	public User() {
	}

	public User(String email, String name) {
		this.email = email;
		this.name = name;
	}

	public User(String email, String name, UserProfile userProfile, Set<UserVerification> userVerifications) {
		this.email = email;
		this.name = name;
		this.userProfile = userProfile;
		this.userVerifications = userVerifications;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "email", nullable = false, length = 45)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "name", nullable = false, length = 45)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
	public UserProfile getUserProfile() {
		return this.userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	public Set<UserVerification> getUserVerifications() {
		return this.userVerifications;
	}

	public void setUserVerifications(Set<UserVerification> userVerifications) {
		this.userVerifications = userVerifications;
	}

}

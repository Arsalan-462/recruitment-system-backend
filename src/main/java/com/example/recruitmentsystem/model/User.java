package com.example.recruitmentsystem.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", address=" + address + ", userType="
				+ userType + ", passwordHash=" + passwordHash + ", profileHeadline=" + profileHeadline + ", profile="
				+ profile + ", appliedJobs=" + appliedJobs + "]";
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(Long id, String name, String email, String address, String userType, String passwordHash,
			String profileHeadline, Profile profile, Set<Job> appliedJobs) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.address = address;
		this.userType = userType;
		this.passwordHash = passwordHash;
		this.profileHeadline = profileHeadline;
		this.profile = profile;
		this.appliedJobs = appliedJobs;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getProfileHeadline() {
		return profileHeadline;
	}

	public void setProfileHeadline(String profileHeadline) {
		this.profileHeadline = profileHeadline;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Set<Job> getAppliedJobs() {
		return appliedJobs;
	}

	public void setAppliedJobs(Set<Job> appliedJobs) {
		this.appliedJobs = appliedJobs;
	}

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private String address;

    @Column(nullable = false)
    private String userType; // "Admin" or "Applicant"

    @Column(nullable = false)
    private String passwordHash;

    private String profileHeadline;

    @OneToOne(mappedBy = "applicant", cascade = CascadeType.ALL)
    private Profile profile;

    @ManyToMany(mappedBy = "applicants")
    private Set<Job> appliedJobs;


}

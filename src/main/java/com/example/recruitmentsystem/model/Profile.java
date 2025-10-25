package com.example.recruitmentsystem.model;

import jakarta.persistence.*;

@Entity
@Table(name = "profiles")
public class Profile {
    @Override
	public String toString() {
		return "Profile [id=" + id + ", applicant=" + applicant + ", resumeFileAddress=" + resumeFileAddress
				+ ", skills=" + skills + ", education=" + education + ", experience=" + experience + ", phone=" + phone
				+ "]";
	}

	public Profile() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Profile(Long id, User applicant, String resumeFileAddress, String skills, String education,
			String experience, String phone) {
		super();
		this.id = id;
		this.applicant = applicant;
		this.resumeFileAddress = resumeFileAddress;
		this.skills = skills;
		this.education = education;
		this.experience = experience;
		this.phone = phone;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getApplicant() {
		return applicant;
	}

	public void setApplicant(User applicant) {
		this.applicant = applicant;
	}

	public String getResumeFileAddress() {
		return resumeFileAddress;
	}

	public void setResumeFileAddress(String resumeFileAddress) {
		this.resumeFileAddress = resumeFileAddress;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Id
    private Long id;  // same as User id

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User applicant;

    private String resumeFileAddress;

    @Column(length = 2000)
    private String skills;

    @Column(length = 2000)
    private String education;

    @Column(length = 2000)
    private String experience;

    private String phone;

   
}


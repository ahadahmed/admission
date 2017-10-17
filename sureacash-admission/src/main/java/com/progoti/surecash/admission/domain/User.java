package com.progoti.surecash.admission.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.google.common.base.MoreObjects;

@Entity
@Table(name = "user_info", uniqueConstraints = @UniqueConstraint(columnNames = { "university_id", "user_name" }))
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int userId;
	@Column(name = "user_name", length = 100)
	private String userName;
	@Column(name = "password", length = 100)
	private String password;
	@Column(name = "email")
	private String email;
	
	@OneToOne
	@JoinColumn(name = "role_id")
	private Role role;
	@ManyToOne
	@JoinColumn(name = "university_id")
	private University univ;
	@Column(name = "mobile_no", length = 11)
	private String mobile;
	@Column(name = "is_student", length = 11)
	private boolean isStudent;
	@OneToOne
	@JoinColumn(name = "student_id")
	private StudentInfo studentId;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public University getUniv() {
		return univ;
	}

	public void setUniv(University univ) {
		this.univ = univ;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public boolean isStudent() {
		return isStudent;
	}

	public void setStudent(boolean isStudent) {
		this.isStudent = isStudent;
	}

	public StudentInfo getStudentId() {
		return studentId;
	}

	public void setStudentId(StudentInfo studentId) {
		this.studentId = studentId;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("userId", userId).add("userName", userName).add("email", email)
				.add("role", role).add("univ", univ).toString();
	}
}

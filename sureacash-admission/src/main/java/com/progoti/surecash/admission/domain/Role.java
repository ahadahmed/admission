package com.progoti.surecash.admission.domain;

import com.progoti.surecash.admission.utility.Constants;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "role", uniqueConstraints = @UniqueConstraint(columnNames = { "role"}))
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "role")
    @Enumerated(EnumType.STRING)
	private Constants.RoleName roleName;

	@OneToMany(mappedBy = "role")
	private Set<User> userRole;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Constants.RoleName getRoleName() {
		return roleName;
	}

	public void setRoleName(Constants.RoleName roleName) {
		this.roleName = roleName;
	}

	public Set<User> getUserRole() {
		return userRole;
	}

	public void setUserRole(Set<User> userRole) {
		this.userRole = userRole;
	}
}

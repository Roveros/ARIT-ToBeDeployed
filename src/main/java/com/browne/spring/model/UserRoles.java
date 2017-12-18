package com.browne.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="USER_ROLES")
public class UserRoles {
	
	@Id
	@Column(name="user_role_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int user_role_id;
	
	 
	@OneToOne
	@JoinColumn(name="username")
	private Users users;
	
	
	String role;
	
	public int getUser_role_id() {
		return user_role_id;
	}
	public void setUser_role_id(int user_role_id) {
		this.user_role_id = user_role_id;
	}
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	@Override
	public String toString(){
		return "id="+user_role_id+", username="+users.getUsername()+", role="+role;
	}
	
}

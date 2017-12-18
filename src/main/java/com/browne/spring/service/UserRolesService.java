package com.browne.spring.service;

import java.util.List;

import com.browne.spring.model.UserRoles;

public interface UserRolesService {
	public void addUserRoles(UserRoles userRoles);
	public void updateUserRoles(UserRoles userRoles);
	public List<UserRoles> listUserRoles();
	public UserRoles getUserRolesById(int id);
	public List<UserRoles> getUserRolesByDepartment(String department);
	public void removeUserRoles(int id);
}

package com.browne.spring.dao;


import java.util.List;

import com.browne.spring.model.UserRoles;

public interface UserRolesDAO {
	public void addUserRoles(UserRoles userRoles);
	public void updateUserRoles(UserRoles userRoles);
	public List<UserRoles> listUserRoles();
	public UserRoles getUserRolesById(int id);
	public List<UserRoles> getUserRolesByDepartment(String department);
	public void removeUserRoles(int id);
}

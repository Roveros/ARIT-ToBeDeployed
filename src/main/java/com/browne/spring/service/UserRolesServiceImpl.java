package com.browne.spring.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.browne.spring.dao.UserRolesDAO;
import com.browne.spring.model.UserRoles;

@Service("userRolesService")
public class UserRolesServiceImpl implements UserRolesService{
	
	@Autowired
	private UserRolesDAO userRolesDAO;
	
	public void setUserRolesDAO(UserRolesDAO userRolesDAO) {
		this.userRolesDAO = userRolesDAO;
	}

	@Override
	@Transactional
	public void addUserRoles(UserRoles userRoles) {
		// TODO Auto-generated method stub
		this.userRolesDAO.addUserRoles(userRoles);
	}

	@Override
	@Transactional
	public void updateUserRoles(UserRoles userRoles) {
		// TODO Auto-generated method stub
		this.userRolesDAO.updateUserRoles(userRoles);
	}

	@Override
	@Transactional
	public List<UserRoles> listUserRoles() {
		// TODO Auto-generated method stub
		return this.userRolesDAO.listUserRoles();
	}

	@Override
	@Transactional
	public UserRoles getUserRolesById(int id) {
		// TODO Auto-generated method stub
		return this.userRolesDAO.getUserRolesById(id);
	}
	
	@Override
	@Transactional
	public List<UserRoles> getUserRolesByDepartment(String department) {
		// TODO Auto-generated method stub
		return this.userRolesDAO.getUserRolesByDepartment(department);
	}

	@Override
	@Transactional
	public void removeUserRoles(int id) {
		// TODO Auto-generated method stub
		this.userRolesDAO.removeUserRoles(id);
	}

}

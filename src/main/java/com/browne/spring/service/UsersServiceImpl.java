package com.browne.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.browne.spring.dao.UsersDAO;
import com.browne.spring.model.Users;

@Service("usersService")
public class UsersServiceImpl implements UsersService {
	
	@Autowired
	private UsersDAO usersDAO;
	
	public void setUsersDAO(UsersDAO usersDAO) {
		this.usersDAO = usersDAO;
	}

	@Override
	@Transactional
	public void addUsers(Users users) {
		// TODO Auto-generated method stub
		this.usersDAO.addUsers(users);
	}

	@Override
	@Transactional
	public void updateUsers(Users users) {
		// TODO Auto-generated method stub
		this.usersDAO.updateUsers(users);
	}

	@Override
	@Transactional
	public List<Users> listUsers() {
		// TODO Auto-generated method stub
		return this.usersDAO.listUsers();
	}

	@Override
	@Transactional
	public Users getUsersById(String id) {
		// TODO Auto-generated method stub
		return this.usersDAO.getUsersById(id);
	}
	
	@Override
	@Transactional
	public List<Users> getUsersByResetToken(String reset_token) {
		// TODO Auto-generated method stub
		return this.usersDAO.getUsersByResetToken(reset_token);
	}

	@Override
	@Transactional
	public void removeUsers(String id) {
		// TODO Auto-generated method stub
		this.usersDAO.removeUsers(id);
	}

}

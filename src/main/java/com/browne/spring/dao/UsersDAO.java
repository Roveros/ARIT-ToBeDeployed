package com.browne.spring.dao;


import java.util.List;

import com.browne.spring.model.Users;

public interface UsersDAO {
	public void addUsers(Users users);
	public void updateUsers(Users users);
	public List<Users> listUsers();
	public Users getUsersById(String id);
	public List<Users> getUsersByResetToken(String reset_token);
	public void removeUsers(String id);
}

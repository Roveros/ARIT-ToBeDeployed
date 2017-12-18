package com.browne.spring.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.browne.spring.model.Users;

@Repository("usersDAO")
public class UsersDAOImpl extends AbstractDAO<String, Users> implements UsersDAO {

	private static final Logger logger = LoggerFactory.getLogger(UsersDAOImpl.class);
	
	@Override
	public void addUsers(Users users) {
		// TODO Auto-generated method stub
		persist(users);
		logger.info("Users saved successfully, Incident Details="+users.toString());
	}

	@Override
	public void updateUsers(Users users) {
		// TODO Auto-generated method stub
		getSession().update(users);
		logger.info("Users updated successfully, Users Details="+users.toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Users> listUsers() {
		// TODO Auto-generated method stub
        Criteria criteria = createEntityCriteria();		
        logger.info("Users List::"+criteria.list().toString());		
        return (List<Users>) criteria.list();
	}

	@Override
	public Users getUsersById(String id) {
		// TODO Auto-generated method stub
		logger.info("User loaded successfully, Users details="+getByKey(id).toString());
		return getByKey(id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Users> getUsersByResetToken(String reset_token) {
        Criteria criteria = createEntityCriteria().add(Restrictions.in( "reset_token", new String[] { reset_token } ));		
        logger.info("User located by reset_token: "+reset_token+"::"+criteria.list().toString());		
        return (List<Users>) criteria.list();
	}

	@Override
	public void removeUsers(String id) {
		// TODO Auto-generated method stub
		logger.info("Users deleted successfully, Users details="+getByKey(id).toString());
		delete(getByKey(id));

	}

}

package com.browne.spring.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.browne.spring.model.UserRoles;
import com.browne.spring.model.Users;

@Repository("userRolesDAO")
public class UserRolesDAOImpl extends AbstractDAO<Integer, UserRoles> implements UserRolesDAO {

	private static final Logger logger = LoggerFactory.getLogger(UserRolesDAOImpl.class);
	
	@Override
	public void addUserRoles(UserRoles userRoles) {
		// TODO Auto-generated method stub
		persist(userRoles);
		logger.info("User Roles saved successfully, User Roles Details="+userRoles.toString());
	}	

	@Override
	public void updateUserRoles(UserRoles userRoles) {
		// TODO Auto-generated method stub		
		getSession().update(userRoles);
		logger.info("User Roles updated successfully, User Roles Details="+userRoles.toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserRoles> listUserRoles() {
        Criteria criteria = createEntityCriteria();		
       // logger.info("User Roles List::"+criteria.list().toString());		
        return (List<UserRoles>) criteria.list();
	}

	@Override
	public UserRoles getUserRolesById(int id) {
		logger.info("User Roles loaded successfully, User Roles details="+getByKey(id).toString());
		return getByKey(id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserRoles> getUserRolesByDepartment(String department) {
        Criteria criteria = createEntityCriteria().add(Restrictions.in( "role", new String[] { department } ));		
        logger.info("User Roles located by department: "+department+"::"+criteria.list().toString());		
        return (List<UserRoles>) criteria.list();
	}

	@Override
	public void removeUserRoles(int id) {
		logger.info("User Roles deleted successfully, User Roles details="+getByKey(id).toString());
		delete(getByKey(id));
	}

}

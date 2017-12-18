package com.browne.spring.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.browne.spring.model.Incident;

@Repository("incidentDAO")
public class IncidentDAOImpl extends AbstractDAO<Integer, Incident> implements IncidentDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(IncidentDAOImpl.class);

	public void addIncident(Incident incident) {
		persist(incident);
		logger.info("Incident saved successfully, Incident Details="+incident.toString());
	}

	public void updateIncident(Incident incident) {
		
		getSession().update(incident);
		logger.info("Incident updated successfully, Incident Details="+incident.toString());
	}
	
	@SuppressWarnings("unchecked")
	public List<Incident> listIncidents() {
        Criteria criteria = createEntityCriteria();		
        logger.info("Incident List::"+criteria.list().toString());		
        return (List<Incident>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Incident> listIncidentsByLecturer(String lecturer) {
        Criteria criteria = createEntityCriteria().add(Restrictions.in( "lecturer", new String[] { lecturer } ));		
        logger.info("Incident List by Lecturer "+lecturer+"::"+criteria.list().toString());		
        return (List<Incident>) criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Incident> listIncidentsByDepartment(String department) {
        Criteria criteria = createEntityCriteria().add(Restrictions.in( "department", new String[] { department } ));		
        logger.info("Incident List by Department "+department+"::"+criteria.list().toString());		
        return (List<Incident>) criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Incident> listNotificationsByLecturer(String lecturer, String stage) {
        Criteria criteria = createEntityCriteria().add(Restrictions.in( "lecturer", new String[] { lecturer } )).add(Restrictions.like("stage", stage, MatchMode.EXACT));		
        logger.info("Notification List by Lecturer "+lecturer+"::"+criteria.list().toString());		
        return (List<Incident>) criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Incident> listNotificationsByDepartment(String department, String stage) {
        Criteria criteria = createEntityCriteria().add(Restrictions.in( "department", new String[] { department } )).add(Restrictions.like("stage", stage, MatchMode.EXACT));		
        logger.info("Notification List by Department "+department+"::"+criteria.list().toString());		
        return (List<Incident>) criteria.list();
	}
	
	public String determineFirstOccurrence(String studentNumber) {
        Criteria criteria = createEntityCriteria().add(Restrictions.in( "student_number", new String[] { studentNumber } )).add(Restrictions.like("plagiarism_confirmed", "Yes", MatchMode.EXACT));		
        logger.info("First occuracnce by Student Number "+studentNumber+"::"+criteria.list().toString());	
        if (criteria.list().isEmpty()) {
			return "Yes";
		}else {
			return "No";
		}       
	}

	public Incident getIncidentById(int id) {
		logger.info("Incident loaded successfully, Incident details="+getByKey(id).toString());
		return getByKey(id);
	}
	
	public void removeIncident(int id) {
		logger.info("Incident deleted successfully, Incident details="+getByKey(id).toString());
		delete(getByKey(id));

	}

}
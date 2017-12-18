package com.browne.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.browne.spring.dao.IncidentDAO;
import com.browne.spring.model.Incident;

@Service("incidentService")
public class IncidentServiceImpl implements IncidentService {
	
	@Autowired
	private IncidentDAO incidentDAO;
	
	public void setIncidentDAO(IncidentDAO incidentDAO) {
		this.incidentDAO = incidentDAO;
	}

	@Override
	@Transactional
	public void addIncident(Incident incident) {
		// TODO Auto-generated method stub
		this.incidentDAO.addIncident(incident);
	}

	@Override
	@Transactional
	public void updateIncident(Incident incident) {
		// TODO Auto-generated method stub
		this.incidentDAO.updateIncident(incident);
	}

	@Override
	@Transactional
	public List<Incident> listIncidents() {
		// TODO Auto-generated method stub
		return this.incidentDAO.listIncidents();
	}
	
	@Override
	@Transactional
	public List<Incident> listIncidentsByLecturer(String lecturer) {
		// TODO Auto-generated method stub
		return this.incidentDAO.listIncidentsByLecturer(lecturer);
	}
	
	@Override
	@Transactional
	public List<Incident> listIncidentsByDepartment(String department) {
		// TODO Auto-generated method stub
		return this.incidentDAO.listIncidentsByDepartment(department);
	}
	
	@Override
	@Transactional
	public List<Incident> listNotificationsByLecturer(String lecturer, String stage) {
		// TODO Auto-generated method stub
		return this.incidentDAO.listNotificationsByLecturer(lecturer, stage);
	}
	
	@Override
	@Transactional
	public List<Incident> listNotificationsByDepartment(String department, String stage) {
		// TODO Auto-generated method stub
		return this.incidentDAO.listNotificationsByDepartment(department, stage);
	}
	
	
	@Override
	@Transactional
	public String determineFirstOccurrence(String studentNumber) {
		// TODO Auto-generated method stub
		return this.incidentDAO.determineFirstOccurrence(studentNumber);
	}

	@Override
	@Transactional
	public Incident getIncidentById(int id) {
		// TODO Auto-generated method stub
		return this.incidentDAO.getIncidentById(id);
	}

	@Override
	@Transactional
	public void removeIncident(int id) {
		// TODO Auto-generated method stub
		this.incidentDAO.removeIncident(id);
	}
	


}

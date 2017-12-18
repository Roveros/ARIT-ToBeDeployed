package com.browne.spring.dao;

import java.util.List;

import com.browne.spring.model.Incident;

public interface IncidentDAO {
	
	public void addIncident(Incident incident);
	public void updateIncident(Incident incident);
	public List<Incident> listIncidents();
	public List<Incident> listIncidentsByLecturer(String lecturer);
	public List<Incident> listIncidentsByDepartment(String department);
	public List<Incident> listNotificationsByLecturer(String lecturer, String stage);
	public List<Incident> listNotificationsByDepartment(String department, String stage);
	public String determineFirstOccurrence(String studentNumber);
	public Incident getIncidentById(int id);
	public void removeIncident(int id);
}

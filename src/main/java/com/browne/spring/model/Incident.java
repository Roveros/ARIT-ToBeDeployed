package com.browne.spring.model;



import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="INCIDENTS")
public class Incident {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;	
	private String student_name;
	private String student_number;
	private String course_description;
	private int year;
	private String module_code;
	private String lecturer;
	private String department;
	private String plagiarism;
	private String summary;
	private String first_occurrence;
	private String stage;
	private String plagiarism_confirmed;
	private String sanction;
	private int plagiarism_level;
	private String meeting_attended;
	private String sanction_accepted;
	private String uncooperative;
	private Date date_created;	
	private String status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStudent_name() {
		return student_name;
	}

	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}

	public String getStudent_number() {
		return student_number;
	}

	public void setStudent_number(String student_number) {
		this.student_number = student_number;
	}

	public String getCourse_description() {
		return course_description;
	}

	public void setCourse_description(String course_description) {
		this.course_description = course_description;
	}

	public String getYear() {
		return String.valueOf(year);
	}

	public void setYear(String year) {
		this.year = Integer.valueOf(year);
	}

	public String getModule_code() {
		return module_code;
	}

	public void setModule_code(String module_code) {
		this.module_code = module_code;
	}

	public String getLecturer() {
		return lecturer;
	}

	public void setLecturer(String lecturer) {
		this.lecturer = lecturer;
	}	

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPlagiarism() {
		return plagiarism;
	}

	public void setPlagiarism(String plagiarism) {
		this.plagiarism = plagiarism;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}	

	public String getFirst_occurrence() {
		return first_occurrence;
	}

	public void setFirst_occurrence(String first_occurrence) {
		this.first_occurrence = first_occurrence;
	}	

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getPlagiarism_confirmed() {
		return plagiarism_confirmed;
	}

	public void setPlagiarism_confirmed(String plagiarism_confirmed) {
		this.plagiarism_confirmed = plagiarism_confirmed;
	}

	public String getSanction() {
		return sanction;
	}

	public void setSanction(String sanction) {
		this.sanction = sanction;
	}

	public int getPlagiarism_level() {
		return plagiarism_level;
	}

	public void setPlagiarism_level(int plagiarism_level) {
		this.plagiarism_level = plagiarism_level;
	}

	public String getMeeting_attended() {
		return meeting_attended;
	}

	public void setMeeting_attended(String meeting_attended) {
		this.meeting_attended = meeting_attended;
	}

	public String getSanction_accepted() {
		return sanction_accepted;
	}

	public void setSanction_accepted(String sanction_accepted) {
		this.sanction_accepted = sanction_accepted;
	}

	public String getUncooperative() {
		return uncooperative;
	}

	public void setUncooperative(String uncooperative) {
		this.uncooperative = uncooperative;
	}

	public Date getDate_created() {
		return date_created;
	}

	public void setDate_created(Date date_created) {
		this.date_created = date_created;

	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString(){
		return "id = "+id+", name = "+student_name+", number = "+student_number+", Status: "+status;
	}
	
	public String details() {
		return "Date: "+date_created+", Stage = "+stage+", Plagiarism Confirmed ="+plagiarism_confirmed+", Summary: " +summary;
		
	}

}

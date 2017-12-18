package com.browne.spring.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.browne.spring.model.FileBucket;
import com.browne.spring.model.Incident;
import com.browne.spring.model.MailObject;
import com.browne.spring.model.Policy;
import com.browne.spring.model.RecipientInfo;
import com.browne.spring.service.AutoMailService;
import com.browne.spring.service.IncidentService;
import com.browne.spring.service.UserRolesService;


@Controller
public class IncidentController {	
	
	@Autowired
	IncidentService incidentService;
	
	@Autowired
	UserRolesService userRolesService;
	
	//Gets the order service for mail services
	@Autowired
	AutoMailService autoMailService;
	
	Authentication auth;
	String username; 
	
	//@Bean
	@Autowired(required=true)
	@Qualifier(value="incidentService")
	public void setIncidentService(IncidentService incidentService){
		this.incidentService = incidentService;
	}	
	
	//@Bean
	@Autowired(required=true)
	@Qualifier(value="userRolesService")
	public void setUserRolesService(UserRolesService userRolesService){
		this.userRolesService = userRolesService;
	}

	@RequestMapping(value = { "/incidents"}, method = RequestMethod.GET)
	public ModelAndView incidentsPage() {
		ModelAndView model = new ModelAndView();
		
		auth = SecurityContextHolder.getContext().getAuthentication();
		username = auth.getName(); //get logged in username
		
		model.addObject("username", username);
		model.addObject("incident", new Incident());
		
		if (auth.getAuthorities().toString().equals("[ROLE_USER]")) {
			model.addObject("listIncidents", this.incidentService.listIncidentsByLecturer(username));
		} else {
			model.addObject("listIncidents", this.incidentService.listIncidentsByDepartment(auth.getAuthorities().toString()));			
		}
		
		model.setViewName("incidents");
		return model;
	}
	
	@RequestMapping(value = "/incident/{id}", method = RequestMethod.GET)
	public String getIncident(@PathVariable("id") int id, Model model) {
		//model.addAttribute("incident", new Incident());
		model.addAttribute("incident", this.incidentService.getIncidentById(id));
		model.addAttribute("procedure", new Policy().getPolicy(this.incidentService.getIncidentById(id).getStage()));
        FileBucket fileModel = new FileBucket();
        model.addAttribute("fileBucket", fileModel);
		return "incident";
	}
	
	//For adding and updating incidents
	@RequestMapping(value= "/incident/add", method = RequestMethod.POST)
	public String addIncident(@ModelAttribute("incident") Incident incident ){		
		
		if(incident.getId() == 0){ 	//If ID == 0 then it is a new incident record			
	        long millis=System.currentTimeMillis();  
	        java.sql.Date date=new java.sql.Date(millis);  
	        incident.setDate_created(date);
	        incident.setFirst_occurrence(this.incidentService.determineFirstOccurrence(incident.getStudent_number()));
	        incident.setStatus("Pending Incident Material Upload");
	        
	        if (incident.getFirst_occurrence().equals("No")) {
				incident.setPlagiarism_level(2);
			} else {
				incident.setPlagiarism_level(1);
			}
	        
	        if (this.incidentService.determineFirstOccurrence(incident.getStudent_number()).equals("Yes")) {
				incident.setStage("1a");     	
	        	String department =  incident.getDepartment().substring(1, incident.getDepartment().length()-1);
	        	String HODemail = userRolesService.getUserRolesByDepartment(department).get(0).getUsers().getUsername();
	        	
	        	//Sending mail
	        	String message, status, subject;  
	        	subject = "New ARIT Incident Notification - [Automated Response]";
	        	status = "ARIT did not detect previous sanctions";
	        	message = "Hello " + HODemail + ",\n\n"
	            		+ "The Assesment Related Incident Tool (ARIT) did not detect previous instances of "
	            		+ "plagiarism for an incident. If there are any incident records not currently stored"
	            		+ " on the ARIT system that indicate that there has been a previous instance of "
	            		+ "plagiarism for the student linked to this incident then please update the ARIT record."
	            		+ "\n\nIncident ID: " + incident.getId() + "\nStatus: "+status;

	        	autoMailService.sendAutoMail(getMailObject(incident.getId(), subject, message, status, HODemail));

			} else {
				incident.setStage("2a");
				incident.setPlagiarism_level(2);
				incident.setStatus("Pending Tutor Material Upload");
			}
	        
	        //put n/a here
	        incident.setLecturer(username);
	        incident.setPlagiarism_confirmed("n/a");
	        incident.setSanction("n/a");
	        incident.setMeeting_attended("n/a");
	        incident.setSanction_accepted("n/a");
	        incident.setUncooperative("n/a");
			
			//new person, add it
			this.incidentService.addIncident(incident);
		}else{  //updating existing record
			if (incident.getFirst_occurrence() == null) { //User is updating
				incident.setStage(this.incidentService.getIncidentById(incident.getId()).getStage());
				if (incident.getPlagiarism_confirmed() == null) {
					incident.setPlagiarism_confirmed(this.incidentService.getIncidentById(incident.getId()).getPlagiarism_confirmed());
				}				
				incident.setFirst_occurrence(this.incidentService.getIncidentById(incident.getId()).getFirst_occurrence());
			} else if (incident.getDepartment() == null) { //HoD is updating
				incident.setStudent_name(this.incidentService.getIncidentById(incident.getId()).getStudent_name());
				incident.setStudent_number(this.incidentService.getIncidentById(incident.getId()).getStudent_number());
				incident.setCourse_description(this.incidentService.getIncidentById(incident.getId()).getCourse_description());
				incident.setModule_code(this.incidentService.getIncidentById(incident.getId()).getModule_code());
				incident.setSummary(this.incidentService.getIncidentById(incident.getId()).getSummary());
				
				incident.setStage(this.incidentService.getIncidentById(incident.getId()).getStage());
				incident.setYear(this.incidentService.getIncidentById(incident.getId()).getYear());
				incident.setDepartment(this.incidentService.getIncidentById(incident.getId()).getDepartment());
				incident.setPlagiarism(this.incidentService.getIncidentById(incident.getId()).getPlagiarism());
				
				incident.setPlagiarism_level(this.incidentService.getIncidentById(incident.getId()).getPlagiarism_level());
				incident.setMeeting_attended(this.incidentService.getIncidentById(incident.getId()).getMeeting_attended());
				incident.setSanction_accepted(this.incidentService.getIncidentById(incident.getId()).getSanction_accepted());
				incident.setUncooperative(this.incidentService.getIncidentById(incident.getId()).getUncooperative());
				
				if (!incident.getStage().contains("3")) {
					incident.setStatus(this.incidentService.getIncidentById(incident.getId()).getStatus());
					incident.setSanction(this.incidentService.getIncidentById(incident.getId()).getSanction());
				}				
			}

			String stage = this.incidentService.getIncidentById(incident.getId()).getStage();
			String confirmation = incident.getPlagiarism_confirmed();
			System.err.println("Stage: "+stage+", Confirmation: "+confirmation);
			
			/*
			 * Setting stage. Begins 1a or 2a - No upload. FileUploadController sets 1b or 2b - File uploaded.
			 * 1c or 2c Plagiarism confirmation from HoD
			 */
			
			//HoD confirmation stage change
			if (stage.endsWith("b") && !confirmation.equals("n/a")) {
				if (confirmation.equals("No")) {
					incident.setStage(stage.replace('b', 'd'));
					incident.setStatus("Closed");
				} else {
					incident.setStage(stage.replace('b', 'c'));
					incident.setStatus("Active");
				}
				
	        	//Sending mail
	        	String message, status, subject, email;  
	        	email = incident.getLecturer();
	        	subject = "New ARIT Incident Notification - [Automated Response]";
	        	status = "Plagiarism Confirmation";
	        	message = "Hello " + email + ",\n\nConfirmation of the material uploaded has been provided. "
	        			+ "Please log into the ARIT system to continue processing the incident."
	        			+ "\n\nIncident ID: " + incident.getId() + "\nStatus: "+status;

	        	autoMailService.sendAutoMail(getMailObject(incident.getId(), subject, message, status, email));	      
			}
			
			//Plagiarism level manual set by user
			int level = incident.getPlagiarism_level();
			//Setting the stage
			if (stage.startsWith("1") && level == 2) {				
				incident.setStage("2a");
				incident.setPlagiarism_confirmed("n/a");
				incident.setMeeting_attended("n/a");
				incident.setUncooperative("n/a");
				incident.setSanction_accepted("n/a");
				incident.setStatus("Pending Tutor Material Upload");
				
	        	//Sending mail
	        	String message, status, subject, email;  
	        	email = incident.getLecturer();
	        	subject = "New ARIT Incident Notification - [Automated Response]";
	        	status = "Plagiarism Level Changed";
	        	message = "Hello " + email + ",\n\nThe plagiarism level of this incident has changed. "
	        			+ "Please log into the ARIT system to continue processing the incident."
	        			+ "\n\nIncident ID: " + incident.getId() + "\nStatus: "+status;

	        	autoMailService.sendAutoMail(getMailObject(incident.getId(), subject, message, status, email));		        
			}			
			
			//Setting stage by status
			String status = incident.getStatus();
			if ((status.equals("Closed") && !stage.contains("d"))) {
				char replaceMe[] = stage.toCharArray();
				incident.setStage(String.valueOf(replaceMe[0]) + 'd');
			} else if (status.equals("Appealed") && !stage.contains("d")) {
				incident.setStage("2a");
				incident.setPlagiarism_confirmed("n/a");
				incident.setMeeting_attended("n/a");
				incident.setUncooperative("n/a");
				incident.setSanction_accepted("n/a");
			}
			
			//Setting stage based on level 2 criteria
			String meetingAttended = incident.getMeeting_attended();
			String sanctionAccepted = incident.getSanction_accepted();
			String uncooperative = incident.getUncooperative();
			if (meetingAttended.equals("No") && uncooperative.equals("Yes")) {
				incident.setStage("3f");
				incident.setStatus("Refered to Registrar");
				incident.setPlagiarism_level(3);
	        	String department =  incident.getDepartment().substring(1, incident.getDepartment().length()-1);
	        	String HODemail = userRolesService.getUserRolesByDepartment(department).get(0).getUsers().getUsername();
	        	
	        	//Sending mail
	        	String message, msgStatus, subject;  
	        	subject = "New ARIT Incident Notification - [Automated Response]";
	        	msgStatus = "Registrar Referral";
	        	message = "Hello " + HODemail + ",\n\nThis incident may require a referral to the registrar. "
	        			+ "Please log into the ARIT system to continue processing the incident."
	        			+ "\n\nIncident ID: " + incident.getId() + "\nStatus: "+status;	        	
	        	autoMailService.sendAutoMail(getMailObject(incident.getId(), subject, message, msgStatus, HODemail));
			} else if (sanctionAccepted.equals("No") && incident.getPlagiarism_level()==2 ) {
				incident.setStage("3e");
				incident.setPlagiarism_level(3);
				incident.setStatus("Awaiting Sanctions");
	        	String department =  incident.getDepartment().substring(1, incident.getDepartment().length()-1);
	        	String HODemail = userRolesService.getUserRolesByDepartment(department).get(0).getUsers().getUsername();
	        	
	        	//Sending mail
	        	String message, msgStatus, subject;  
	        	subject = "New ARIT Incident Notification - [Automated Response]";
	        	msgStatus = "Awaiting Sanctions";
	        	message = "Hello " + HODemail + ",\n\nThe head of department must determine the applicaiton of "
	        			+ "level 1 or 2 sanctions on this incident. Please log into the ARIT system to continue "
	        			+ "processing the incident.\n\nIncident ID: " + incident.getId() + "\nStatus: "+status;
	        	autoMailService.sendAutoMail(getMailObject(incident.getId(), subject, message, msgStatus, HODemail));

			}
			
			//Setting stage based on first occurance changes
			String modelFirstOccurrence = incident.getFirst_occurrence();
			String storedFirstOccurrence = this.incidentService.getIncidentById(incident.getId()).getFirst_occurrence();
			if (modelFirstOccurrence.equals("No") && storedFirstOccurrence.equals("Yes") ) {
				incident.setStage(stage.replace('1', '2'));
				incident.setPlagiarism_level(2);
				incident.setPlagiarism_confirmed("n/a");
				incident.setMeeting_attended("n/a");
				incident.setUncooperative("n/a");
				incident.setSanction_accepted("n/a");
				
	        	//Sending mail
	        	String message, msgStatus, subject, email;  
	        	email = incident.getLecturer();
	        	subject = "New ARIT Incident Notification - [Automated Response]";
	        	msgStatus = "First Occurrence Changed";
	        	message = "Hello " + email + ",\n\nThis incident has had its plagiarism level changed. "
	        			+ "Please log into the ARIT system to view this incident.\n\n"
	        			+ "Incident ID: " + incident.getId() + "\nStatus: "+status;

	        	autoMailService.sendAutoMail(getMailObject(incident.getId(), subject, message, msgStatus, email));

			} else if (modelFirstOccurrence.equals("Yes") && storedFirstOccurrence.equals("No") ) {
				incident.setStage(stage.replace('2', '1'));
				incident.setPlagiarism_level(1);
				
	        	//Sending mail
	        	String message, msgStatus, subject, email;  
	        	email = incident.getLecturer();
	        	subject = "New ARIT Incident Notification - [Automated Response]";
	        	msgStatus = "First Occurrence Changed";
	        	message = "Hello " + email + ",\n\nThis incident has had its plagiarism level changed. "
	        			+ "Please log into the ARIT system to view this incident.\n\n"
	        			+ "Incident ID: " + incident.getId() + "\nStatus: "+status;
	        	
	        	autoMailService.sendAutoMail(getMailObject(incident.getId(), subject, message, msgStatus, email));
			}				
			this.incidentService.updateIncident(incident);
			
		}			
		return "redirect:/incidents";		
	}
	
	@RequestMapping("/remove/{id}")
    public String removeIncident(@PathVariable("id") int id){
		
        this.incidentService.removeIncident(id);
        return "redirect:/incidents";
    }
    
    @ModelAttribute("yearList")
    public Map<String, String> getYearList()
    {
       Map<String, String> yearList = new HashMap<String, String>();
       yearList.put("1", "Year 1");
       yearList.put("2", "Year 2");
       yearList.put("3", "Year 3");
       yearList.put("4", "Year 4");
       return yearList;
    }
    
    @ModelAttribute("numberList")
    public Map<String, String> getNumberList()
    {
       Map<String, String> numberList = new HashMap<String, String>();
       numberList.put("1", "1");
       numberList.put("2", "2");
       numberList.put("3", "3");
       numberList.put("4", "4");
       return numberList;
    }
    
    @ModelAttribute("choiceList")
    public Map<String, String> getchoiceList()
    {
    	Map<String, String> choiceList = new HashMap<String, String>();
       	choiceList.put("No", "No");
       	choiceList.put("Yes", "Yes");       
       	return choiceList;
    }
    
    @ModelAttribute("departmentList")
    public Map<String, String> getdepartmentList()
    {
    	Map<String, String> departmentList = new HashMap<String, String>();
       	departmentList.put("[ROLE_HOD_DPT1]", "Department 1");
       	departmentList.put("[ROLE_HOD_DPT2]", "Department 2");   
       	departmentList.put("[ROLE_HOD_DPT3]", "Department 3");
       	departmentList.put("[ROLE_HOD_DPT4]", "Department 4");
       	return departmentList;
    }
    
    @ModelAttribute("statusList")
    public Map<String, String> getStatusList()
    {
    	Map<String, String> statusList = new HashMap<String, String>();
    	statusList.put("Awaiting Sanctions", "Awaiting Sanctions");
    	statusList.put("Refer to Registrar", "Refer to Registrar");
    	statusList.put("Pending Tutor Material Upload", "Pending Tutor Material Upload");
    	statusList.put("Pending Incident Material Upload", "Pending Incident Material Upload");
    	statusList.put("Pending Incident Material Upload", "Pending Incident Material Upload");
    	statusList.put("Pending Plagiarism Confirmation", "Pending Plagiarism Confirmation");   
    	statusList.put("Active", "Active");
    	statusList.put("Appealed", "Appealed");
    	statusList.put("Closed", "Closed");
       	return statusList;
    }
    
    @ModelAttribute("notifications")
    public String getNotifications()
    {   
		auth = SecurityContextHolder.getContext().getAuthentication();
		username = auth.getName(); //get logged in username
		
		if (!auth.getAuthorities().toString().equals("[ROLE_ADMIN]")){
	    	java.util.List<Incident> list; 
	    	String notifications = "";
	    	int i = 0;

	    	
			if (auth.getAuthorities().toString().equals("[ROLE_USER]")) {
				list = this.incidentService.listNotificationsByLecturer(username, "1c");
				list.addAll((this.incidentService.listNotificationsByLecturer(username, "2b")));
				list.addAll((this.incidentService.listNotificationsByLecturer(username, "2c")));
			} else { //i.e ROLE_HOD_SPORTS		
				list = this.incidentService.listNotificationsByDepartment(auth.getAuthorities().toString(), "1b");
				list.addAll(this.incidentService.listNotificationsByDepartment(auth.getAuthorities().toString(), "3e"));
				list.addAll(this.incidentService.listNotificationsByDepartment(auth.getAuthorities().toString(), "3f"));
			}
			
			i = list.size();
			
			
			if (i != 0) {
				notifications = " (" + String.valueOf(i) + ")";
			}
			
			return notifications;
		}
		return "0";

    }
    
    public static MailObject getMailObject(int id, String subject, String message, String status, String email){
    	MailObject object = new MailObject();
        object.setId(String.valueOf(id));
        object.setStatus(status);
         
        RecipientInfo recipientInfo = new RecipientInfo();
        recipientInfo.setEmail(email);
        object.setRecipientInfo(recipientInfo);

        object.setMessage(message);
        object.setSubject(subject);
        
        return object;
    }
	
}

package com.browne.spring.controller;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.browne.spring.dao.UserRolesDAOImpl;
import com.browne.spring.model.Incident;
import com.browne.spring.model.UserRoles;
import com.browne.spring.service.IncidentService;
import com.browne.spring.service.UserRolesService;
import com.browne.spring.service.UsersService;

@Controller
public class AritController {
	
	private static final Logger logger = LoggerFactory.getLogger(AritController.class);
	
	@Autowired
	IncidentService incidentService;
	
	@Autowired
	UsersService usersService;
	
	@Autowired
	UserRolesService userRolesService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
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
	@Qualifier(value="usersService")
	public void setUsersService(UsersService usersService){
		this.usersService = usersService;
	}
	
	//@Bean
	@Autowired(required=true)
	@Qualifier(value="userRolesService")
	public void setUserRolesService(UserRolesService userRolesService){
		this.userRolesService = userRolesService;
	}	

	/*
	 * Creates a model and view, setting the view name and returning the model as a response to the "\" HTTP GET request
	 */
	@RequestMapping(value = { "/notifications"}, method = RequestMethod.GET)
	public ModelAndView notificationsPage() {
		ModelAndView model = new ModelAndView();
		
		auth = SecurityContextHolder.getContext().getAuthentication();
		username = auth.getName(); //get logged in username
		
		model.addObject("username", username);
		model.addObject("incident", new Incident());
		java.util.List<Incident> list; 
		
		if (auth.getAuthorities().toString().equals("[ROLE_USER]")) {
			list = this.incidentService.listNotificationsByLecturer(username, "1c");
			list.addAll((this.incidentService.listNotificationsByLecturer(username, "2b")));
			list.addAll((this.incidentService.listNotificationsByLecturer(username, "2c")));
			model.addObject("listIncidents", list);
		} else { //i.e ROLE_HOD_SPORTS	
			list = this.incidentService.listNotificationsByDepartment(auth.getAuthorities().toString(), "1b");
			list.addAll(this.incidentService.listNotificationsByDepartment(auth.getAuthorities().toString(), "3e"));
			list.addAll(this.incidentService.listNotificationsByDepartment(auth.getAuthorities().toString(), "3f"));
			model.addObject("listIncidents", list);
		}	
		model.setViewName("notifications");
		return model;
	}
	
	@RequestMapping(value = { "/admin"}, method = RequestMethod.GET)
	public ModelAndView getAdminPage() {
		ModelAndView model = new ModelAndView();
		
		auth = SecurityContextHolder.getContext().getAuthentication();
		username = auth.getName(); //get logged in username
		
		model.addObject("username", username);
		
		model.addObject("userRoles", new UserRoles());
		model.addObject("listUserRoles", this.userRolesService.listUserRoles());
		model.setViewName("admin");
		return model;
	}
	
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public String getUser(@PathVariable("id") int id, Model model) {
		model.addAttribute("userRoles", this.userRolesService.getUserRolesById(id));
		//username = this.userRolesService.getUserRolesById(id).getUsers().getUsername();
		return "user";
	}
	
	//For adding and updating incidents
	@RequestMapping(value= "/admin/add", method = RequestMethod.POST)
	public String addUser(@ModelAttribute("userRoles") UserRoles userRoles){
		
		logger.info("User Roles to save. User Roles Details= "+userRoles.toString());
		String storedPassword = "", formPassword = "";
		formPassword = userRoles.getUsers().getPassword();
		
		//userRoles.getUsers().setPassword(passwordEncoder.encode(formPassword));
		
		if(userRoles.getUser_role_id() == 0){			
			//new person, add it
			userRoles.getUsers().setPassword(passwordEncoder.encode(formPassword));
			this.usersService.addUsers(userRoles.getUsers());
			this.userRolesService.addUserRoles(userRoles);
		}else{	
			boolean passChanged;
			try {				
				storedPassword = userRolesService.getUserRolesById(userRoles.getUser_role_id()).getUsers().getPassword();	
				if (formPassword.startsWith("$")) {
					passChanged = false;
				} else {
					passChanged = true;
					userRoles.getUsers().setPassword(passwordEncoder.encode(formPassword));
				}
				
				logger.info("Matching: " + passChanged);
				logger.info("Form password: "+formPassword+ ", DB password:" + storedPassword);
				
			} catch (NullPointerException ex) {
				// TODO Auto-generated catch block
				storedPassword = "";
				passChanged = true;
				userRoles.getUsers().setPassword(passwordEncoder.encode(formPassword));
				//ex.printStackTrace();
			}
			
			//existing person, call update			
			String storedName = this.userRolesService.getUserRolesById(userRoles.getUser_role_id()).getUsers().getUsername();
			String entityName = userRoles.getUsers().getUsername();
			logger.info("Stored Primay Key Name: " + storedName);
			
			if (storedName.equals(entityName)) {
				this.usersService.updateUsers(userRoles.getUsers());
				this.userRolesService.updateUserRoles(userRoles);
			} else {
				String name = userRolesService.getUserRolesById(userRoles.getUser_role_id()).getUsers().getUsername();
				//System.err.println("Name:" +name);
				this.userRolesService.removeUserRoles(userRoles.getUser_role_id());
				this.usersService.removeUsers(name);
				
				userRoles.setUser_role_id(0);
				this.usersService.addUsers(userRoles.getUsers());
				this.userRolesService.addUserRoles(userRoles);
			}
		}	

		return "redirect:/admin";
		
	}
	
	@RequestMapping("/removeUser/{id}")
    public String removeIncident(@PathVariable("id") int id){
		String username = userRolesService.getUserRolesById(id).getUsers().getUsername();
		this.userRolesService.removeUserRoles(id);
		this.usersService.removeUsers(username);		
       
        return "redirect:/admin";
    }
	
	
	@RequestMapping("/403")
	public String accessDenied() {
	    return "403";
	}
	
	@RequestMapping("/default")
	public String redirect() {
		if (!auth.getAuthorities().toString().equals("[ROLE_ADMIN]")){
			return "redirect:/incidents";
		} else {
			return "redirect:/admin";
		}
	    
	}
	
	/*
	 * As above but the request mapped method takes parameters from the request,
	 * These parameters are not required.
	 */
	@RequestMapping(value = {"/","/login"}, method = RequestMethod.GET)
	public ModelAndView loginPage(@RequestParam(value = "error",required = false) String error,
	@RequestParam(value = "logout",	required = false) String logout) {
		
		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid Credentials provided.");
		}

		if (logout != null) {

			model.addObject("message", "Logged out from ARIT successfully.");
		}		

		model.setViewName("login");
		return model;
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
    
    
    @ModelAttribute("departmentList")
    public HashMap<String, String> getdepartmentList()
    {
    	HashMap<String, String> departmentList = new HashMap<String, String>();
       	departmentList.put("ROLE_HOD_DPT1", "Department 1");
       	departmentList.put("ROLE_HOD_DPT2", "Department 2");  
       	departmentList.put("ROLE_HOD_DPT3", "Department 3");
       	departmentList.put("ROLE_HOD_DPT4", "Department 4");
       	return departmentList;
    }
    
    @ModelAttribute("roleList")
    public HashMap<String, String> getRoleList()
    {
    	HashMap<String, String> roleList = new HashMap<String, String>();
    	roleList.put("ROLE_HOD_DPT1", "Department 1");
    	roleList.put("ROLE_HOD_DPT2", "Department 2");  
    	roleList.put("ROLE_HOD_DPT3", "Department 3");
    	roleList.put("ROLE_HOD_DPT4", "Department 4");
    	roleList.put("ROLE_ADMIN", "Administration");
    	roleList.put("ROLE_USER", "User");
       	return roleList;
    }
    
    @ModelAttribute("choiceList")
    public HashMap<Integer, String> getchoiceList()
    {
    	HashMap<Integer, String> choiceList = new HashMap<Integer, String>();
       	choiceList.put(1, "Yes");   
       	choiceList.put(0, "No");    
       	return choiceList;
    }

}

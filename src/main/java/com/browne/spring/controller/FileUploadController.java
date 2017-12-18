package com.browne.spring.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.browne.spring.model.RecipientInfo;
import com.browne.spring.model.UserRoles;
import com.browne.spring.model.FileBucket;
import com.browne.spring.model.Incident;
import com.browne.spring.model.MailObject;
import com.browne.spring.service.IncidentService;
import com.browne.spring.service.UserRolesService;
import com.browne.spring.service.AutoMailService;
import com.browne.spring.util.FileValidator;

@Controller
public class FileUploadController {

	//Gets the incident service for incident CRUD
	@Autowired
	IncidentService incidentService;	
	
	//Gets the incident service for incident CRUD
	@Autowired
	UserRolesService userRolesService;
	
	//Gets the order service for mail services
	@Autowired
	AutoMailService autoMailService;
	
	String dpt1 = "[ROLE_HOD_DPT1]";
	String dpt2 = "[ROLE_HOD_DPT2]";
	String dpt3 = "[ROLE_HOD_DPT3]";
	String dpt4 = "[ROLE_HOD_DPT4]";
	
    String dir[] = {"DPT1\\","DPT2\\","DPT3\\","DPT4\\"};
	
    private static String UPLOAD_LOCATION="C:\\ARIT\\";    
    
    //Custom validator for uploading files
    @Autowired
    FileValidator fileValidator;
     
    @InitBinder("fileBucket")
    protected void initBinderFileBucket(WebDataBinder binder) {
       binder.setValidator(fileValidator);
    }	
    
    //request method for form posts from incident pages
    @RequestMapping(value="/upload/{id}", method = RequestMethod.POST)
    public String singleFileUpload(@PathVariable("id") int id, @Valid FileBucket fileBucket, BindingResult result, ModelMap model, HttpServletRequest request) throws IOException {
 
    	String appBaseUrl = request.getScheme() + "://" + request.getServerName();
    	String fullPath = null;
    	
        if (result.hasErrors()) {
            System.out.println("validation errors");
            //System.err.println(result.getFieldError());
            return "fileUploader";
        } else {  
        	Incident incident = incidentService.getIncidentById(id);        	
        	String department =  incident.getDepartment().substring(1, incident.getDepartment().length()-1);
        	String HODemail = userRolesService.getUserRolesByDepartment(department).get(0).getUsers().getUsername();
        	
        	if (fileBucket.getAltEmail().contains("@")) {
				HODemail = fileBucket.getAltEmail();
			}  
        	
        	File file = null;
        	
        	if (incident.getDepartment().equals(dpt1)) {
        		fullPath = UPLOAD_LOCATION+dir[0];
				file = new File(fullPath);
			}else if (incident.getDepartment().equals(dpt2)) {
				fullPath = UPLOAD_LOCATION+dir[1];
				file = new File(fullPath);
			}else if (incident.getDepartment().equals(dpt3)) {
				fullPath = UPLOAD_LOCATION+dir[2];
				file = new File(fullPath);
			}else if (incident.getDepartment().equals(dpt4)) {
				fullPath = UPLOAD_LOCATION+dir[3];
				file = new File(fullPath);
			}      	
        	
        	//Sending material upload email
        	String message, status, subject;  
        	subject = "New ARIT Incident Notification - [Automated Response]";
        	status = "Material Upload";
        	message = "Hello " + HODemail + ",\n\n"
            		+ "You have a new incident notification on the Assesment Related Incident "
            		+ "Tool (ARIT).\n\nIncident ID: " + id + "\nStatus: "+status;
        	
        	if (fileBucket.getAltEmail().contains("@")) {
        		fullPath="C:\\ARIT\\External\\";
        		file = new File(fullPath);
        		String fullAppBaseUrl = appBaseUrl + "/download/External/"+id;
        		String lecturerEmail = incident.getLecturer();
        		message = "Hello " + HODemail + ",\n\n"
                		+ "This is an automated message from the Assesment Related Incident "
                		+ "Tool (ARIT). Please follow the following link to download material "
                		+ "related to a suspected assesment related incident and contact "+lecturerEmail+" "
                		+ "with your evaluation of the material.\n\nIncident ID: " + id + "\n"
                		+ "Status: "+status+"\nMaterial link: "+fullAppBaseUrl;
        	}        	
        	autoMailService.sendAutoMail(getMailObject(id, subject, message, status, HODemail));
        	
	        if (!file.exists()) {
	            if (file.mkdir()) {
	                System.out.println("Directory is created!");
	            } else {
	                System.out.println("Failed to create directory!");
	            }
	        }
        	
            System.out.println("Fetching file");
            MultipartFile multipartFile = fileBucket.getFile();            
 
            //Now do something with file...
            String fileName = String.valueOf(id);
            
            String originalFileName = multipartFile.getOriginalFilename();
            int index = originalFileName.indexOf('.');
            String extention = originalFileName.substring(index, originalFileName.length());
            fileName = fileName + extention;
            
            FileCopyUtils.copy(fileBucket.getFile().getBytes(), new File(fullPath + fileName));
             
            //String fileName = multipartFile.getOriginalFilename();       
            model.addAttribute("fileName", fileName);
            
            /*
             * This section updates the incident stage
             */
            if (incident.getStage().charAt(0)=='1') {
				incident.setStage("1b");
			} else if (incident.getStage().charAt(0)=='2'){
				incident.setStage("2b");
			}
            
            incident.setStatus("Pending Plagiarism Confirmation");
            incidentService.updateIncident(incident);
            
            return "success";
        }
    }
    
    public static MailObject getMailObject(int id, String subject, String message, String status, String email){        
    	RecipientInfo recipientInfo = new RecipientInfo();
    	recipientInfo.setEmail(email);
    	
		MailObject object = new MailObject();
		object.setId(String.valueOf(id));
		object.setStatus(status);		
		object.setRecipientInfo(recipientInfo);		
		object.setMessage(message);
		object.setSubject(subject);
        
        return object;
    }
    
    //ref: http://www.journaldev.com/2573/spring-mvc-file-upload-example-single-multiple-files
}

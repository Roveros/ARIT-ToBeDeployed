package com.browne.spring.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.browne.spring.model.RecipientInfo;
import com.browne.spring.model.Password;
import com.browne.spring.model.MailObject;
import com.browne.spring.model.Users;
import com.browne.spring.service.AutoMailService;
import com.browne.spring.service.UsersService;


@Controller
public class PasswordController {
	
	private static final Logger logger = LoggerFactory.getLogger(PasswordController.class);

	@Autowired
	private UsersService userService;

	@Autowired
	private AutoMailService autoMailService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	Authentication auth;
	String username;

	// Display forgotPassword page
	@RequestMapping(value = "/recovery", method = RequestMethod.GET)
	public ModelAndView displayForgotPasswordPage() {
		Users users = new Users();
		ModelAndView model = new ModelAndView();
		model.addObject(users);
		model.setViewName("recovery");
		return model;
    }
	
    // Process form submission from forgotPassword page
	@RequestMapping(value = "/recovery", method = RequestMethod.POST)
	public ModelAndView processForgotPasswordForm(ModelAndView modelAndView, @ModelAttribute("users") Users users, HttpServletRequest request) {

		// Lookup user in database by e-mail
		Users user;
		
		try {
			user = userService.getUsersById(users.getUsername());
			user.setReset_token(UUID.randomUUID().toString());
			userService.updateUsers(user);
			
			String appUrl = request.getScheme() + "://" + request.getServerName();
			
			autoMailService.sendAutoMail(getMailObject(user.getUsername(), user.getReset_token(), appUrl));
			
			// Add success message to view
			modelAndView.addObject("message", "A password reset link has been sent to " + user.getUsername());
			
		} catch (Exception e) {
			modelAndView.addObject("message", "We didn't find an account for that e-mail address.");
			e.printStackTrace();
		}

		modelAndView.setViewName("recovery");
		return modelAndView;

	}

	// Display form to reset password
	@RequestMapping(value = "/reset", method = RequestMethod.GET)
	public ModelAndView displayResetPasswordPage(ModelAndView modelAndView, @RequestParam("token") String token) {
		//i.e. http://localhost/reset?token=4048df20-5300-4818-b343-d5b54f2bad7e
		List<Users> users = userService.getUsersByResetToken(token);
		logger.info("token="+token);
		Users user = users.get(0);
		user.setReset_token(token);

		if (!users.isEmpty()) { // Token found in DB
			modelAndView.addObject("reset_token", token);
			Password password = new Password();
			password.setReset_token(token);
			modelAndView.addObject(password);
		} else { // Token not found in DB
			modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
		}

		modelAndView.setViewName("reset");
		return modelAndView;
	}
	
	@RequestMapping(value = { "/myprofile"}, method = RequestMethod.GET)
	public ModelAndView myprofilePage(ModelAndView modelAndView) {
		auth = SecurityContextHolder.getContext().getAuthentication();
		username = auth.getName(); //get logged in username
		
			modelAndView.addObject("reset_token", username);
			Password password = new Password();
			password.setReset_token(username);
			modelAndView.addObject(password);
		

		modelAndView.setViewName("myprofile");
		return modelAndView;
	}
	
	// Process reset password form
	@RequestMapping(value = "/myprofile", method = RequestMethod.POST)
	public ModelAndView setNewProfilePassword(ModelAndView modelAndView, @ModelAttribute("password") Password password, RedirectAttributes redir) {

		// Find the user associated with the reset token
		Users user = userService.getUsersById(password.getReset_token());
		
		if (!password.getPassword().equals(password.getPassword_confirmation())) {
			System.err.println("Pass 1: "+password.getPassword()+", Pass 2: "+password.getPassword_confirmation()+", User: "+password.getReset_token());
			redir.addAttribute("user", password.getReset_token());
			modelAndView.setViewName("redirect:reset");
			logger.info("redirect:reset?user="+password.getReset_token());
		} else if (password.getPassword().equals(password.getPassword_confirmation())) {
			
			Users resetUser = user; 
            
			// Set new password    
            resetUser.setPassword(bCryptPasswordEncoder.encode(password.getPassword()));
            
			// Set the reset token to null so it cannot be used again
			resetUser.setReset_token(null);

			// Save user
			userService.updateUsers(resetUser);

			// In order to set a model attribute on a redirect, we must use
			// RedirectAttributes
			redir.addFlashAttribute("message", "You have successfully reset your password.  You may now login.");

			modelAndView.setViewName("redirect:login");
			return modelAndView;
			
		} else {
			modelAndView.addObject("message", "Oops!  This is an invalid password reset link.");
			modelAndView.setViewName("reset");	
		}
		
		return modelAndView;
   }

	// Process reset password form
	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	public ModelAndView setNewPassword(ModelAndView modelAndView, @ModelAttribute("password") Password password, RedirectAttributes redir) {

		// Find the user associated with the reset token
		List<Users> user = userService.getUsersByResetToken(password.getReset_token());
		
		if (!password.getPassword().equals(password.getPassword_confirmation())) {
			System.err.println("Pass 1: "+password.getPassword()+", Pass 2: "+password.getPassword_confirmation()+", token: "+password.getReset_token());
			redir.addAttribute("token", password.getReset_token());
			modelAndView.setViewName("redirect:reset");
			logger.info("redirect:reset?token="+password.getReset_token());
		} else if (!user.isEmpty() && password.getPassword().equals(password.getPassword_confirmation())) {
			
			Users resetUser = user.get(0); 
            
			// Set new password    
            resetUser.setPassword(bCryptPasswordEncoder.encode(password.getPassword()));
            
			// Set the reset token to null so it cannot be used again
			resetUser.setReset_token(null);

			// Save user
			userService.updateUsers(resetUser);

			// In order to set a model attribute on a redirect, we must use
			// RedirectAttributes
			redir.addFlashAttribute("message", "You have successfully reset your password.  You may now login.");

			modelAndView.setViewName("redirect:login");
			return modelAndView;
			
		} else {
			modelAndView.addObject("message", "Oops!  This is an invalid password reset link.");
			modelAndView.setViewName("reset");	
		}
		
		return modelAndView;
   }
   
    // Going to reset page without a token redirects to login page
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ModelAndView handleMissingParams(MissingServletRequestParameterException ex) {
		return new ModelAndView("redirect:login");
	}
	
    public static MailObject getMailObject(String email, String token, String appUrl){
        MailObject object = new MailObject();
        RecipientInfo recipientInfo = new RecipientInfo();
        
        recipientInfo.setEmail(email);
        object.setRecipientInfo(recipientInfo);
        
        String message = "To reset your password, click the link below:\n" + appUrl
				+ "/reset?token=" + token;
        String subject = "Password Reset Request";
        
        object.setMessage(message);
        object.setSubject(subject);
        
        return object;
    }
    //ref: http://www.baeldung.com/spring-security-registration-password-encoding-bcrypt
}

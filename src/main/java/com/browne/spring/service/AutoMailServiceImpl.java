package com.browne.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.browne.spring.model.MailObject;

@Service("autoMailService")
public class AutoMailServiceImpl implements AutoMailService{
 
    @Autowired
    MailService mailService;
 
    @Override
    public void sendAutoMail(MailObject mailObject) {
        mailService.sendEmail(mailObject);
    }
     
}
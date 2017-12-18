package com.browne.spring.service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.browne.spring.model.MailObject;

@Service("mailService")
public class MailServiceImpl implements MailService {
	
    @Autowired
    private Environment environment;
 
    @Autowired
    JavaMailSender mailSender;
 
    @Override
    public void sendEmail(Object object) {
 
        MailObject mailObject = (MailObject) object;
 
        MimeMessagePreparator preparator = getMessagePreparator(mailObject);
 
        try {
            mailSender.send(preparator);
            System.out.println("Message Sent");
        } catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }
 
    private MimeMessagePreparator getMessagePreparator(final MailObject mailObject) {
 
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
 
            public void prepare(MimeMessage mimeMessage) throws Exception {
                mimeMessage.setFrom(environment.getRequiredProperty("javamailsender.username"));
                mimeMessage.setRecipient(Message.RecipientType.TO,
                        new InternetAddress(mailObject.getRecipientInfo().getEmail()));
                mimeMessage.setText(mailObject.getMessage());
                mimeMessage.setSubject(mailObject.getSubject());
            }
        };
        return preparator;
    }
 
}
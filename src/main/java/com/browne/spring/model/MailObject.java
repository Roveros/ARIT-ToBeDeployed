package com.browne.spring.model;

public class MailObject {
	
	private String type;
    
    private String id;
 
    private String status;
    
    private String message;
    
    private String subject;
     
    private RecipientInfo recipientInfo;
     
    public String getId() {
        return id;
    }
 
    public void setId(String id) {
        this.id = id;
    }
 
    public String getStatus() {
        return status;
    }
 
    public void setStatus(String status) {
        this.status = status;
    }
 
    public RecipientInfo getRecipientInfo() {
        return recipientInfo;
    }
 
    public void setRecipientInfo(RecipientInfo recipientInfo) {
        this.recipientInfo = recipientInfo;
    }
 
    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}	

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Override
    public String toString() {
        return "MailOrder [id=" + id + ", status=" + status
                + ", recipientInfo=" + recipientInfo + "]";
    }
 
     
}

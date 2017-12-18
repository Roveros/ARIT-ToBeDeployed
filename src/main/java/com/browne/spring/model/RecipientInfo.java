package com.browne.spring.model;

public class RecipientInfo {
     
    private String email;
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    @Override
    public String toString() {
        return "RecipientInfo [email=" + email + "]";
    }
     
}

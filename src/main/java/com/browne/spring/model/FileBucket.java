package com.browne.spring.model;

import org.springframework.web.multipart.MultipartFile;

public class FileBucket {
	String altEmail;
    MultipartFile file;
    
    public MultipartFile getFile() {
        return file;
    }
 
    public void setFile(MultipartFile file) {
        this.file = file;
    }

	public String getAltEmail() {
		return altEmail;
	}

	public void setAltEmail(String altEmail) {
		this.altEmail = altEmail;
	}
    
}

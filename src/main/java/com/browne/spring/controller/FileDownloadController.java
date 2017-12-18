package com.browne.spring.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class FileDownloadController {
	
	public static final String dpt1 = "[ROLE_HOD_DPT1]";
	public static final String dpt2 = "[ROLE_HOD_DPT2]";
	public static final String dpt3 = "[ROLE_HOD_DPT3]";
	public static final String dpt4 = "[ROLE_HOD_DPT4]";
	
	String dir[] = {"DPT1/","DPT2/","DPT3/","DPT4/"};

	//root directory path for files to be downloaded
    private static String FILE_PATH="C:/ARIT/";     
 
    /*
     * Download files by ROLE
     */
    @RequestMapping(value="/download/"+dpt1+"/{id}", method = RequestMethod.GET)
    public void downloadDPT1File(HttpServletResponse response, @PathVariable("id") int id) throws IOException {
    	
    	//file path to retrieve file from
    	String fullPath = FILE_PATH + dir[0];
        File file = null;
        
        //Attempts to identify the file extension
        file = new File(fullPath+String.valueOf(id)+".zip");        
        if(!file.exists()){
        	file = new File(fullPath+String.valueOf(id)+".7z");
        }        
        if(!file.exists()){
        	file = new File(fullPath+String.valueOf(id)+".rar");
        }
        
        //If the file does not exist then report the error
        if(!file.exists()){
            String errorMessage = "Sorry. The file \""+fullPath+String.valueOf(id)+"\" you are looking for does not exist";
            System.out.println(errorMessage);
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
            outputStream.close();
            return;
        }
         
        String mimeType= URLConnection.guessContentTypeFromName(file.getName());
        if(mimeType==null){
            System.out.println("mimetype is not detectable, will take default");
            mimeType = "application/octet-stream";
        }
         
        System.out.println("mimetype : "+mimeType);         
        response.setContentType(mimeType);
         
        /* "Content-Disposition : inline" will show viewable types [like images/text/pdf/anything viewable by browser] right on browser 
            while others(zip e.g) will be directly downloaded [may provide save as popup, based on your browser setting.]*/
        response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() +"\""));
          
        /* "Content-Disposition : attachment" will be directly download, may provide save as popup, based on your browser setting*/
        //response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
        response.setContentLength((int)file.length());
 
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
 
        //Copy bytes from source to destination(outputstream in this example), closes both streams.
        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }
    
    @RequestMapping(value="/download/"+dpt2+"/{id}", method = RequestMethod.GET)
    public void downloadDPT2File(HttpServletResponse response, @PathVariable("id") int id) throws IOException {
     
    	String fullPath = FILE_PATH + dir[1];
        File file = null;
        file = new File(fullPath+String.valueOf(id)+".zip");        
        if(!file.exists()){
        	file = new File(fullPath+String.valueOf(id)+".7z");
        }        
        if(!file.exists()){
        	file = new File(fullPath+String.valueOf(id)+".rar");
        }        
        if(!file.exists()){
            String errorMessage = "Sorry. The file \""+fullPath+String.valueOf(id)+"\" you are looking for does not exist";
            System.out.println(errorMessage);
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
            outputStream.close();
            return;
        }
         
        String mimeType= URLConnection.guessContentTypeFromName(file.getName());
        if(mimeType==null){
            System.out.println("mimetype is not detectable, will take default");
            mimeType = "application/octet-stream";
        }
         
        System.out.println("mimetype : "+mimeType);
         
        response.setContentType(mimeType);
        response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() +"\""));
        response.setContentLength((int)file.length());
 
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }
    
    @RequestMapping(value="/download/"+dpt3+"/{id}", method = RequestMethod.GET)
    public void downloadDPT3File(HttpServletResponse response, @PathVariable("id") int id) throws IOException {
     
    	String fullPath = FILE_PATH + dir[2];
        File file = null;
        file = new File(fullPath+String.valueOf(id)+".zip");        
        if(!file.exists()){
        	file = new File(fullPath+String.valueOf(id)+".7z");
        }        
        if(!file.exists()){
        	file = new File(fullPath+String.valueOf(id)+".rar");
        }        
        if(!file.exists()){
            String errorMessage = "Sorry. The file \""+fullPath+String.valueOf(id)+"\" you are looking for does not exist";
            System.out.println(errorMessage);
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
            outputStream.close();
            return;
        }
         
        String mimeType= URLConnection.guessContentTypeFromName(file.getName());
        if(mimeType==null){
            System.out.println("mimetype is not detectable, will take default");
            mimeType = "application/octet-stream";
        }
         
        System.out.println("mimetype : "+mimeType);
         
        response.setContentType(mimeType);
        response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() +"\""));
        response.setContentLength((int)file.length());
 
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }
    
    @RequestMapping(value="/download/"+dpt4+"/{id}", method = RequestMethod.GET)
    public void downloadDPT4File(HttpServletResponse response, @PathVariable("id") int id) throws IOException {
     
    	String fullPath = FILE_PATH + dir[3];
        File file = null;
        file = new File(fullPath+String.valueOf(id)+".zip");        
        if(!file.exists()){
        	file = new File(fullPath+String.valueOf(id)+".7z");
        }        
        if(!file.exists()){
        	file = new File(fullPath+String.valueOf(id)+".rar");
        }        
        if(!file.exists()){
            String errorMessage = "Sorry. The file \""+fullPath+String.valueOf(id)+"\" you are looking for does not exist";
            System.out.println(errorMessage);
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
            outputStream.close();
            return;
        }
         
        String mimeType= URLConnection.guessContentTypeFromName(file.getName());
        if(mimeType==null){
            System.out.println("mimetype is not detectable, will take default");
            mimeType = "application/octet-stream";
        }
         
        System.out.println("mimetype : "+mimeType);
         
        response.setContentType(mimeType);
        response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() +"\""));
        response.setContentLength((int)file.length());
 
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }
    
    @RequestMapping(value="/download/External/{id}", method = RequestMethod.GET)
    public void downloadExternalFile(HttpServletResponse response, @PathVariable("id") int id) throws IOException {
     
    	String fullPath = FILE_PATH + "External/";
        File file = null;
        file = new File(fullPath+String.valueOf(id)+".zip");        
        if(!file.exists()){
        	file = new File(fullPath+String.valueOf(id)+".7z");
        }        
        if(!file.exists()){
        	file = new File(fullPath+String.valueOf(id)+".rar");
        }        
        if(!file.exists()){
            String errorMessage = "Sorry. The file \""+fullPath+String.valueOf(id)+"\" you are looking for does not exist";
            System.out.println(errorMessage);
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
            outputStream.close();
            return;
        }
         
        String mimeType= URLConnection.guessContentTypeFromName(file.getName());
        if(mimeType==null){
            System.out.println("mimetype is not detectable, will take default");
            mimeType = "application/octet-stream";
        }
         
        System.out.println("mimetype : "+mimeType);
         
        response.setContentType(mimeType);
        response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() +"\""));
        response.setContentLength((int)file.length());
 
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }
    
 
}
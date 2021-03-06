package com.webautomation.amazonfresh.testcases;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.mail.*;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.testng.annotations.Test;

import com.webautomation.amazonfresh.library.PropertyReader;

public class TC006_SendEmail {

		public static void sendEmailWithAttachments(String host, String port, final String userName, 
	    	   final String password, String toAddress, String subject, String message, List<String> attachFiles)
	           throws AddressException, MessagingException {
	        // sets SMTP server properties
	        Properties properties = new Properties();
	        properties.put("mail.smtp.host", host);
	        properties.put("mail.smtp.port", port);
	        properties.put("mail.smtp.auth", "true");
	        properties.put("mail.smtp.starttls.enable", "true");
	        properties.put("mail.user", userName);
	        properties.put("mail.password", password);
	        
	        // creates a new session with an authenticator
	        Authenticator auth = new Authenticator() {
	            public PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(userName, password);
	            }
	        };
	        Session session = Session.getInstance(properties, auth);
	 
	        // creates a new e-mail message
	        Message msg = new MimeMessage(session);
	 
	        msg.setFrom(new InternetAddress(userName));

	        InternetAddress[] toAddresses =  InternetAddress.parse(toAddress);
	        msg.setRecipients(Message.RecipientType.TO, toAddresses);
	        msg.setSubject(subject);
	        msg.setSentDate(new Date());
	 
	        // creates message part
	        MimeBodyPart messageBodyPart = new MimeBodyPart();
	        messageBodyPart.setContent(message, "text/plain");
	 
	        // creates multi-part
	        Multipart multipart = new MimeMultipart();
	        multipart.addBodyPart(messageBodyPart);
	 
	        // adds attachments
	        if (attachFiles != null) {
	            for (String filePath : attachFiles) {
	                MimeBodyPart attachPart = new MimeBodyPart();
	                try {
	                    attachPart.attachFile(filePath);
	                } catch (IOException ex) {
	                    ex.printStackTrace();
	                }
	                multipart.addBodyPart(attachPart);
	            }
	        }
	        // sets the multi-part as e-mail's content
	        msg.setContent(multipart);
	        // sends the e-mail
	        Transport.send(msg);
	    }
		@Test
	    public void sendReportsThroughEmail() throws IOException {
	        // SMTP info
	        String host = "smtp.gmail.com";
	        String port = "587";
	        String mailFrom = System.getenv("Sender_Address");
	        String password = System.getenv("Sender_Password");
	 
	        // message info
	        String mailTo = System.getenv("Recipients_Address");
	        
	        String subject = "[Automatic] Amazon Fresh Items Info";
	        String message = "Hi, \n\nPlease find attached reports for your Amazon Fresh items. \n\nRegards, "
	        		+ "\nIshita \n\n*** Message automatically generated by script, No need to reply. ***";
	 
	        File folder = new File(PropertyReader.configReader("ResultsFilesPath"));
	        String[] attachFiles = folder.list();
	        List<String> attachFilesPath = new ArrayList<>();
	        for (String path : attachFiles) {
	        	path = PropertyReader.configReader("ResultsFilesPath") + path;
	        	attachFilesPath.add(path);
	        }
	        try {
	            sendEmailWithAttachments(host, port, mailFrom, password, mailTo,
	                subject, message, attachFilesPath);
	            System.out.println("Email sent.");
	        } catch (Exception ex) {
	            System.out.println("Could not send email.");
	            ex.printStackTrace();
	        }
	   }
  }

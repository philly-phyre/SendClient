package org.tempestdesign.sendclient;

import java.util.Date;
//import java.util.NoSuchElementException;
import java.util.Properties;
//import java.util.Scanner;
import javax.mail.*;
import javax.mail.internet.*;

public class Send {
	
	static Session sesh;
	static Properties prop = new Properties();
		
	static String host = Client.host;
	static String port = Client.port;
	static String UN = Client.UN;
	static String PW = Client.PW;
	static String msub = Client.msub;
	static String mto = Client.mto;
	static String cTEXT = Client.cTEXT;
	static String cTYPE = Client.cTYPE;
	
	public static void send() {
		
		
		
		try {
			
			Message m = new MimeMessage(sesh);
			m.setFrom(new InternetAddress(UN));
			m.addRecipients(Message.RecipientType.TO, InternetAddress.parse(mto)); //////////////////////////////////////////
			m.setSubject(msub);
			m.setSentDate(new Date());
			m.setContent(cTEXT, cTYPE);
			
			Transport t;
			if(host.equals("smtp.mail.yahoo.com"))
				t = sesh.getTransport("smtps");
			else 
				t = sesh.getTransport("smtp");
			 //
			System.out.println(">> ? smtp(s) ---> ## " + t.getURLName() + " \n>> ?");
			
			Transport.send(m);

			
			

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		} // end try/catch //
		
		
	}

	public static boolean auth(String UN, String PW) /*throws AuthenticationFailedException /*AddressException*/ {
		
		prop.put("mail.smtp.auth", "true");
		if(host.equals("smtp.gmail.com") || host.equals("smtp.mail.yahoo.com")){
			prop.put("mail.smtp.starttls.enable", "true");
		}
		prop.put("mail.smtp.host", host/*[0]*/);
		prop.put("mail.smtp.port", port);
		if(host.equals("smtp.mail.yahoo.com")) { prop.put("mail.smtp.ssl.enable", "true"); }
		
		boolean chk = true;
		//
		try {
			InternetAddress e = new InternetAddress(UN);
			e.validate();
		} catch (AddressException e) {
			e.getStackTrace();
			chk = false;
		}
		
		if(chk) {
			sesh = Session.getInstance(prop,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(UN, PW);
					}
				});
		}
		
		return chk;
		
		
		
		
	}
	
}

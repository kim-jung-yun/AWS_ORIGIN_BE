package com.ssgtarbucks.util;



import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

import org.springframework.stereotype.Component;


@Component
public class SendMail extends Authenticator {
	public PasswordAuthentication getPasswordAuthentication() {
		      
		System.out.println("PasswordAuthentication"); 

		return new PasswordAuthentication("jungyun5535@naver.com", "3QSG5QGXL22F");
	}
}
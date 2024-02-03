package com.ssgtarbucks.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Component
public class SendMessage {
	//@Value("${message.api.key}")
	private String key="";
	
    //@Value("${message.api.secret}")
    private String secret="";

    public void setKey(String key) {
		this.key = key;
	}

    public void setSecret(String secret) {
    	this.secret = secret;
    }
    
    final DefaultMessageService messageService;
    
    public SendMessage() {
        this.messageService = NurigoApp.INSTANCE.initialize(key, secret, "https://api.coolsms.co.kr");

    }
    
    public void Message(String to, String tempCode) {
    	System.out.println(key);
        net.nurigo.sdk.message.model.Message message = new net.nurigo.sdk.message.model.Message();
        message.setFrom("01084037635");
        message.setTo(to);
        message.setText("[SSGtarbucks]비밀번호 찾기 인증번호 => ["+tempCode+"]");

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
    
    }

}

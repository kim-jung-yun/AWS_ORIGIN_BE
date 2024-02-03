package com.ssgtarbucks.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.*;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

import com.ssgtarbucks.util.SendMail;
import com.ssgtarbucks.util.SendMessage;
import com.ssgtarbucks.domain.UserDTO;
import com.ssgtarbucks.persistence.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
    final DefaultMessageService messageService;
    
    @Value("${message.api.imgpath}")
    private String imgPath;
    
    public UserServiceImpl() {
        this.messageService = NurigoApp.INSTANCE.initialize("", "", "https://api.coolsms.co.kr");

    }
    
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()_-+=<>?";
    private static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    
	@Autowired
	private UserRepository userRepository;
    
	@Autowired
	SendMail sendMail;
	
	@Autowired
	SendMessage sendMessage;
	
	@Override
	public UserDTO selectUserByUserId(String user_id) {
		return userRepository.selectUserByUserId(user_id);
	}

	@Override
	public UserDTO selectUserAndBranchToInfo(String user_id) {
		return userRepository.selectUserAndBranchToInfo(user_id);
	}

	@Override
	public int selectCountToFindUserExist(UserDTO userDTO) {
		return userRepository.selectCountToFindUserExist(userDTO);
	}

	@Override
	public int updateUserByUserIdToChgPW(UserDTO userDTO) {
		return userRepository.updateUserByUserIdToChgPW(userDTO);
	}
    
	

	@Override
	public int insertTempCode(String temp_pw_code, String user_id) {
		return userRepository.insertTempCode(temp_pw_code, user_id);
	}


	@Override
	public String selectTempCodeByUserId(String user_id) {
		return userRepository.selectTempCodeByUserId(user_id);
	}
	

	@Override
	public int deleteTempCodeByUserId(String user_id) {
		return userRepository.deleteTempCodeByUserId(user_id);
	}

    
    //임시비밀번호 생성구간
	@Override
	public String generateTempPw() {

        StringBuilder password = new StringBuilder();
        SecureRandom random = new SecureRandom();
        String allCharacters = SPECIAL_CHARACTERS + UPPERCASE_LETTERS + LOWERCASE_LETTERS + DIGITS;

        // Use at least one character from each character set
        password.append(getRandomChar(SPECIAL_CHARACTERS, random));
        password.append(getRandomChar(UPPERCASE_LETTERS, random));
        password.append(getRandomChar(LOWERCASE_LETTERS, random));
        password.append(getRandomChar(DIGITS, random));

        for (int i = 4; i < 10; i++) {
            password.append(getRandomChar(allCharacters, random));
        }

        return shuffleString(password.toString(), random);
	}
	
    private static char getRandomChar(String characterSet, SecureRandom random) {
        int randomIndex = random.nextInt(characterSet.length());
        return characterSet.charAt(randomIndex);
    }
    
    private static String shuffleString(String input, SecureRandom random) {
        char[] characters = input.toCharArray();
        for (int i = characters.length - 1; i > 0; i--) {
            int randomIndex = random.nextInt(i + 1);
            char temp = characters[i];
            characters[i] = characters[randomIndex];
            characters[randomIndex] = temp;
        }
        return new String(characters);
    }


    //문자전송
    public void Message(String to, String tempCode) {
        net.nurigo.sdk.message.model.Message message = new net.nurigo.sdk.message.model.Message();
        message.setFrom("01084037635");
        message.setTo(to);
        message.setText("[SSGtarbucks]비밀번호 찾기 인증번호 => ["+tempCode+"]");

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
    
    }
    
    //문자전송 component
    public void MessageCompnt(String to, String tempCode) {
    	
    }
    
    
   //메일전송
    public void Mail(String to, String tempCode) {
    	
    	

   		String host = "smtp.naver.com";
	    String subject = "[SSGTARBUCKS] 비밀번호 인증코드 발송";  //메일제목
	    String from = "jungyun5535@naver.com"; //보내는 메일주소 ////////////수정필요
	    String fromName = "SSGtarbucks Korea";   //보내는 사람이름
	    String content =
	    		"<html><body>" +
	                    "<p>안녕하세요, 사랑하는 SSGtarbucks 직원님!</p>" +
	                    "<p>SSGTARBUCKS 비밀번호 재설정을 위한 인증코드를 보내드립니다. </p>"+
	                    "<p>만약 비밀번호 인증코드를 요청하지 않았다면 이 이메일을 무시해주시기 바랍니다.</p><br/><br/>" +
	                    "<p>     비밀번호 인증코드 : " + tempCode + "</p><br/><br/>" +
	                    "<p>인증코드 입력 시 비밀번호는 직원코드로 초기화됩니다.</p>" +
	                    "<p>도움이 필요하시거나 다른 문의사항이 있다면 언제든지 지원팀에 문의하시거나 SSGTARBUCKS로 연락해주세요:) </p><br/><br/>" +
	                    "<p>SSGTARBUCKS 팀 드림</p>" ;
	            
	    
	   try{
	     //프로퍼티 값 인스턴스 생성과 기본세션(SMTP 서버 호스트 지정)W
	     Properties props = new Properties();
	     //네이버 SMTP 사용시
	 
	     props.put("mail.transport.protocol","smtp");
	     props.put("mail.smtp.host", host);
	     ///////////////추가 부분///////////////////////////////////////////
	     props.put("mail.smtp.starttls.enable", "true");////////////
	     props.put("mail.smtp.starttls.trust", "*");////////////////
	     props.put("mail.smtp.ssl.enable", "false");///////////////////////
	     props.put("mail.smtp.ssl.protocols", "TLSv1.2");///////////////////////
	     ///////////////추가 부분///////////////////////////////////////////
	     
	     
	     props.put("mail.smtp.port","465");  // 보내는 메일 포트 설정
	     props.put("mail.smtp.user", from);
	     props.put("mail.smtp.auth","true");
	     props.put("mail.smtp.debug", "true");
	     props.put("mail.smtp.socketFactory.port", "465");
	     props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	     props.put("mail.smtp.socketFactory.fallback", "false");

	     Authenticator auth = sendMail;
	     Session mailSession = Session.getInstance(props,auth);
	     Transport transport = mailSession.getTransport("smtp");

	     Message msg = new MimeMessage(mailSession);
	     msg.setFrom(new InternetAddress(from, MimeUtility.encodeText(fromName,"UTF-8","B"))); //보내는 사람 설정
	     InternetAddress[] address = {new InternetAddress(to)};
	    
	     msg.setRecipients(Message.RecipientType.TO, address); //받는 사람설정
	   
	     msg.setSubject(subject); //제목설정
	     msg.setSentDate(new java.util.Date()); //보내는 날짜 설정
	     msg.setContent(content,"text/html; charset=EUC-KR"); //내용 설정(MIME 지정-HTML 형식)
	    
	     Transport.send(msg); //메일 보내기

	     System.out.println("메일 발송을 완료하였습니다.");
	     transport.close();
	     }catch(MessagingException ex){
	      System.out.println("mail send error : "+ex.getMessage());
	       ex.printStackTrace();
	     }catch(Exception e){
	      System.out.println("error : "+e.getMessage());
	       e.printStackTrace();
	     }
	    
    }
    
    //이미지 변경
    private String encodeImageToBase64(String imagePath) {
        try {
            Path path = Paths.get(imagePath);
            byte[] imageBytes = Files.readAllBytes(path);
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (Exception e) {
            System.out.println("에러: " + e.getMessage());
            e.printStackTrace();
            return "";
        }
    }
    
}
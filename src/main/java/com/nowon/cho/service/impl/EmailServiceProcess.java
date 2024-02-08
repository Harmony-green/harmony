package com.nowon.cho.service.impl;



import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.nowon.cho.service.EmailService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceProcess implements EmailService{

	private final JavaMailSender javaMailSender; // 의존성 주입을 통해 필요한 객체를 가져옴
	private static final String senderEmail="scn0707074@gmail.com";
	private static int number; // 랜덤인증코드
	
	public static void createNumber() {
		number = (int)(Math.random()*(90000))+100000; // (int) Math.random() * (최댓값-최소값+1) + 최소값
	}

	public MimeMessage createMail(String mail) {

		createNumber();

		MimeMessage message = javaMailSender.createMimeMessage();

		try {
            message.setFrom(senderEmail);   // 보내는 이메일
            message.setRecipients(MimeMessage.RecipientType.TO, mail); // 보낼 이메일 설정
            message.setSubject("[Harmony] 회원가입을 위한 이메일 인증");  // 제목 설정
            String body = "";
            body += "<h1>" + "안녕하세요." + "</h1>";
            body += "<h1>" + "Harmony 주얼리 입니다." + "</h1>";
            body += "<h3>" + "회원가입을 위한 요청하신 인증 번호입니다." + "</h3><br>";
            body += "<h2>" + "아래 코드를 회원가입 창으로 돌아가 입력해주세요." + "</h2>";
            body += "<div align='center' style='border:5px solid black;'>";
            body += "<h2>" + "회원가입 인증 코드입니다." + "</h2>";
            body += "<h1 style='color:blue'>" + number + "</h1>";
            body += "</div><br>";
            body += "<h3>" + "감사합니다." + "</h3>";
            message.setText(body,"UTF-8", "html");
        } catch (Exception e) {
            e.printStackTrace();
        }
		return message;
	}

	 public int sendEmail(String userEmail) {
	        // 메일 전송에 필요한 정보 설정
	        MimeMessage message = createMail(userEmail);
	        // 실제 메일 전송
	        javaMailSender.send(message);
	        // 인증 코드 반환
	        return number;
	    }

	

	

	

}
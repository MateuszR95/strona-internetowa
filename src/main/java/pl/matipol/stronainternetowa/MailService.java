package pl.matipol.stronainternetowa;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String ownerEmail;
    private static final String RESPONSE_MAIL_TITLE = "Oponex-potwierdzenie wysłania zapytania";
    private static final String RESPONSE_MAIL_CONTENT = "Potwierdzamy otrzymanie zapytania. Niebawem prześlemy odpowiedź";

    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(MailMessageDto mailMessageDto) {
        sendQuestion(mailMessageDto);
        sendResponse(mailMessageDto);
    }

    private void sendQuestion(MailMessageDto mailMessageDto) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(ownerEmail);
        simpleMailMessage.setTo(ownerEmail);
        simpleMailMessage.setReplyTo(mailMessageDto.getSenderEmail());
        simpleMailMessage.setSubject("Wiadomość od: " + mailMessageDto.getSenderName());
        simpleMailMessage.setText(mailMessageDto.getContent());
        try {
            javaMailSender.send(simpleMailMessage);
            mailMessageDto.setSent(true);
        } catch (MailException e) {
            e.printStackTrace();
        }

    }

    private void sendResponse(MailMessageDto mailMessageDto) {
        if (mailMessageDto.isSent()) {
            SimpleMailMessage response = new SimpleMailMessage();
            response.setFrom(ownerEmail);
            response.setTo(mailMessageDto.getSenderEmail());
            response.setSubject(RESPONSE_MAIL_TITLE);
            response.setText(RESPONSE_MAIL_CONTENT);
            javaMailSender.send(response);
        }
    }
}



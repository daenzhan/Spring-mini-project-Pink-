package Lab5.Project_mini_final_without_Security.service;

import Lab5.Project_mini_final_without_Security.model.User;
import Lab5.Project_mini_final_without_Security.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.bson.types.ObjectId;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class EmailService {
    private final JavaMailSender mail_sender;

    public EmailService(JavaMailSender mail_sender) {
        this.mail_sender = mail_sender;
    }

    public void send_email (String to, String title, String text){
        try {
            MimeMessage message = mail_sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true);

            helper.setTo(to);
            helper.setSubject(title);
            helper.setText(text,true);

            mail_sender.send(message);
        }
        catch (MessagingException e) {
            System.err.println("ошибка с отправкой в email" + e);
        }
    }


}

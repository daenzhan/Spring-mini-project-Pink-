package Lab5.Project_mini_final_without_Security.controller;

import Lab5.Project_mini_final_without_Security.service.EmailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/send/email")
    public String send_email (@RequestParam String to, @RequestParam String title, @RequestParam String text){
        emailService.send_email(to,title,text);
        return "уведомление отправлено!!!";
    }

}

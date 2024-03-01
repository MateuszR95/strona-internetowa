package pl.matipol.stronainternetowa;

import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    private final MailService mailService;

    public HomeController(MailService mailService) {
        this.mailService = mailService;
    }

    @GetMapping("/")
    String home() {
        return "home";
    }

    @GetMapping("/form")
    String form() {
        return "form";
    }

    @GetMapping("/success")
    String success() {
        return "success";
    }

    @GetMapping("/fail")
    String fail() {
        return "fail";
    }

    @PostMapping("/contact")
    String sendEmail(MailMessageDto mailMessageDto) {
        try {
            mailService.sendEmail(mailMessageDto);
            return "redirect:success";
        } catch (MailException e) {
            e.printStackTrace();
            return "redirect:fail";
        }
    }
}

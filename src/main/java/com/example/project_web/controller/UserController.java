package com.example.project_web.controller;

import com.example.project_web.entity.User;
import com.example.project_web.service.UserDetailService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class UserController {
    @Autowired
    UserDetailService userDetailService;

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping(value = "/admin_userprofile")
    public String admin_userprofile() {
        return "admin_userprofile";
    }

    @GetMapping(value = "/add_users")
    public String add_users(Model model) {
        model.addAttribute("getRole", listRole);
        return "add_users";
    }

    static List<String> listRole = new ArrayList<>();

    static {
        listRole.add("ADMIN");
        listRole.add("USER");
        listRole.add("MEMBER");
        listRole.add("OTHER");
    }

    @GetMapping(value = "/user_forgot")
    public String user_forgot() {
        return "user_forgot";
    }

    @PostMapping("/user_forgot")
    public String processForgotPassword(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        String token = RandomString.make(30);

        try {
            userDetailService.updateResetPasswordToken(token, email);
            String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
            String subject = "Here's the link to reset your password";

            String content = "<p>Hello,</p>"
                    + "<p>You have requested to reset your password.</p>"
                    + "<p>Click the link below to change your password:</p>"
                    + "<p><a href=\"" + resetPasswordLink + "\">Change my password</a></p>"
                    + "<br>"
                    + "<p>Ignore this email if you do remember your password, "
                    + "or you have not made the request.</p>";
            sendEmail(email, subject, content);
            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");

        } catch (UsernameNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Error while sending email");
        }

        return "user_forgot";
    }

    public void sendEmail(String recipientEmail, String subject, String content) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("contact@yogafitness.com", "Yoga Support");
        helper.setTo(recipientEmail);

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }

    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        User user = userDetailService.getByResetPasswordToken(token);
        model.addAttribute("token", token);

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        }

        return "reset_password";
    }

    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        User user = userDetailService.getByResetPasswordToken(token);
        model.addAttribute("title", "Reset your password");

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "admin_login";
        } else {
            userDetailService.updatePassword(user, password);

            model.addAttribute("message", "You have successfully changed your password.");
        }

        return "admin_login";
    }

    // Đăng ký
    @PostMapping(value = "/register")
    public String register(@ModelAttribute User user, HttpServletRequest request, Model model) {
        String linkCode = RandomString.make(100);
        String verifyCode = String.format("%06d", new Random().nextInt(1000000));

        try {
            String confirmLink = Utility.getSiteURL(request) + "/verify?token=" + linkCode;
            String subject = "Here's the link to confirm your email";

            String content = "<p>Hello,</p>"
                    + "<p>You have registered an account.</p>"
                    + "<p>Click the link below and enter the code to confirm this email address:</p>"
                    + "<p><a href=\"" + confirmLink + "\">Confirm link</a></p>"
                    + "<p>If you can't click the link, copy and paste " + confirmLink + " into a web browser<p>"
                    + "<blockquote><code style='letter-spacing:3px;border:solid;font-size:x-large;color:black;font-weight:bold'>" + verifyCode + "</code></blockquote>"
                    + "<p>Ignore this email if you do remember your password, "
                    + "or you have not made the request.</p>";
            sendEmail(user.getEmail(), subject, content);
            model.addAttribute("message", "We have sent a link to your email. Please check.");
        } catch (UsernameNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Error while sending email");
        }

        User.verifyCodes.put(linkCode, verifyCode);
        User.tempUsers.put(linkCode, user);
        return "register";
    }

    @PostMapping(value = "/verify")
    public String verify(@RequestParam("verify") String verifyCode, @RequestParam("token") String token, HttpServletResponse response) {
        System.out.println(User.verifyCodes);
        if (User.verifyCodes.remove(token, verifyCode)) {
            userDetailService.register(User.tempUsers.remove(token));
            try {
                response.sendRedirect("/admin_login");
            } catch (IOException e) {
                return "Cannot redirect";
            }
        } else try {
            response.sendRedirect("/verify?token=" + token);
        } catch (IOException e) {
            return "Cannot redirect";
        }
        return null;
    }
}

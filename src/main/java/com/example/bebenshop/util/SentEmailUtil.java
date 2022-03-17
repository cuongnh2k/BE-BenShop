package com.example.bebenshop.util;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
@RequiredArgsConstructor
public class SentEmailUtil {

    private final JavaMailSender mJavaMailSender;

    public void verificationCode(String email, String code) throws MessagingException {
        MimeMessage message = mJavaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
        message.setContent(code, "text/html; charset=UTF-8");
        helper.setTo(email);
        helper.setSubject("Test send HTML email");
        this.mJavaMailSender.send(message);
    }
}

package com.tmjonker.portfoliobackend.services;

import com.tmjonker.portfoliobackend.dao.EmailDetailsDAO;
import com.tmjonker.portfoliobackend.entities.EmailDetails;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ContactServiceImpl implements ContactService {

    private final String RECIPIENT_ADDRESS = "tmjonker1@outlook.com";

    private JavaMailSender javaMailSender;
    private EmailDetailsDAO emailDetailsDAO;

    public ContactServiceImpl(JavaMailSender javaMailSender, EmailDetailsDAO emailDetailsDAO) {
        this.javaMailSender = javaMailSender;
        this.emailDetailsDAO = emailDetailsDAO;
    }

    @Override
    public String sendMail(EmailDetails emailDetails) {

        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(emailDetails.getSender());
            simpleMailMessage.setTo(RECIPIENT_ADDRESS);
            simpleMailMessage.setSubject(emailDetails.getSubject());
            simpleMailMessage.setText(emailDetails.getMsg());

            javaMailSender.send(simpleMailMessage);
            emailDetails.setDate(new Date().toString());
            emailDetailsDAO.save(emailDetails);

            return "success";
        } catch (Exception e) {

            return "error";
        }
    }
}

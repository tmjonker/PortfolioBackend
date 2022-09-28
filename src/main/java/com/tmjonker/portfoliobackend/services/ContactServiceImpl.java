package com.tmjonker.portfoliobackend.services;

import com.tmjonker.portfoliobackend.dao.EmailDetailsDAO;
import com.tmjonker.portfoliobackend.entities.EmailDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ContactServiceImpl implements ContactService {

    Logger logger = LoggerFactory.getLogger(ContactServiceImpl.class);

    private final String RECIPIENT_ADDRESS = "tmjonker1@outlook.com";

    private JavaMailSender javaMailSender;
    private EmailDetailsDAO emailDetailsDAO;

    public ContactServiceImpl(JavaMailSender javaMailSender, EmailDetailsDAO emailDetailsDAO) {
        this.javaMailSender = javaMailSender;
        this.emailDetailsDAO = emailDetailsDAO;
    }

    @Override
    public void sendMail(EmailDetails emailDetails) {

        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(emailDetails.getSender());
            simpleMailMessage.setTo(RECIPIENT_ADDRESS);
            simpleMailMessage.setSubject(emailDetails.getSubject());
            simpleMailMessage.setText(emailDetails.getMsg());
            
            javaMailSender.send(simpleMailMessage);
            emailDetails.setDate(new Date());
            emailDetailsDAO.save(emailDetails);
        } catch (Exception e) {

            logger.error("Exception --> Class ContactServiceImpl --> Method sendMail() --> " + e.getMessage());
        }
    }
}

package com.tmjonker.portfoliobackend.controllers;

import com.tmjonker.portfoliobackend.entities.EmailDetails;
import com.tmjonker.portfoliobackend.services.ContactService;
import com.tmjonker.portfoliobackend.services.ContactServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactController {

    private ContactService contactServiceImpl;

    public ContactController(ContactService contactServiceImpl) {

        this.contactServiceImpl = contactServiceImpl;
    }

    @PostMapping("/contact")
    @CrossOrigin("http://localhost:4200")
    public String postContact(@RequestBody EmailDetails emailDetails) {

        return contactServiceImpl.sendMail(emailDetails);
    }
}

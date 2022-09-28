package com.tmjonker.portfoliobackend.services;

import com.tmjonker.portfoliobackend.entities.EmailDetails;

public interface ContactService {

    public void sendMail(EmailDetails emailDetails);
}

package org.example.bitespeedbackendexample.service;

import org.example.bitespeedbackendexample.dto.ContactResponse;

public interface ContactService {

  ContactResponse identifyOrCreat(String email, String phoneNumber);

}

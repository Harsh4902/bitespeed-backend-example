package org.example.bitespeedbackendexample.controller;

import org.example.bitespeedbackendexample.dto.ContactResponse;
import org.example.bitespeedbackendexample.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

  @Autowired
  ContactService contactService;

  @PostMapping("/identity")
  public ResponseEntity<ContactResponse> identifyCustomer(@RequestBody IdentifyRequest request) {
    System.out.println(request.email()+request.phoneNumber());
    ContactResponse response = contactService.identifyOrCreat(request.email(), request.phoneNumber());
    return ResponseEntity.ok(response);
  }

}



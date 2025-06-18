package org.example.bitespeedbackendexample.dto;

import java.util.List;

public class ContactResponse {

  public Long primaryContactId;
  public List<String> emails;
  public List<String> phoneNumbers;
  public List<Long> secondaryContactIds;

  public ContactResponse(Long primaryContactId, List<String> emails, List<String> phoneNumbers, List<Long> secondaryContactIds) {
    this.primaryContactId = primaryContactId;
    this.emails = emails;
    this.phoneNumbers = phoneNumbers;
    this.secondaryContactIds = secondaryContactIds;
  }

  public ContactResponse() {
  }
}

package org.example.bitespeedbackendexample.repository;

import org.example.bitespeedbackendexample.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
  List<Contact> findByEmailOrPhoneNumber(String email, String phoneNumber);
  List<Contact> findByLinkedId(Long linkedId);
}

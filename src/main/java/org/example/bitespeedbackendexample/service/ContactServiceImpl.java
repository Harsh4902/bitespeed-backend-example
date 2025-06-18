package org.example.bitespeedbackendexample.service;

import org.example.bitespeedbackendexample.dto.ContactResponse;
import org.example.bitespeedbackendexample.entity.Contact;
import org.example.bitespeedbackendexample.entity.LinkPrecedence;
import org.example.bitespeedbackendexample.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ContactServiceImpl implements ContactService{

  @Autowired
  ContactRepository contactRepository;

  @Override
  public ContactResponse identifyOrCreat(String email, String phoneNumber) {
    List<Contact> matched = contactRepository.findByEmailOrPhoneNumber(email, phoneNumber);
    System.out.println(matched);
    Set<Contact> allRelated = new HashSet<>(matched);

    // Get transitive linked contacts (those linked by linkedId or are primary for others)
    for (Contact contact : matched) {
      if (contact.getLinkedId() != null) {
        contactRepository.findById(contact.getLinkedId()).ifPresent(allRelated::add);
      }
      allRelated.addAll(contactRepository.findByLinkedId(contact.getID()));
    }

    // Find the primary contact (oldest one with linkPrecedence = PRIMARY)
    Contact primary = allRelated.stream()
      .filter(c -> c.getLinkPrecedence() == LinkPrecedence.PRIMARY)
      .min(Comparator.comparing(Contact::getCreatedAt))
      .orElse(null);

    // Fallback if no primary found
    if (primary == null && !allRelated.isEmpty()) {
      primary = allRelated.stream()
        .min(Comparator.comparing(Contact::getCreatedAt))
        .orElseThrow();
    }

    boolean emailExists = email != null && allRelated.stream()
      .anyMatch(c -> email.equals(c.getEmail()));

    boolean phoneExists = phoneNumber != null && allRelated.stream()
      .anyMatch(c -> phoneNumber.equals(c.getPhoneNumber()));

    if (primary == null) {
      // No match at all — create a new primary contact
      Contact newContact = createNewContact(email, phoneNumber, null, LinkPrecedence.PRIMARY);
      return returnContactResponse(newContact, List.of(newContact));
    }

    if ((emailExists && phoneExists) || (emailExists && phoneNumber == null) || (phoneExists && email == null)) {
      // Both already present — no need to add new contact
      return returnContactResponse(primary, allRelated);
    }

    // If either email or phone is missing in the linked contacts, create a new secondary contact
    Contact newSecondary = createNewContact(email, phoneNumber, primary.getID(), LinkPrecedence.SECONDARY);
    allRelated.add(newSecondary);

    return returnContactResponse(primary, allRelated);
  }


  private Contact createNewContact(String email, String phoneNumber, Long linkedId, LinkPrecedence precedence) {
    Contact contact = new Contact();
    contact.setEmail(email);
    contact.setPhoneNumber(phoneNumber);
    contact.setLinkPrecedence(precedence);
    contact.setLinkedId(linkedId);
    contact.setCreatedAt(LocalDateTime.now());
    contact.setUpdatedAt(LocalDateTime.now());
    return contactRepository.save(contact);
  }

  private ContactResponse returnContactResponse(Contact primary, Collection<Contact> contacts) {
    List<String> emails = contacts.stream()
      .map(Contact::getEmail).filter(Objects::nonNull).distinct().toList();
    List<String> phoneNumbers = contacts.stream()
      .map(Contact::getPhoneNumber).filter(Objects::nonNull).distinct().toList();
    List<Long> secondaryIds = contacts.stream()
      .filter(c -> c.getLinkPrecedence() == LinkPrecedence.SECONDARY)
      .map(Contact::getID).toList();

    return new ContactResponse(
      primary.getID(),
      emails,
      phoneNumbers,
      secondaryIds
    );
  }
}

package org.example.bitespeedbackendexample.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Contact {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long ID;
  private String phoneNumber;
  private String email;
  private Long linkedId;

  @Enumerated(EnumType.STRING)
  private LinkPrecedence linkPrecedence;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private LocalDateTime deletedAt;

  public Long getID() {
    return ID;
  }

  public void setID(Long ID) {
    this.ID = ID;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Long getLinkedId() {
    return linkedId;
  }

  public void setLinkedId(Long linkedId) {
    this.linkedId = linkedId;
  }

  public LinkPrecedence getLinkPrecedence() {
    return linkPrecedence;
  }

  public void setLinkPrecedence(LinkPrecedence linkPrecedence) {
    this.linkPrecedence = linkPrecedence;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public LocalDateTime getDeletedAt() {
    return deletedAt;
  }

  public void setDeletedAt(LocalDateTime deletedAt) {
    this.deletedAt = deletedAt;
  }

  @Override
  public String toString() {
    return "Contact{" +
      "ID=" + ID +
      ", phoneNumber='" + phoneNumber + '\'' +
      ", email='" + email + '\'' +
      ", linkedId=" + linkedId +
      ", linkPrecedence=" + linkPrecedence +
      ", createdAt=" + createdAt +
      ", updatedAt=" + updatedAt +
      ", deletedAt=" + deletedAt +
      '}';
  }
}

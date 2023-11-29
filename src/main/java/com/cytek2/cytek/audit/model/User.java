package com.cytek2.cytek.audit.model;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User  {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String firstname;
  private String lastname;
  private String phone;
  private String email;
  private String password;
  private String country;
  private String town;
  private String location;
  private String manager;
  private String meterId;
  private Role role;
  private LocalDateTime registrationDateTime; // Use LocalDateTime for date and time


  private String accessToken; // Add the access token field
  private String refreshToken; // Add the refresh token field

  @Enumerated(EnumType.STRING)
  private UserStatus userStatus;

  @Enumerated(EnumType.STRING)
  private  UserType userType;





  @PrePersist
  protected void onCreate() {
    registrationDateTime = LocalDateTime.now();
  }

  public User(Integer userId) {
  }


}

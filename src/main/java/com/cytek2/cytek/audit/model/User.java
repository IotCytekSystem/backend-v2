package com.cytek2.cytek.audit.model;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User  {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  private String phone;
  private String email;
  private String password;
  private String country;
  private String county;
  private String town;
  private Role role;

  private String accessToken; // Add the access token field
  private String refreshToken; // Add the refresh token field


  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  @JsonManagedReference
  private List<Meter> meters;



  public User(Integer userId) {
  }
}

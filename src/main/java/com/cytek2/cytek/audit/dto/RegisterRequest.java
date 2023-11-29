package com.cytek2.cytek.audit.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.management.relation.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class RegisterRequest {
  public String firstname;
  public String lastname;
  public String email;
  public String password;
  public String phone;


}

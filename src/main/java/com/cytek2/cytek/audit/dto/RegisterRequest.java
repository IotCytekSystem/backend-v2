package com.cytek2.cytek.audit.dto;

import com.cytek2.cytek.audit.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  private String name;
  private String email;
  private String password;
  private Role role;
  private String phone;
  private String country;
  private String county;
  private String town;

}

package com.cytek2.cytek.audit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddClientRequest {
    public String lastname;
    public String firstname;
    public String phone;
    public String email;
    public String password;
    public String country;
    public String town;
    public String location;
    public String manager;
    public String meterId;
}

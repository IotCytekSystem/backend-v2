package com.cytek2.cytek.audit.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
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

  @OneToMany(mappedBy = "user")
  private List<Meter> meters;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<EnergyData> energyDataList = new ArrayList<>();


  public void setArchived(boolean b) {
  }
}

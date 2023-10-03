package com.cytek2.cytek.audit.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


import java.util.Collections;

import java.util.Set;
import java.util.stream.Collectors;

import static com.cytek2.cytek.audit.model.Permission.*;


@RequiredArgsConstructor
public enum Role {

  USER(Collections.emptySet()),
  ADMIN(
          Set.of(
                  ADMIN_READ,
                  ADMIN_UPDATE,
                  ADMIN_DELETE,
                  ADMIN_CREATE,
                  MANAGER_READ,
                  MANAGER_UPDATE,
                  MANAGER_DELETE,
                  MANAGER_CREATE
          )
  ),
  MANAGER(
          Set.of(
                  MANAGER_READ,
                  MANAGER_UPDATE,
                  MANAGER_DELETE,
                  MANAGER_CREATE
          )
  ),
  CLIENT(
          Set.of(
                CLIENT_UPDATE,
                  CLIENT_READ,
                  CLIENT_CREATE,
                  CLIENT_DELETE
          )
  )

  ;

  @Getter
  private final Set<Permission> permissions;


}

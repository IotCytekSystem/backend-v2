package com.cytek2.cytek.audit.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Set;

import static com.cytek2.cytek.audit.model.Permission.*;


@RequiredArgsConstructor
public enum UserStatus {
    PENDING,
   APPROVED,
    REJECTED,
    ARCHIVED

}

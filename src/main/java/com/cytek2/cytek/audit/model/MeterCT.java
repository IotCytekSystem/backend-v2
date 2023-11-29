package com.cytek2.cytek.audit.model;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public enum MeterCT {
    CT_150(150),
    CT_250(250),
    CT_500(500);

    private final int value;

    public int getValue() {
        return value;
    }
}
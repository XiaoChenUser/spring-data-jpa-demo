package com.example.quickstart.enumeration;

import lombok.Getter;

@Getter
public enum GenderEnum {
    MALE("男"),
    FEMALE("女"),
    UNKNOWN("未知");

    private String gender;

    GenderEnum(String gender) {
        this.gender = gender;
    }
}

package com.example.novasparkle.LunaEngine.telegram.utils;

import lombok.Getter;

@Getter
public enum Version {
    V1_16_5("1.16.5"),
    V1_17_1("1.17.1"),
    V1_18_2("1.18.2"),
    V1_19_4("1.19.4"),
    V1_20_6("1.20.6"),
    V1_21("1.21");
    private final String versionName;
    Version(String versionName) {
        this.versionName = versionName;
    }
}

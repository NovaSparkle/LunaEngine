package com.example.novasparkle.LunaEngine.telegram.utils;

import com.example.novasparkle.LunaEngine.configuration.Messages;
import lombok.Getter;

@Getter
public enum KeyQueryAnswer {
    ACCEPT,
    REJECT;
    private final String text;

    KeyQueryAnswer() {
        this.text = Messages.getMessage(name());
    }
}

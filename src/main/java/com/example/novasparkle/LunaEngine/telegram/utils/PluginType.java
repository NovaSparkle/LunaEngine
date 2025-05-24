package com.example.novasparkle.LunaEngine.telegram.utils;

import com.example.novasparkle.LunaEngine.configuration.Messages;
import lombok.Getter;

@Getter
public enum PluginType {
    FREE,
    PAID;
    private final String type;
    PluginType() {
        this.type = Messages.getMessage(name());
    }
}

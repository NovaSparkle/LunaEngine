package com.example.novasparkle.LunaEngine.configuration;

import org.yaml.snakeyaml.Yaml;

import java.util.Map;

public class Messages {
    private static final Map<String, Object> messages;

    static   {
        messages = new Yaml().load(Thread.currentThread().getContextClassLoader().getResourceAsStream("messages.yml"));
    }
    public static String getMessage(String key) {
        return (String) messages.get(key);
    }
}

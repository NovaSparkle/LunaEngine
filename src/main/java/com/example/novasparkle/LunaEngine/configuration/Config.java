package com.example.novasparkle.LunaEngine.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource("application.properties")
public class Config {
    @Value("${bot.name}")
    String botName;
    @Value("${bot.token}")
    String botToken;
    @Value("${admin.userId}")
    String adminId;

    public long getAdminUserId() {
        return Long.parseLong(this.adminId);
    }
}

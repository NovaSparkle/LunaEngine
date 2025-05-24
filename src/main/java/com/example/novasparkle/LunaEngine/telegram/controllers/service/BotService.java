package com.example.novasparkle.LunaEngine.telegram.controllers.service;

import com.example.novasparkle.LunaEngine.telegram.controllers.KeyController;
import com.example.novasparkle.LunaEngine.telegram.controllers.PluginController;
import com.example.novasparkle.LunaEngine.telegram.controllers.UserController;
import jakarta.transaction.Transactional;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
@Getter
@Transactional
public class BotService {
    private final UserController userController;
    private final PluginController pluginController;
    private final KeyController keyController;

    public BotService(UserController userController, PluginController pluginController, KeyController keyController) {
        this.userController = userController;
        this.pluginController = pluginController;
        this.keyController = keyController;
    }
}

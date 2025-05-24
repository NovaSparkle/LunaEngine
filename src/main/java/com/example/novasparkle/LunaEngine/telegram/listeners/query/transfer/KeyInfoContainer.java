package com.example.novasparkle.LunaEngine.telegram.listeners.query.transfer;

import com.example.novasparkle.LunaEngine.telegram.models.Plugin;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.User;

@Getter
@RequiredArgsConstructor
public class KeyInfoContainer implements TransferContainer {
    private final User sender;
    private final String ipv4;
    private Plugin plugin;
    @Override
    public <T> void set(T type) {
        if (type instanceof Plugin plugin) {
            this.plugin = plugin;
        }
    }
}

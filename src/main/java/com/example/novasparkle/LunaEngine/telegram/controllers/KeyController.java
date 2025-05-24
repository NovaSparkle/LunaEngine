package com.example.novasparkle.LunaEngine.telegram.controllers;

import com.example.novasparkle.LunaEngine.telegram.models.Key;
import com.example.novasparkle.LunaEngine.telegram.models.Plugin;
import com.example.novasparkle.LunaEngine.telegram.repo.KeyRepository;
import com.example.novasparkle.LunaEngine.telegram.utils.PluginType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class KeyController {
    @Autowired
    private KeyRepository keysRepository;

    public List<Key> getKeys() {
        List<Key> keys = new ArrayList<>();
        this.keysRepository.findAll().forEach(keys::add);
        return keys;
    }
    public List<Key> getKeys(Plugin plugin) {
        return this.keysRepository.findAllByPlugin(plugin);
    }
    public List<Key> getKeys(String ipv4) {
        return this.keysRepository.findAllByIpv4(ipv4);
    }
    public boolean register(Plugin plugin, String ipv4) {
        if (!plugin.getPluginType().equals(PluginType.FREE.name())) return false;
        Key key = new Key();
        key.setValue(UUID.randomUUID().toString());
        key.setIpv4(ipv4);
        plugin.addKey(key);
        this.keysRepository.save(key);
        return true;
    }
}

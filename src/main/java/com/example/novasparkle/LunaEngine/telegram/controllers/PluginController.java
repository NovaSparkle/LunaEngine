package com.example.novasparkle.LunaEngine.telegram.controllers;

import com.example.novasparkle.LunaEngine.telegram.models.Plugin;
import com.example.novasparkle.LunaEngine.telegram.models.User;
import com.example.novasparkle.LunaEngine.telegram.repo.PluginRepository;
import com.example.novasparkle.LunaEngine.telegram.utils.PluginType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PluginController {
    @Autowired
    private PluginRepository pluginsRepository;

    public List<Plugin> getPlugins() {
        List<Plugin> plugins = new ArrayList<>();
        this.pluginsRepository.findAll().forEach(plugins::add);
        return plugins;
    }
    public Plugin getPlugin(String name) {
        return this.pluginsRepository.findByName(name);
    }
    public List<Plugin> getPlugins(User author) {
        return this.pluginsRepository.findAllByAuthor(author);
    }
    public List<Plugin> getPlugins(PluginType pluginType) {
        return this.pluginsRepository.findAllByPluginType(pluginType.name());
    }
    public boolean exists(Plugin plugin) {
        return this.pluginsRepository.existsById(plugin.getName());
    }
    public void register(String name, User author, String version, PluginType pluginType) {
        Plugin plugin = new Plugin();
        plugin.setName(name);
        plugin.setVersion(version);
        plugin.setPluginType(pluginType.name());
        author.addPlugin(plugin);
        pluginsRepository.save(plugin);
    }
}

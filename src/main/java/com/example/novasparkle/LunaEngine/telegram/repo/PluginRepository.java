package com.example.novasparkle.LunaEngine.telegram.repo;

import com.example.novasparkle.LunaEngine.telegram.models.Plugin;
import com.example.novasparkle.LunaEngine.telegram.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PluginRepository extends CrudRepository<Plugin, String> {
    List<Plugin> findAllByPluginType(String pluginType);
    List<Plugin> findAllByAuthor(User author);
    Plugin findByName(String name);
}

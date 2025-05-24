package com.example.novasparkle.LunaEngine.telegram.models;

import com.example.novasparkle.LunaEngine.telegram.utils.Permission;
import com.example.novasparkle.LunaEngine.telegram.utils.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    private long id;
    private String firstName;
    private String userName;
    private Status status;

    @OneToMany(mappedBy = "author", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Plugin> plugins;

    public void addPlugin(Plugin plugin) {
        if (this.status.getWeight() >= 1) {
            this.plugins.add(plugin);
            plugin.setAuthor(this);
        }
    }
    public void removePlugin(Plugin plugin) {
        this.plugins.remove(plugin);
        plugin.setAuthor(null);
    }
    public boolean hasPermission(Permission permission) {
        return this.getStatus().hasPermission(permission);
    }
}

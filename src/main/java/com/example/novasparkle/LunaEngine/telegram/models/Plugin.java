package com.example.novasparkle.LunaEngine.telegram.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Entity
@Table(name = "plugins")
@Data
public class Plugin {
    @Id
    private String name;

    @ManyToOne()
    private User author;
    private String version;
    private String pluginType;

    @OneToMany(mappedBy = "plugin", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Key> keys;

    public void addKey(Key key) {
        this.keys.add(key);
        key.setPlugin(this);
    }
    public void removeKey(Key key) {
        this.keys.remove(key);
        key.setPlugin(null);
    }
}

package com.example.novasparkle.LunaEngine.telegram.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "plugin_keys")
@Data
public class Key {
    @Id
    @GeneratedValue
    private long id;
    private String value;
    @ManyToOne
    private Plugin plugin;
    private String ipv4;
}

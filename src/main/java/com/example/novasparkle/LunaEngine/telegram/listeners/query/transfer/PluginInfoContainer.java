package com.example.novasparkle.LunaEngine.telegram.listeners.query.transfer;

import com.example.novasparkle.LunaEngine.telegram.models.User;
import com.example.novasparkle.LunaEngine.telegram.utils.PluginType;
import com.example.novasparkle.LunaEngine.telegram.utils.Version;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PluginInfoContainer implements TransferContainer {
    private final String pluginName;

    private Version version;

    private User developer;

    private PluginType pluginType;

    @Override
    public String toString() {
        return "PluginInfoContainer{" +
                "pluginName='" + pluginName + '\'' +
                ", version=" + version +
                ", developer=" + developer +
                ", pluginType=" + pluginType +
                '}';
    }

    @Override
    public <T> void set(T type) {
        if (type instanceof Version version) {
            this.version = version;
        } else if (type instanceof User developer) {
            this.developer = developer;
        } else if (type instanceof PluginType pluginType) {
            this.pluginType = pluginType;
        }
    }
}
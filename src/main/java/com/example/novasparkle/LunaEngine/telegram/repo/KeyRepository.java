package com.example.novasparkle.LunaEngine.telegram.repo;

import com.example.novasparkle.LunaEngine.telegram.models.Key;
import com.example.novasparkle.LunaEngine.telegram.models.Plugin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeyRepository extends CrudRepository<Key, Long> {
    List<Key> findAllByPlugin(Plugin plugin);
    List<Key> findAllByIpv4(String ipv4);
}

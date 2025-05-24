package com.example.novasparkle.LunaEngine.telegram.repo;

import com.example.novasparkle.LunaEngine.telegram.utils.Status;
import com.example.novasparkle.LunaEngine.telegram.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findAllByStatus(Status status);
    User findByFirstName(String firstName);
}
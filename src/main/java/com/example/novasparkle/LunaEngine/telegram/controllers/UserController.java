package com.example.novasparkle.LunaEngine.telegram.controllers;


import com.example.novasparkle.LunaEngine.telegram.models.User;
import com.example.novasparkle.LunaEngine.telegram.repo.UserRepository;
import com.example.novasparkle.LunaEngine.telegram.utils.Status;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @SneakyThrows
    public void register(org.telegram.telegrambots.meta.api.objects.User tgUser, Status status) {
        User user = new User();
        user.setId(tgUser.getId());
        user.setUserName(tgUser.getUserName());
        user.setFirstName(tgUser.getFirstName());
        user.setStatus(status);

        this.userRepository.save(user);
    }

    public boolean isAdmin(long userId) {
        return this.userRepository.findById(userId).map(user -> user.getStatus().equals(Status.ADMIN)).orElse(false);
    }
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        this.userRepository.findAll().forEach(users::add);
        return users;
    }
    public List<User> getUsers(Status status) {
        return this.userRepository.findAllByStatus(status);
    }
    public User getUser(String firstName) {
        return this.userRepository.findByFirstName(firstName);
    }
    public User getUser(long id) {
        return this.userRepository.findById(id).orElse(null);
    }
}

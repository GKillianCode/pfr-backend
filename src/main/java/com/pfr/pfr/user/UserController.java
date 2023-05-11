package com.pfr.pfr.user;

import com.pfr.pfr.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Validated
@CrossOrigin(origins = {"${app.api.settings.cross-origin.url}"})
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<User> getAllUsers() { return userService.getAll(); }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Integer userId) {
        return userService.getById(userId);
    }

    @GetMapping("/{id}/promos")
    public UserWithPromos getUserWithPromos(@PathVariable("id") Integer userId) {
        return userService.getUserWithPromos(userId);
    }

    @PostMapping("/connect")
    public String connect(@RequestBody User user) {
        return userService.connect(user.getEmail(), user.getPassword());
    }
}

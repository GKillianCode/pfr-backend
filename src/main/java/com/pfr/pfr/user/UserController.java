package com.pfr.pfr.user;

import com.pfr.pfr.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/connect")
    public String connect(@RequestBody User user) {
        return userService.connect(user.getEmail(), user.getPassword());
    }
}

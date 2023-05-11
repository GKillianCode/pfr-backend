package com.pfr.pfr.user;

import com.pfr.pfr.entities.Promo;
import com.pfr.pfr.entities.User;
import com.pfr.pfr.entities.repository.PromoRepository;
import com.pfr.pfr.entities.repository.UserRepository;
import com.pfr.pfr.user.dto.UserWithPromos;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PromoRepository promoRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(int userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return user.get();
        }
        throw new EntityNotFoundException("User with ID %d not found".formatted(userId));
    }

    public UserWithPromos getUserWithPromos(int userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            List<Promo> listPromos = promoRepository.findPromosFromUser(userId);
            return new UserWithPromos(user.get(), listPromos);
        }
        throw new EntityNotFoundException("User with ID %d not found".formatted(userId));

    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public String connect(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return "lorem ipsum connected";
        }
        return null;
    }

}

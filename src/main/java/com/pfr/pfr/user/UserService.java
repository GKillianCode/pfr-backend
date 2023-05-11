package com.pfr.pfr.user;

import com.pfr.pfr.entities.Promo;
import com.pfr.pfr.entities.User;
import com.pfr.pfr.entities.repository.UserRepository;
import com.pfr.pfr.promo.dto.PromoWithEvents;
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

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public UserWithPromos getUserWithPromos(int userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            List<Promo> listPromos = userRepository.findPromosFromUser(userId);
            return new UserWithPromos(user.get(), listPromos);
        }
        throw new EntityNotFoundException("User with ID %d not found".formatted(userId));

    }

}

package com.journeyplanner.user.domain.user;

import com.journeyplanner.user.domain.exceptions.ResourceNotFound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

class UserRepositoryInMemory implements UserRepository {

    private ConcurrentHashMap<String, User> db = new ConcurrentHashMap<>();

    @Override
    public User save(User user) {
        return db.put(user.getId(), user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return db.values()
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public boolean existsByEmail(String email) {
        return findByEmail(email).isPresent();
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updatePassword(String email, String password) {
        User user = findByEmail(email)
                .orElseThrow(() -> new ResourceNotFound("Cannot found user with this email"));

        User updatedUser = User.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .secondName(user.getSecondName())
                .role(user.getRole())
                .password(password)
                .build();

        db.put(user.getId(), updatedUser);
    }

    @Override
    public void changeIsBlacklisted(String email, boolean isBlocked) {
        throw new UnsupportedOperationException();
    }
}

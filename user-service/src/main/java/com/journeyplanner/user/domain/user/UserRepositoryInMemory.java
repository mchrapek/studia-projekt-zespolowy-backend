package com.journeyplanner.user.domain.user;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

class UserRepositoryInMemory implements UserRepository {

    private ConcurrentHashMap<String, User> store = new ConcurrentHashMap<>();

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }
}

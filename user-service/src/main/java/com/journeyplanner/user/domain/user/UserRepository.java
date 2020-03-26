package com.journeyplanner.user.domain.user;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

interface UserRepository extends
        Repository<User, String>, UserCustomRepository,
        QuerydslPredicateExecutor<User>, QuerydslBinderCustomizer<QUser> {

    User save(User user);

    Optional<User> findByEmail(String email);

    Optional<User> findById(String id);

    List<User> findByRole(String role);

    boolean existsByEmail(String email);

    Page<User> findAll(Predicate predicate, Pageable pageable);

    void deleteAll();

    @Override
    default void customize(QuerydslBindings bindings, QUser root) {
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>)
                (stringPath, str) -> stringPath.containsIgnoreCase(str));

        bindings.excluding(root.password);
    }
}

package com.journeyplanner.catalogue.domain.journey;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.MultiValueBinding;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.repository.Repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

interface JourneyRepository extends
        Repository<Journey, String>, CustomJourneyRepository,
        QuerydslPredicateExecutor<Journey>, QuerydslBinderCustomizer<QJourney> {

    Page<Journey> findAll(Predicate predicate, Pageable pageable);

    List<Journey> findByGuideEmail(String email);

    Journey save(Journey journey);

    Optional<Journey> findById(String id);

    void deleteAll();

    @Override
    default void customize(QuerydslBindings bindings, QJourney root) {
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>)
                (stringPath, str) -> stringPath.containsIgnoreCase(str));
        bindings.bind(BigDecimal.class).all((MultiValueBinding<NumberPath<BigDecimal>, BigDecimal>)
                (path, values) -> Optional.ofNullable(getNumberCondition(path, values)));
        bindings.bind(Instant.class).all((MultiValueBinding<DateTimePath<Instant>, Instant>)
                (path, values) -> Optional.ofNullable(getTemporalCondition(path, values)));

        bindings.excluding(root.description);
        bindings.excluding(root.id);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    default BooleanExpression getTemporalCondition(final TemporalExpression path, Collection<? extends Comparable> values) {
        final BooleanExpression expression;

        if (values.size() == 2) {
            final List<? extends Comparable> dateValues = new ArrayList<>(values);
            Collections.sort(dateValues);
            expression = path.between(dateValues.get(0), dateValues.get(1));
        } else {
            expression = path.goe(values.iterator().next());
        }

        return expression;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    default BooleanExpression getNumberCondition(final NumberPath path, Collection<? extends Number> values) {
        final BooleanExpression expression;

        if (values.size() == 2) {
            final List<? extends Comparable> numberValues = new ArrayList(values);
            Collections.sort(numberValues);
            expression = path.between((Number) numberValues.get(0), (Number) numberValues.get(1));
        } else {
            expression = path.eq(values.iterator().next());
        }

        return expression;
    }
}

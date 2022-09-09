package com.example.managespending.data.remotes.service;

import com.example.managespending.data.models.entities.Budget;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface BudgetService {


    Budget findBudgetByRAPName(String rapName);

    List<Budget> findAll();

    List<Budget> findAll(Sort sort);

    List<Budget> findAllById(Iterable<Long> longs);

    <S extends Budget> List<S> saveAll(Iterable<S> entities);

    void flush();

    <S extends Budget> S saveAndFlush(S entity);

    <S extends Budget> List<S> saveAllAndFlush(Iterable<S> entities);

    void deleteAllInBatch(Iterable<Budget> entities);

    void deleteAllByIdInBatch(Iterable<Long> longs);

    void deleteAllInBatch();

    Budget getReferenceById(Long aLong);

    <S extends Budget> List<S> findAll(Example<S> example);

    <S extends Budget> List<S> findAll(Example<S> example, Sort sort);

    Page<Budget> findAll(Pageable pageable);

    <S extends Budget> S save(S entity);

    Optional<Budget> findById(Long aLong);

    boolean existsById(Long aLong);

    long count();

    void deleteById(Long aLong);

    void delete(Budget entity);

    void deleteAllById(Iterable<? extends Long> longs);

    void deleteAll(Iterable<? extends Budget> entities);

    void deleteAll();

    <S extends Budget> Optional<S> findOne(Example<S> example);

    <S extends Budget> Page<S> findAll(Example<S> example, Pageable pageable);

    <S extends Budget> long count(Example<S> example);

    <S extends Budget> boolean exists(Example<S> example);

    <S extends Budget, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction);
}

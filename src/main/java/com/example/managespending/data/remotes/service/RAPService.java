package com.example.managespending.data.remotes.service;

import com.example.managespending.data.models.entities.RAP;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface RAPService {
    List<RAP> findAll();

    List<RAP> findAll(Sort sort);

    List<RAP> findAllById(Iterable<Long> longs);

    <S extends RAP> List<S> saveAll(Iterable<S> entities);

    void flush();

    <S extends RAP> S saveAndFlush(S entity);

    <S extends RAP> List<S> saveAllAndFlush(Iterable<S> entities);

    void deleteAllInBatch(Iterable<RAP> entities);

    void deleteAllByIdInBatch(Iterable<Long> longs);

    void deleteAllInBatch();

    RAP getReferenceById(Long aLong);

    <S extends RAP> List<S> findAll(Example<S> example);

    <S extends RAP> List<S> findAll(Example<S> example, Sort sort);

    Page<RAP> findAll(Pageable pageable);

    <S extends RAP> S save(S entity);

    Optional<RAP> findById(Long aLong);

    boolean existsById(Long aLong);

    long count();

    void deleteById(Long aLong);

    void delete(RAP entity);

    void deleteAllById(Iterable<? extends Long> longs);

    void deleteAll(Iterable<? extends RAP> entities);

    void deleteAll();

    <S extends RAP> Optional<S> findOne(Example<S> example);

    <S extends RAP> Page<S> findAll(Example<S> example, Pageable pageable);

    <S extends RAP> long count(Example<S> example);

    <S extends RAP> boolean exists(Example<S> example);

    <S extends RAP, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction);
}

package com.example.managespending.data.remotes.service;

import com.example.managespending.data.models.entities.RAPCategory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface RAPCategoryService {


    RAPCategory findRAPCategoryByRapCategoryName(String receiptsAndPaymentsCategoryName);

    List<RAPCategory> findAll();

    List<RAPCategory> findAll(Sort sort);

    List<RAPCategory> findAllById(Iterable<Long> longs);

    <S extends RAPCategory> List<S> saveAll(Iterable<S> entities);

    void flush();

    <S extends RAPCategory> S saveAndFlush(S entity);

    <S extends RAPCategory> List<S> saveAllAndFlush(Iterable<S> entities);

    void deleteAllInBatch(Iterable<RAPCategory> entities);

    void deleteAllByIdInBatch(Iterable<Long> longs);

    void deleteAllInBatch();

    RAPCategory getReferenceById(Long aLong);

    <S extends RAPCategory> List<S> findAll(Example<S> example);

    <S extends RAPCategory> List<S> findAll(Example<S> example, Sort sort);

    Page<RAPCategory> findAll(Pageable pageable);

    <S extends RAPCategory> S save(S entity);

    Optional<RAPCategory> findById(Long aLong);

    boolean existsById(Long aLong);

    long count();

    void deleteById(Long aLong);

    void delete(RAPCategory entity);

    void deleteAllById(Iterable<? extends Long> longs);

    void deleteAll(Iterable<? extends RAPCategory> entities);

    void deleteAll();

    <S extends RAPCategory> Optional<S> findOne(Example<S> example);

    <S extends RAPCategory> Page<S> findAll(Example<S> example, Pageable pageable);

    <S extends RAPCategory> long count(Example<S> example);

    <S extends RAPCategory> boolean exists(Example<S> example);

    <S extends RAPCategory, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction);
}

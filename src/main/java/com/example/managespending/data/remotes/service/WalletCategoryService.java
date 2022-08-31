package com.example.managespending.data.remotes.service;

import com.example.managespending.data.models.entities.WalletCategory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface WalletCategoryService {
    List<WalletCategory> findAll();

    List<WalletCategory> findAll(Sort sort);

    List<WalletCategory> findAllById(Iterable<Long> longs);

    <S extends WalletCategory> List<S> saveAll(Iterable<S> entities);

    void flush();

    <S extends WalletCategory> S saveAndFlush(S entity);

    <S extends WalletCategory> List<S> saveAllAndFlush(Iterable<S> entities);

    void deleteAllInBatch(Iterable<WalletCategory> entities);

    void deleteAllByIdInBatch(Iterable<Long> longs);

    void deleteAllInBatch();

    WalletCategory getReferenceById(Long aLong);

    <S extends WalletCategory> List<S> findAll(Example<S> example);

    <S extends WalletCategory> List<S> findAll(Example<S> example, Sort sort);

    Page<WalletCategory> findAll(Pageable pageable);

    <S extends WalletCategory> S save(S entity);

    Optional<WalletCategory> findById(Long aLong);

    boolean existsById(Long aLong);

    long count();

    void deleteById(Long aLong);

    void delete(WalletCategory entity);

    void deleteAllById(Iterable<? extends Long> longs);

    void deleteAll(Iterable<? extends WalletCategory> entities);

    void deleteAll();

    <S extends WalletCategory> Optional<S> findOne(Example<S> example);

    <S extends WalletCategory> Page<S> findAll(Example<S> example, Pageable pageable);

    <S extends WalletCategory> long count(Example<S> example);

    <S extends WalletCategory> boolean exists(Example<S> example);

    <S extends WalletCategory, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction);
}

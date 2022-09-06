package com.example.managespending.data.remotes.service;

import com.example.managespending.data.models.entities.Wallet;
import com.example.managespending.data.models.entities.key.WalletKey;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface WalletService {


    List<Wallet> findAll();

    List<Wallet> findAll(Sort sort);

    List<Wallet> findAllById(Iterable<WalletKey> walletKeys);

    <S extends Wallet> List<S> saveAll(Iterable<S> entities);

    void flush();

    <S extends Wallet> S saveAndFlush(S entity);

    <S extends Wallet> List<S> saveAllAndFlush(Iterable<S> entities);

    void deleteAllInBatch(Iterable<Wallet> entities);

    void deleteAllByIdInBatch(Iterable<WalletKey> walletKeys);

    void deleteAllInBatch();

    Wallet getReferenceById(WalletKey walletKey);

    <S extends Wallet> List<S> findAll(Example<S> example);

    <S extends Wallet> List<S> findAll(Example<S> example, Sort sort);

    Page<Wallet> findAll(Pageable pageable);

    <S extends Wallet> S save(S entity);

    Optional<Wallet> findById(WalletKey walletKey);

    boolean existsById(WalletKey walletKey);

    long count();

    void deleteById(WalletKey walletKey);

    void delete(Wallet entity);

    void deleteAllById(Iterable<? extends WalletKey> walletKeys);

    void deleteAll(Iterable<? extends Wallet> entities);

    void deleteAll();

    <S extends Wallet> Optional<S> findOne(Example<S> example);

    <S extends Wallet> Page<S> findAll(Example<S> example, Pageable pageable);

    <S extends Wallet> long count(Example<S> example);

    <S extends Wallet> boolean exists(Example<S> example);

    <S extends Wallet, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction);
}

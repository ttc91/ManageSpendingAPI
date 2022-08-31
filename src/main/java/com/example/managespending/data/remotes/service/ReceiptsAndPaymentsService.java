package com.example.managespending.data.remotes.service;

import com.example.managespending.data.models.entities.ReceiptsAndPayments;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface ReceiptsAndPaymentsService {
    List<ReceiptsAndPayments> findAll();

    List<ReceiptsAndPayments> findAll(Sort sort);

    List<ReceiptsAndPayments> findAllById(Iterable<Long> longs);

    <S extends ReceiptsAndPayments> List<S> saveAll(Iterable<S> entities);

    void flush();

    <S extends ReceiptsAndPayments> S saveAndFlush(S entity);

    <S extends ReceiptsAndPayments> List<S> saveAllAndFlush(Iterable<S> entities);

    void deleteAllInBatch(Iterable<ReceiptsAndPayments> entities);

    void deleteAllByIdInBatch(Iterable<Long> longs);

    void deleteAllInBatch();

    ReceiptsAndPayments getReferenceById(Long aLong);

    <S extends ReceiptsAndPayments> List<S> findAll(Example<S> example);

    <S extends ReceiptsAndPayments> List<S> findAll(Example<S> example, Sort sort);

    Page<ReceiptsAndPayments> findAll(Pageable pageable);

    <S extends ReceiptsAndPayments> S save(S entity);

    Optional<ReceiptsAndPayments> findById(Long aLong);

    boolean existsById(Long aLong);

    long count();

    void deleteById(Long aLong);

    void delete(ReceiptsAndPayments entity);

    void deleteAllById(Iterable<? extends Long> longs);

    void deleteAll(Iterable<? extends ReceiptsAndPayments> entities);

    void deleteAll();

    <S extends ReceiptsAndPayments> Optional<S> findOne(Example<S> example);

    <S extends ReceiptsAndPayments> Page<S> findAll(Example<S> example, Pageable pageable);

    <S extends ReceiptsAndPayments> long count(Example<S> example);

    <S extends ReceiptsAndPayments> boolean exists(Example<S> example);

    <S extends ReceiptsAndPayments, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction);
}

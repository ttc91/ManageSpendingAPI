package com.example.managespending.data.remotes.service;

import com.example.managespending.data.models.entities.ReceiptsAndPaymentsCategory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface ReceiptsAndPaymentsCategoryService {
    List<ReceiptsAndPaymentsCategory> findAll();

    List<ReceiptsAndPaymentsCategory> findAll(Sort sort);

    List<ReceiptsAndPaymentsCategory> findAllById(Iterable<Long> longs);

    <S extends ReceiptsAndPaymentsCategory> List<S> saveAll(Iterable<S> entities);

    void flush();

    <S extends ReceiptsAndPaymentsCategory> S saveAndFlush(S entity);

    <S extends ReceiptsAndPaymentsCategory> List<S> saveAllAndFlush(Iterable<S> entities);

    void deleteAllInBatch(Iterable<ReceiptsAndPaymentsCategory> entities);

    void deleteAllByIdInBatch(Iterable<Long> longs);

    void deleteAllInBatch();

    ReceiptsAndPaymentsCategory getReferenceById(Long aLong);

    <S extends ReceiptsAndPaymentsCategory> List<S> findAll(Example<S> example);

    <S extends ReceiptsAndPaymentsCategory> List<S> findAll(Example<S> example, Sort sort);

    Page<ReceiptsAndPaymentsCategory> findAll(Pageable pageable);

    <S extends ReceiptsAndPaymentsCategory> S save(S entity);

    Optional<ReceiptsAndPaymentsCategory> findById(Long aLong);

    boolean existsById(Long aLong);

    long count();

    void deleteById(Long aLong);

    void delete(ReceiptsAndPaymentsCategory entity);

    void deleteAllById(Iterable<? extends Long> longs);

    void deleteAll(Iterable<? extends ReceiptsAndPaymentsCategory> entities);

    void deleteAll();

    <S extends ReceiptsAndPaymentsCategory> Optional<S> findOne(Example<S> example);

    <S extends ReceiptsAndPaymentsCategory> Page<S> findAll(Example<S> example, Pageable pageable);

    <S extends ReceiptsAndPaymentsCategory> long count(Example<S> example);

    <S extends ReceiptsAndPaymentsCategory> boolean exists(Example<S> example);

    <S extends ReceiptsAndPaymentsCategory, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction);
}

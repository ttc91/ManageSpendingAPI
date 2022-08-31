package com.example.managespending.data.remotes.service.impl;

import com.example.managespending.data.models.entities.ReceiptsAndPaymentsCategory;
import com.example.managespending.data.remotes.repositories.ReceiptsAndPaymentsCategoryRepository;
import com.example.managespending.data.remotes.service.ReceiptsAndPaymentsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class ReceiptsAndPaymentsCategoryServiceImpl implements ReceiptsAndPaymentsCategoryService {

    @Autowired
    private ReceiptsAndPaymentsCategoryRepository repository;

    @Override
    public List<ReceiptsAndPaymentsCategory> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ReceiptsAndPaymentsCategory> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Override
    public List<ReceiptsAndPaymentsCategory> findAllById(Iterable<Long> longs) {
        return repository.findAllById(longs);
    }

    @Override
    public <S extends ReceiptsAndPaymentsCategory> List<S> saveAll(Iterable<S> entities) {
        return repository.saveAll(entities);
    }

    @Override
    public void flush() {
        repository.flush();
    }

    @Override
    public <S extends ReceiptsAndPaymentsCategory> S saveAndFlush(S entity) {
        return repository.saveAndFlush(entity);
    }

    @Override
    public <S extends ReceiptsAndPaymentsCategory> List<S> saveAllAndFlush(Iterable<S> entities) {
        return repository.saveAllAndFlush(entities);
    }

    @Override
    public void deleteAllInBatch(Iterable<ReceiptsAndPaymentsCategory> entities) {
        repository.deleteAllInBatch(entities);
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {
        repository.deleteAllByIdInBatch(longs);
    }

    @Override
    public void deleteAllInBatch() {
        repository.deleteAllInBatch();
    }

    @Override
    public ReceiptsAndPaymentsCategory getReferenceById(Long aLong) {
        return repository.getReferenceById(aLong);
    }

    @Override
    public <S extends ReceiptsAndPaymentsCategory> List<S> findAll(Example<S> example) {
        return repository.findAll(example);
    }

    @Override
    public <S extends ReceiptsAndPaymentsCategory> List<S> findAll(Example<S> example, Sort sort) {
        return repository.findAll(example, sort);
    }

    @Override
    public Page<ReceiptsAndPaymentsCategory> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public <S extends ReceiptsAndPaymentsCategory> S save(S entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<ReceiptsAndPaymentsCategory> findById(Long aLong) {
        return repository.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return repository.existsById(aLong);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public void deleteById(Long aLong) {
        repository.deleteById(aLong);
    }

    @Override
    public void delete(ReceiptsAndPaymentsCategory entity) {
        repository.delete(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {
        repository.deleteAllById(longs);
    }

    @Override
    public void deleteAll(Iterable<? extends ReceiptsAndPaymentsCategory> entities) {
        repository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public <S extends ReceiptsAndPaymentsCategory> Optional<S> findOne(Example<S> example) {
        return repository.findOne(example);
    }

    @Override
    public <S extends ReceiptsAndPaymentsCategory> Page<S> findAll(Example<S> example, Pageable pageable) {
        return repository.findAll(example, pageable);
    }

    @Override
    public <S extends ReceiptsAndPaymentsCategory> long count(Example<S> example) {
        return repository.count(example);
    }

    @Override
    public <S extends ReceiptsAndPaymentsCategory> boolean exists(Example<S> example) {
        return repository.exists(example);
    }

    @Override
    public <S extends ReceiptsAndPaymentsCategory, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return repository.findBy(example, queryFunction);
    }
}

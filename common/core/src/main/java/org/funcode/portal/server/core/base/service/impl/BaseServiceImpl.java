/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.core.base.service.impl;


import org.funcode.portal.server.core.base.entity.BaseEntity;
import org.funcode.portal.server.core.base.repository.IBaseRepository;
import org.funcode.portal.server.core.base.service.IBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Slf4j
@Transactional
public abstract class BaseServiceImpl<T extends BaseEntity, I extends Serializable> implements IBaseService<T, I> {

    /**
     * @return IBaseDao
     */
    public abstract IBaseRepository<T, I> getBaseRepository();

    /**
     * findById.
     *
     * @param id id
     * @return T
     */
    @Override
    public T find(I id) {
        return getBaseRepository().findById(id).orElse(null);
    }

    /**
     * @return List
     */
    @Override
    public List<T> findAll() {
        return getBaseRepository().findAll();
    }

    /**
     * @param ids ids
     * @return List
     */
    @Override
    public List<T> findList(I[] ids) {
        List<I> idList = Arrays.asList(ids);
        return getBaseRepository().findAllById(idList);
    }

    /**
     * find list.
     *
     * @param spec spec
     * @return list
     */
    @Override
    public List<T> findList(Specification<T> spec) {
        return getBaseRepository().findAll(spec);
    }

    /**
     * find list.
     *
     * @param spec spec
     * @param sort sort
     * @return List
     */
    @Override
    public List<T> findList(Specification<T> spec, Sort sort) {
        return getBaseRepository().findAll(spec, sort);
    }

    /**
     * find one.
     *
     * @param spec spec
     * @return T
     */
    @Override
    public T findOne(Specification<T> spec) {
        return getBaseRepository().findOne(spec).orElse(null);
    }

    /**
     * @param pageable pageable
     * @return Page
     */
    @Override
    public Page<T> findAll(Pageable pageable) {
        return getBaseRepository().findAll(pageable);
    }

    /**
     * count.
     *
     * @return long
     */
    @Override
    public long count() {
        return getBaseRepository().count();
    }

    /**
     * count.
     *
     * @param spec spec
     * @return long
     */
    @Override
    public long count(Specification<T> spec) {
        return getBaseRepository().count(spec);
    }

    /**
     * exists.
     *
     * @param id id
     * @return boolean
     */
    @Override
    public boolean exists(I id) {
        return getBaseRepository().findById(id).isPresent();
    }

    /**
     * save.
     *
     * @param entity entity
     */
    @Override
    public void save(T entity) {
        getBaseRepository().save(entity);
    }

    /**
     * save.
     *
     * @param entities entities
     */
    @Override
    public void save(List<T> entities) {
        getBaseRepository().saveAll(entities);
    }

    /**
     * update.
     *
     * @param entity entity
     * @return T
     */
    @Override
    public T update(T entity) {
        return getBaseRepository().saveAndFlush(entity);
    }

    /**
     * delete.
     *
     * @param id id
     */
    @Override
    public void delete(I id) {
        getBaseRepository().deleteById(id);
    }

    /**
     * delete by ids.
     *
     * @param ids ids
     */
    @Override
    public void deleteByIds(List<I> ids) {
        getBaseRepository().deleteAllByIdInBatch(ids);
    }

    /**
     * delete all.
     */
    @Override
    public void deleteAll() {
        getBaseRepository().deleteAllInBatch();
    }

    /**
     * delete.
     *
     * @param entities entities
     */
    @Override
    public void delete(T[] entities) {
        List<T> tList = Arrays.asList(entities);
        getBaseRepository().deleteAllInBatch(tList);
    }

    /**
     * delete.
     *
     * @param entities entities
     */
    @Override
    public void delete(Iterable<T> entities) {
        getBaseRepository().deleteAllInBatch(entities);
    }

    /**
     * delete.
     *
     * @param entity entity
     */
    @Override
    public void delete(T entity) {
        getBaseRepository().delete(entity);
    }

    /**
     * @param ids ids
     * @return List
     */
    @Override
    public List<T> findList(Iterable<I> ids) {
        return getBaseRepository().findAllById(ids);
    }

    /**
     * @param spec     spec
     * @param pageable pageable
     * @return Page
     */
    @Override
    public Page<T> findAll(Specification<T> spec, Pageable pageable) {
        return getBaseRepository().findAll(spec, pageable);
    }

    /**
     * flush.
     */
    @Override
    public void flush() {
        getBaseRepository().flush();
    }

}

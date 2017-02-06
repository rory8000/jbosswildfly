package com.rory.demo.repositories;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

public abstract class BaseRepository<T extends Serializable, PK extends Serializable> {

    @PersistenceContext
    protected EntityManager entityManager;

    protected Class<T> classType;

    public BaseRepository(Class<T> classType) {
        this.classType = classType;
    }

    public List<T> findAll(String text) {
        final TypedQuery<T> query = entityManager.createQuery("from " + classType.getName(), classType);
        return query.getResultList();
    }

    public T findById(Long id) {
        return entityManager.find(classType, id);
    }

    public void persist(T entity) {
        entityManager.persist(entity);
    }

    public T merge(T entity) {
        return entityManager.merge(entity);
    }

    public void delete(T entity) {
        entityManager.remove(entity);
    }
}
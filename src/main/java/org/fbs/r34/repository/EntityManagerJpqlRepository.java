package org.fbs.r34.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Deprecated
@Component
public class EntityManagerJpqlRepository implements JpqlRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public <T> List<T> findByCustomJpql(String jpql, Map<String, Object> params, Class<T> tClass) {
        TypedQuery<T> query = entityManager.createQuery(jpql, tClass);
        if (params != null) {
            params.forEach(query::setParameter);
        }
        return query.getResultList();
    }

}

package org.fbs.r34.repository;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Deprecated
@Component
public interface JpqlRepository {
    <T> List<T> findByCustomJpql(String jpql, Map<String, Object> params, Class<T> tClass);
}

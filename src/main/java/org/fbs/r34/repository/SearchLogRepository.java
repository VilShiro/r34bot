package org.fbs.r34.repository;

import org.fbs.r34.entity.SearchLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchLogRepository extends JpaRepository<SearchLog, Long> {
    @Query("SELECT s FROM SearchLog s")
    Page<SearchLog> findAll(Pageable pageable);
}

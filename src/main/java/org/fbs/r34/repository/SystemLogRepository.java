package org.fbs.r34.repository;

import org.fbs.r34.entity.SystemLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemLogRepository extends JpaRepository<SystemLog, Long> {
    @Query("SELECT s FROM SystemLog s")
    Page<SystemLog> findAll(Pageable pageable);
}

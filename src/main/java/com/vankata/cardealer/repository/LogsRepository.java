package com.vankata.cardealer.repository;

import com.vankata.cardealer.domain.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogsRepository extends JpaRepository<Log, Long> {

    @Query("SELECT l from Log AS l WHERE l.user.username = :username")
    List<Log> findAllByUsername(@Param("username") String username);
}

package com.vankata.cardealer.repository;

import com.vankata.cardealer.domain.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findAllByOrderByMakeAscModelAsc();

    List<Car> findAllByOrderByIdDesc();

    List<Car> findAllByMakeOrderByModelAscTravelledDistanceDesc(String make);

    Car findById(long id);

    @Query("SELECT DISTINCT c.make FROM Car AS c " +
            "ORDER BY c.make")
    List<String> getDistinctByMake();

    @Query("SELECT DISTINCT c.model FROM Car AS c " +
            "WHERE c.make = :make " +
            "ORDER BY c.model")
    List<String> getDistinctModelsByMake(@Param("make") String make);
}

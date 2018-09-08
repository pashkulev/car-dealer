package com.vankata.cardealer.repository;

import com.vankata.cardealer.domain.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findById(long id);

    Customer findByName(String name);

    @Query("SELECT c FROM Customer AS c ORDER BY c.birthDate, c.isYoungDriver")
    List<Customer> findAllByBirthDateAndYoungDriver();

    List<Customer> findAllByOrderByBirthDateDescIsYoungDriver();

    List<Customer> findAllByOrderByName();
}

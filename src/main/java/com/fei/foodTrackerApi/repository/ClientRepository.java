package com.fei.foodTrackerApi.repository;

import com.fei.foodTrackerApi.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    boolean existsByAccountId(Integer accountId);
    Optional<Client> findIdByAccountId(Integer accountId);
}

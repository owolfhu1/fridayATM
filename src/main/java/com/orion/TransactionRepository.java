package com.orion;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
    ArrayList<Transaction> findAllByUserIdOrderByTimeStamp(Integer userId);
}
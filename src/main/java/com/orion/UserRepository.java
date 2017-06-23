package com.orion;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    boolean existsByAccountNumber(Integer accountNumber);
    User findIdByAccountNumber(Integer accountNumber);
}
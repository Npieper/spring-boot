package com.example.spring_boot.repository;

import com.example.spring_boot.entity.BankAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends CrudRepository<BankAccount, Integer> {
}

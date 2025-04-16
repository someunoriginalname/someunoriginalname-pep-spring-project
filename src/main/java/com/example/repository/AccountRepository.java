package com.example.repository;

import com.example.entity.Account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    // User story number 1
    @Query("From account WHERE username = :username")
    Optional<Account> findAccountByUsername(@Param("username") String username);
    
    /** User story number 2
     * 
     * @param username
     * @param Password
     * @return
     */
    @Query("FROM account WHERE username = :username AND password = :password")
    Optional<Account> findAccountByUsernameAndPassword(@Param("username") String username, @Param("password") String Password);

    // User story number 3
    @Query("From account WHERE accountId = :accountId")
    Optional<Account> findAccountById(@Param("accountId") int accountId);
}

package com.example.service;

import com.example.entity.Account;
import com.example.exception.InvalidAccountException;
import com.example.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }
    // User story number 1
    /**
     * 
     * @param account
     * @return The brand new account.
     */
    public Account persistAcconut(Account account) throws InvalidAccountException, DataIntegrityViolationException{
        if(account.getUsername() == "" || account.getPassword().length() < 4)
            throw new InvalidAccountException();
        if(accountRepository.findAccountByUsername(account.getUsername()).isPresent())
            throw new DataIntegrityViolationException("Username is already taken.");
        return accountRepository.save(account);
    }
    // User story number 2
    /**
     * 
     * @param account
     * @return The account information, if login is successful.
     */
    public Account loginAccount(Account account){
        Optional<Account> optionalAccount = accountRepository.findAccountByUsernameAndPassword(
            account.getUsername(), 
            account.getPassword());
        if(optionalAccount.isPresent()){
            return optionalAccount.get();
        }
        return null;
    }
}

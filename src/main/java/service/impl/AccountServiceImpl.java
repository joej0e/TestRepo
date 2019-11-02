package service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import repository.AccountRepository;
import service.AccountService;

public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public void fundAccount(Long accountId, Long amount) {

    }

    @Override
    public void withdraw(Long accountId, Long amount) {

    }
}

package springboot.service;

import springboot.model.Account;

public interface AccountService {

    Account fundAccount(Long accountId, String faceValue);

    Account withdraw(Long accountId, Long amount);

    Account create(Account account);
}

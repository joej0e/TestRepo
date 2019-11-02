package service;

public interface AccountService {

    void fundAccount(Long accountId, Long amount);

    void withdraw(Long accountId, Long amount);
}

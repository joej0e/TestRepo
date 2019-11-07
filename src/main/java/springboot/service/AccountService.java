package springboot.service;

public interface AccountService {

    void fundAccount(Long accountId, String faceValue);

    void withdraw(Long accountId, Long amount);
}

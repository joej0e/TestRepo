package service;

import java.util.Map;

public interface AtmService {

    void fundAccount(Long accountId, String faceValue, Long atmId);

    void withdraw(Long accountId, Long amount, Long atmId);

    void addBills(Long atmId, String inputFaceValue);

    Map<String, Long> getAvailableBills(Long atmId);

    void takeAwayBills(Long atmId, String inputFaceValue);
}

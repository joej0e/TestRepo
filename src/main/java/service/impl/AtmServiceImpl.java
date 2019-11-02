package service.impl;

import model.FaceValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.AccountRepository;
import repository.AtmRepository;
import service.AtmService;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class AtmServiceImpl implements AtmService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AtmRepository atmRepository;

    private static final String FIVE100 = "500";
    private static final String TWO100 = "200";
    private static final String ONE100 = "100";

    @Override
    public Map<String, Long> getAvailableBills(Long atmId) {
        Set<FaceValue> faceValues = atmRepository.getOne(atmId).getFaceValues();
        Map<String, Long> quantities = new HashMap<>();
        for (FaceValue faceValue : faceValues) {
            quantities.put(faceValue.getFaceValue(),
                    faceValue.getQuantity() * Integer.parseInt(faceValue.getFaceValue()));
        }
        return quantities;
    }

    @Override
    public void fundAccount(Long accountId, String faceValue, Long atmId) {
        if (faceValue.equals(ONE100) || faceValue.equals(TWO100) || faceValue.equals(FIVE100)) {
            Long currentBalance = accountRepository.getOne(accountId).getBalance();
            accountRepository.getOne(accountId).setBalance(currentBalance + Integer.parseInt(faceValue));
            addBills(atmId, faceValue);
        }
    }

    @Override
    public void withdraw(Long accountId, Long amount, Long atmId) {
        Map<String, Long> availableBills = getAvailableBills(atmId);
        Long balance = accountRepository.getOne(accountId).getBalance();
        if (availableBills.get(FIVE100) >= amount && amount % 500 == 0
                && balance >= amount) {
            accountRepository.getOne(accountId).setBalance(balance - amount);
            takeAwayBills(atmId, FIVE100);
        }
        if (availableBills.get(TWO100) >= amount && amount % 200 == 0
                && balance >= amount) {
            accountRepository.getOne(accountId).setBalance(balance - amount);
            takeAwayBills(atmId, TWO100);
        }
        if (availableBills.get(ONE100) >= amount && amount % 100 == 0
                && balance >= amount) {
            accountRepository.getOne(accountId).setBalance(balance - amount);
            takeAwayBills(atmId, ONE100);
        }
    }

    @Override
    public void addBills(Long atmId, String inputFaceValue) {
        Set<FaceValue> faceValues = atmRepository.getOne(atmId).getFaceValues();
        for (FaceValue faceValue : faceValues) {
            if (inputFaceValue.equals(faceValue.getFaceValue())) {
                Long faceValueQuantity = faceValue.getQuantity();
                faceValue.setQuantity(faceValueQuantity + 1);
            }
        }
    }

    @Override
    public void takeAwayBills(Long atmId, String inputFaceValue) {
        Set<FaceValue> faceValues = atmRepository.getOne(atmId).getFaceValues();
        for (FaceValue faceValue : faceValues) {
            if (inputFaceValue.equals(faceValue.getFaceValue())) {
                Long faceValueQuantity = faceValue.getQuantity();
                faceValue.setQuantity(faceValueQuantity - 1);
            }
        }
    }
}

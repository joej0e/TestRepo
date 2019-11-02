package service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import repository.AccountRepository;
import service.AccountService;
import service.AtmService;

import java.util.HashMap;

public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AtmService atmService;

    private static boolean checkFaceValue(String faceValue) {
        return faceValue.equals(AtmService.FIVE100) ||
                faceValue.equals(AtmService.TWO100) ||
                faceValue.equals(AtmService.ONE100);
    }

    @Override
    public void fundAccount(Long accountId, String faceValue) {
        if (checkFaceValue(faceValue)) {
            atmService.putBillsToAtm(faceValue, 1L);
        }
    }

    @Override
    public void withdraw(Long accountId, Long amount) {
        if (amount <= accountRepository.getOne(accountId).getBalance()) {
            HashMap<String, Long> faceValuesQuantities = atmService.getFaceValuesQuantities();
            faceValuesQuantities.forEach((k, v) -> {
                if (amount <= v && amount % Integer.parseInt(k) == 0
                        && amount / Integer.parseInt(k) < AtmService.LIMIT) {
                    atmService.pickBillsFromAtm(AtmService.ONE100, amount / Integer.parseInt(k));
                }
            });
        }
    }
}

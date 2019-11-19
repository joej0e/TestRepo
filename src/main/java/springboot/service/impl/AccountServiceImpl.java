package springboot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.model.Account;
import springboot.repository.AccountRepository;
import springboot.service.AccountService;
import springboot.service.AtmService;

import java.util.Map;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AtmService atmService;

    @Override
    public Account create(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account fundAccount(Long accountId, String faceValueName) throws IllegalArgumentException {
        atmService.putBillToAtm(faceValueName);
        Long amount = getFaceValueCost(faceValueName);
        Account accountFromDb = accountRepository.getOne(accountId);
        Long currentBalance = accountFromDb.getBalance();
        accountFromDb.setBalance(currentBalance + amount);
        return accountRepository.save(accountFromDb);
    }

    @Override
    public Account withdraw(Long accountId, Long amount) {
        if (accountRepository.getOne(accountId).getBalance() >= amount) {
            String availableFaceValue = getAvailableFaceValue(amount);
            Long quantity = amount / getFaceValueCost(availableFaceValue);
            atmService.pickBillsFromAtm(availableFaceValue, quantity);
            Account account = accountRepository.getOne(accountId);
            Long currentBalance = account.getBalance();
            account.setBalance(currentBalance - amount);
            return accountRepository.save(account);
        }
        throw new IllegalArgumentException("Your balance is too low!!!");
    }

    private static Long getFaceValueCost(String faceValueName) {
        switch (faceValueName) {
            case AtmService.ONE100:
                return 100L;
            case AtmService.TWO100:
                return 200L;
            default:
                return 500L;
        }
    }

    private String getAvailableFaceValue(Long amount) {
        if (amount % 100 == 0) {
            Map<String, Long> quantities = atmService.getFaceValuesQuantities();
            if (quantities.get(AtmService.FIVE100) * 500 >= amount
                    && amount <= AtmService.FIVE100LIMIT
                    && amount % 500 == 0) {
                return AtmService.FIVE100;
            }
            if (quantities.get(AtmService.TWO100) * 200 >= amount
                    && amount <= AtmService.TWO100LIMIT
                    && amount % 200 == 0) {
                return AtmService.TWO100;
            }
            if (quantities.get(AtmService.ONE100) * 100 >= amount
                    && amount <= AtmService.ONE100LIMIT
                    && amount % 100 == 0) {
                return AtmService.ONE100;
            }
        }
        throw new IllegalArgumentException("Put other amount!!!");
    }
}

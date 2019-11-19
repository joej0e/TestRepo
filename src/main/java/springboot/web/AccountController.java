package springboot.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springboot.model.Account;
import springboot.model.FaceValue;
import springboot.model.User;
import springboot.service.AccountService;
import springboot.service.AtmService;
import springboot.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

@RestController
public class AccountController {

    private static final Long INITIAL_BALANCE = 1000L;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AtmService atmService;

    @Autowired
    private UserService userService;

    @RequestMapping("/create_account")
    public Account createAccount(HttpServletRequest request) {
        Account account = new Account();
        String login = currentUserNameSimple(request);
        User user = userService.getByLogin(login);
        account.setUser(user);
        account.setBalance(INITIAL_BALANCE);
        return accountService.create(account);
    }

    private String currentUserNameSimple(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        return principal.getName();
    }

    @RequestMapping("/withdraw")
    public Account withdraw(@RequestParam(value = "accountId") Long accountId,
                         @RequestParam(value = "amount") Long amount) {
        return accountService.withdraw(accountId, amount);
    }

    @RequestMapping("/put_bill_to_atm")
    public FaceValue putBillsToAtm(@RequestParam(value = "faceValue") String faceValue,
                                   @RequestParam(value = "quantity") Long quantity) {
        return atmService.putBillToAtm(faceValue);
    }

    @RequestMapping("/pick_bills_from_atm")
    public FaceValue pickBillsFromAtm(@RequestParam(value = "faceValue") String faceValue,
                                   @RequestParam(value = "quantity") Long quantity) {
        return atmService.pickBillsFromAtm(faceValue, quantity);
    }

    @RequestMapping("/get_face_value_quantities")
    public Map getFaceValueQuantities() {
        return atmService.getFaceValuesQuantities();
    }

    @RequestMapping("/fund_account")
    public Account fundAccount(@RequestParam(value = "accountId") Long accountId,
                               @RequestParam(value = "faceValue") String faceValue) {
        return accountService.fundAccount(accountId, faceValue);
    }
}




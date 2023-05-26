package by.bsuir.vladiyss.controller;

import by.bsuir.vladiyss.exception.EntityNotFoundException;
import by.bsuir.vladiyss.model.Account;
import by.bsuir.vladiyss.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public List<Long> findAllAccountIds() {
        return this.accountService.findAllAccountIds();
    }

    @GetMapping("/account/{id}/balance")
    public Long findAccountBalanceByAccountId(@PathVariable Long id) {
        return this.accountService.findBalance(id)
                .orElseThrow(() -> new EntityNotFoundException(Account.class.getSimpleName(), id));
    }

    @PostMapping("/account/{id}/change-balance")
    public void changeAccountBalance(@PathVariable Long id, @RequestBody final Map<String, Long> body) {
        this.accountService.changeBalance(id, body.get("amount"));
    }
}

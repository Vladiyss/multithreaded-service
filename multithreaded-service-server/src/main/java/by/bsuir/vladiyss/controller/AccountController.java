package by.bsuir.vladiyss.controller;

import by.bsuir.vladiyss.exception.EntityNotFoundException;
import by.bsuir.vladiyss.model.Account;
import by.bsuir.vladiyss.service.AccountService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/ids")
    public List<Long> findAllAccountIds() {
        return this.accountService.findAllAccountIds();
    }

    @GetMapping("/account/{id}/balance")
    public Long findAccountBalanceByAccountId(@PathVariable @NotNull Long id) {
        return this.accountService.findBalance(id)
                .orElseThrow(() -> new EntityNotFoundException(Account.class.getSimpleName(), id));
    }

    @PatchMapping("/account/{id}/change-balance")
    public void changeAccountBalance(@PathVariable @NotNull Long id, @RequestBody @NotNull final Long amount) {
        this.accountService.changeBalance(id, amount);
    }
}

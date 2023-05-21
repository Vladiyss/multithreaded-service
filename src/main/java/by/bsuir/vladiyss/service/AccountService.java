package by.bsuir.vladiyss.service;

import by.bsuir.vladiyss.model.Account;

import java.util.Optional;

public interface AccountService {

    Optional<Long> findBalance(final Long id);
    void changeBalance(final Account account, final Long amount);

    Optional<Account> findById(final Long id);
}

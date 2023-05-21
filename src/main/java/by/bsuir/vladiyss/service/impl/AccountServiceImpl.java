package by.bsuir.vladiyss.service.impl;

import by.bsuir.vladiyss.model.Account;
import by.bsuir.vladiyss.repository.AccountRepository;
import by.bsuir.vladiyss.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    public Optional<Long> findBalance(final Long id) {
        return this.accountRepository.findById(id).map(Account::getBalance);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void changeBalance(final Account account, final Long amount) {
        account.setBalance(account.getBalance() + amount);
        this.accountRepository.save(account);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Account> findById(Long id) {
        return this.accountRepository.findById(id);
    }
}

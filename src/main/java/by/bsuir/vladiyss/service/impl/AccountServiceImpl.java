package by.bsuir.vladiyss.service.impl;

import by.bsuir.vladiyss.model.Account;
import by.bsuir.vladiyss.repository.AccountRepository;
import by.bsuir.vladiyss.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional
@RequiredArgsConstructor
@EnableScheduling
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AtomicInteger findBalanceCount = new AtomicInteger(0);
    private final AtomicInteger changeBalanceCount = new AtomicInteger(0);

    @Value("${fixedDelay.in.seconds}")
    private int FIXED_DELAY_IN_SECONDS;

    @Override
    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    public Optional<Long> findBalance(final Long id) {
        findBalanceCount.incrementAndGet();

        return this.accountRepository.findById(id).map(Account::getBalance);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void changeBalance(final Account account, final Long amount) {
        changeBalanceCount.incrementAndGet();
        account.setBalance(account.getBalance() + amount);
        this.accountRepository.save(account);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Account> findById(Long id) {
        return this.accountRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> findAllAccountIds() {
        return this.accountRepository.findAll()
                .stream()
                .map(Account::getId)
                .toList();
    }

    @Scheduled(
            initialDelayString = "${requestStatisticsCollector.initDelay.in.milliseconds}",
            fixedDelayString = "${requestStatisticsCollector.fixedDelay.in.milliseconds}")
    protected void requestStatisticsCollector() {
        log.info("Recurrent READ_BALANCE request statistics collection state - {} request per second \n" +
            "Recurrent CHANGE_BALANCE request statistics collection state - {} request per second \n",
            (double) findBalanceCount.get() / FIXED_DELAY_IN_SECONDS,
            (double) changeBalanceCount.get() / FIXED_DELAY_IN_SECONDS);

        findBalanceCount.set(0);
        changeBalanceCount.set(0);
    }
}

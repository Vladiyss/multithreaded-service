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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
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
    private final Map<Long, Long> accountBalances = new ConcurrentHashMap<>();

    @Value("${fixedDelay.in.seconds}")
    private int FIXED_DELAY_IN_SECONDS;

    @Override
    @Transactional(readOnly = true)
    public Optional<Long> findBalance(final Long id) {
        findBalanceCount.incrementAndGet();

        log.debug("GETTING BALANCE: ID - {}", id);

        return Optional.ofNullable(this.accountBalances.get(id))
                .filter(balance -> balance != Long.MIN_VALUE)
                .or(() -> this.accountRepository.findBalance(id));
    }

    @Override
    @Transactional
    public void changeBalance(final Long id, final Long amount) {
        this.accountBalances.computeIfPresent(id, (ignoredKey, ignoredValue) -> Long.MIN_VALUE);
        changeBalanceCount.incrementAndGet();

        this.accountRepository.findById(id).ifPresent(a -> {
            log.debug("CHANGING BALANCE: ID - {} :: AMOUNT - {}", id, amount);

            final long newBalance = a.getBalance() + amount;
            a.setBalance(newBalance);
            this.accountRepository.save(a);

            log.debug("CHANGING BALANCE: ID - {} :: AMOUNT - {} - SUCCESS", id, amount);

            this.accountBalances.put(a.getId(), newBalance);
        });
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
        log.trace("\nRecurrent READ_BALANCE request statistics collection state - {} request per second \n" +
            "Recurrent CHANGE_BALANCE request statistics collection state - {} request per second \n",
            (double) findBalanceCount.get() / FIXED_DELAY_IN_SECONDS,
            (double) changeBalanceCount.get() / FIXED_DELAY_IN_SECONDS);

        findBalanceCount.set(0);
        changeBalanceCount.set(0);
    }
}

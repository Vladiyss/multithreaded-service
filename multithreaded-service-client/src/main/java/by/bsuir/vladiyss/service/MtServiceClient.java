package by.bsuir.vladiyss.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class MtServiceClient {

    private final List<Integer> accountIds = new ArrayList<>();
    private final RestTemplate restTemplate;

    @Value("${concurrency.thread.count}")
    private int THREAD_NUMBER;
    @Value("${multithreadedService.accounts.ids.url}")
    private String MULTITHREADED_SERVICE_ACCOUNTS_IDS_URL;
    @Value("${multithreadedService.accounts.getBalance.url}")
    private String MULTITHREADED_SERVICE_ACCOUNTS_GET_BALANCE_URL;
    @Value("${multithreadedService.accounts.changeBalance.url}")
    private String MULTITHREADED_SERVICE_ACCOUNTS_CHANGE_BALANCE_URL;
    @Value("${balanceChange.amount}")
    private long BALANCE_CHANGE_AMOUNT;

    public void start() {
        accountIds.addAll(this.getAccountIds());

        final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_NUMBER);
        IntStream.range(0, THREAD_NUMBER)
            .forEach(ignored -> executorService.submit(this::task));
    }

    private List<Integer> getAccountIds() {
        return restTemplate.getForObject(MULTITHREADED_SERVICE_ACCOUNTS_IDS_URL, List.class);
    }

    private void task() {
        double readQuota;
        double writeQuota;
        double readProbability;

        while (true) {
            readQuota = ThreadLocalRandom.current().nextDouble();
            writeQuota = 1 - readQuota;

            readProbability = readQuota / (readQuota + writeQuota);

            if (ThreadLocalRandom.current().nextDouble() < readProbability) {
                getBalance(this.randomFromList());
            } else {
                changeBalance(this.randomFromList());
            }
        }
    }

    private void getBalance(final int id) {
        log.debug("Trying to get account {} balance", id);

        final Long balance = restTemplate.getForObject(MULTITHREADED_SERVICE_ACCOUNTS_GET_BALANCE_URL, Long.class, id);

        log.debug("Got account {} balance - {}", id, balance);
    }

    private void changeBalance(final int id) {
        log.debug("Trying to change account {} balance with amount {}", id, BALANCE_CHANGE_AMOUNT);

        final HttpEntity<Long> entity = new HttpEntity<>(BALANCE_CHANGE_AMOUNT);
        final Map<String, Integer> urlParams = new HashMap<>();
        urlParams.put("id", id);

        restTemplate.patchForObject(MULTITHREADED_SERVICE_ACCOUNTS_CHANGE_BALANCE_URL, entity, Object.class, urlParams);

        log.debug("Account {} balance was updated", id);
    }
    private int randomFromList() {
        return this.accountIds.get(ThreadLocalRandom.current().nextInt(0, accountIds.size()));
    }
}

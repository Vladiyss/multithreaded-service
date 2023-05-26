package by.bsuir.vladiyss.service;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    Optional<Long> findBalance(final Long id);

    void changeBalance(final Long id, final Long amount);

    List<Long> findAllAccountIds();
}

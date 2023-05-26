package by.bsuir.vladiyss.repository;

import by.bsuir.vladiyss.model.Account;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query(value =
            "SELECT a.balance " +
            "FROM Account a " +
            "WHERE a.id = :id")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Long> findBalance(@Param("id") final Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Account> findById(@Param("id") final Long id);
}

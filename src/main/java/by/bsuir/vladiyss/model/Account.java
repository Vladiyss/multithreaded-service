package by.bsuir.vladiyss.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "account")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Account {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "balance", nullable = false)
    private Long balance;

    @Column(name = "last_updated_time", nullable = false)
    @LastModifiedDate
    private LocalDateTime lastUpdatedTime;
}

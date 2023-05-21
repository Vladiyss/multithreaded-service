package by.bsuir.vladiyss.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.generator.EventType;

import java.time.LocalDateTime;

@Entity
@Table(name = "account")
@Getter
@Setter
public class Account {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "balance", nullable = false)
    private Long balance;

    @Column(name = "last_updated_time", nullable = false)
    private LocalDateTime lastUpdatedTime;
}

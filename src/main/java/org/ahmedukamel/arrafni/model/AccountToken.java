package org.ahmedukamel.arrafni.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.ahmedukamel.arrafni.model.enumration.TokenType;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "account_tokens")
public class AccountToken {
    @Id
    private int otp;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private User user;

    @Column(nullable = false, columnDefinition = "bit(1) default false")
    private boolean revoked;

    @Column(nullable = false, updatable = false)
    private LocalDateTime creation;

    @Column(nullable = false, updatable = false)
    private LocalDateTime expiration;

    @Column(nullable = false, updatable = false)
    private TokenType type;
}
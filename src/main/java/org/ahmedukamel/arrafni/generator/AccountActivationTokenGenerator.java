package org.ahmedukamel.arrafni.generator;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.model.AccountToken;
import org.ahmedukamel.arrafni.model.User;
import org.ahmedukamel.arrafni.model.enumration.TokenType;
import org.ahmedukamel.arrafni.repository.AccountTokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class AccountActivationTokenGenerator implements Function<User, AccountToken> {
    final AccountTokenRepository repository;
    final OTPGenerator generator;

    @Override
    public AccountToken apply(User user) {
        LocalDateTime localDateTime = LocalDateTime.now();
        AccountToken accountToken = new AccountToken();
        accountToken.setUser(user);
        accountToken.setType(TokenType.ACCOUNT_ACTIVATION);
        accountToken.setCreation(localDateTime);
        accountToken.setExpiration(localDateTime.plusMinutes(3));

        do {
            accountToken.setOtp(generator.get());
        } while (repository.existsById(accountToken.getOtp()));

        repository.revokeTokens(user, TokenType.ACCOUNT_ACTIVATION);
        return repository.save(accountToken);
    }
}
package org.ahmedukamel.arrafni.updater.business;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.dto.business.UpdateBusinessRequest;
import org.ahmedukamel.arrafni.mapper.phonenumber.PhoneNumberMapper;
import org.ahmedukamel.arrafni.model.Business;
import org.ahmedukamel.arrafni.model.SocialLink;
import org.ahmedukamel.arrafni.model.embeddable.PhoneNumber;
import org.ahmedukamel.arrafni.repository.BusinessRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class UpdateBusinessRequestUpdater
        implements BiFunction<Business, UpdateBusinessRequest, Business> {

    private final BusinessRepository businessRepository;
    private final PhoneNumberMapper phoneNumberMapper;

    @Override
    public Business apply(Business business, UpdateBusinessRequest request) {
        business.setDescription(request.description().strip());

        Set<PhoneNumber> numbers = request.numbers()
                .stream()
                .flatMap(Stream::ofNullable)
                .map(phoneNumberMapper)
                .collect(Collectors.toSet());
        Assert.notEmpty(numbers, "No valid numbers remain.");
        business.setNumbers(numbers);

        Consumer<SocialLink> consumer = socialLink -> socialLink.setBusiness(business);

        if (Objects.nonNull(request.socialLinks())) {
            Set<SocialLink> socialLinks = request.socialLinks()
                    .stream()
                    .peek(consumer)
                    .collect(Collectors.toSet());

            business.setLinks(socialLinks);
        }

        return businessRepository.save(business);
    }
}
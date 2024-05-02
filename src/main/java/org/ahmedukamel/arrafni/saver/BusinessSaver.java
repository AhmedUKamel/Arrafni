package org.ahmedukamel.arrafni.saver;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.dto.business.CreateBusinessRequest;
import org.ahmedukamel.arrafni.mapper.phonenumber.PhoneNumberMapper;
import org.ahmedukamel.arrafni.model.*;
import org.ahmedukamel.arrafni.model.embeddable.Location;
import org.ahmedukamel.arrafni.repository.BusinessRepository;
import org.ahmedukamel.arrafni.repository.SubCategoryRepository;
import org.ahmedukamel.arrafni.repository.KeywordRepository;
import org.ahmedukamel.arrafni.service.db.DatabaseService;
import org.ahmedukamel.arrafni.util.ContextHolderUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class BusinessSaver implements Function<CreateBusinessRequest, Business> {
    final BusinessRepository businessRepository;
    final SubCategoryRepository subCategoryRepository;
    final PhoneNumberMapper phoneNumberMapper;
    final KeywordRepository keywordRepository;

    @Override
    public Business apply(CreateBusinessRequest request) {
        DatabaseService.unique(businessRepository::existsByEmailIgnoreCase, request.email(), Business.class);

        Set<SubCategory> categories = request.categories()
                .stream()
                .flatMap(Stream::ofNullable)
                .map(subCategoryRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
        Assert.notEmpty(categories, "No valid categories id remain.");

        Set<PhoneNumber> numbers = request.numbers()
                .stream()
                .flatMap(Stream::ofNullable)
                .map(phoneNumberMapper)
                .collect(Collectors.toSet());
        Assert.notEmpty(numbers, "No valid numbers remain.");

        Set<Keyword> keywords = request.keywords()
                .stream()
                .flatMap(Stream::ofNullable)
                .map(Keyword::new)
                .map(keywordRepository::save)
                .collect(Collectors.toSet());
        Assert.notEmpty(keywords, "No valid keywords remain.");

        Business business = new Business();
        Consumer<SocialLink> consumer = socialLink -> socialLink.setBusiness(business);

        Assert.noNullElements(request.socialLinks(), "Social links contains null value.");
        Set<SocialLink> links = request.socialLinks()
                .stream()
                .peek(consumer)
                .collect(Collectors.toSet());

        business.setOwner(ContextHolderUtils.getUser());
        business.setName(request.name().strip());
        business.setDescription(request.description().strip());
        business.setEmail(request.email().strip().toLowerCase());
        business.setSeries(request.series());
        business.setAddress(request.address().strip());
        business.setLocation(new Location(request.latitude(), request.longitude()));
        business.setSubCategories(categories);
        business.setNumbers(numbers);
        business.setKeywords(keywords);
        business.setLinks(links);
        return businessRepository.save(business);
    }
}
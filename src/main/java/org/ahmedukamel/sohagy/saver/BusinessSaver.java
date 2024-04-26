package org.ahmedukamel.sohagy.saver;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.sohagy.dto.business.CreateBusinessRequest;
import org.ahmedukamel.sohagy.mapper.phonenumber.PhoneNumberMapper;
import org.ahmedukamel.sohagy.model.*;
import org.ahmedukamel.sohagy.model.embeddable.Location;
import org.ahmedukamel.sohagy.repository.BusinessRepository;
import org.ahmedukamel.sohagy.repository.CategoryRepository;
import org.ahmedukamel.sohagy.service.db.DatabaseService;
import org.ahmedukamel.sohagy.util.ContextHolderUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BusinessSaver implements Function<CreateBusinessRequest, Business> {
    final BusinessRepository businessRepository;
    final CategoryRepository categoryRepository;
    final PhoneNumberMapper phoneNumberMapper;

    @Override
    public Business apply(CreateBusinessRequest request) {
        DatabaseService.unique(businessRepository::existsByEmailIgnoreCase, request.email(), Business.class);

        Assert.noNullElements(request.categories(), "Categories contains null value.");
        Set<Category> categories = request.categories()
                .stream()
                .map(categoryRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
        Assert.notEmpty(categories, "No valid categories id remain.");

        Assert.noNullElements(request.numbers(), "Numbers contains null value.");
        Set<PhoneNumber> numbers = request.numbers()
                .stream()
                .map(phoneNumberMapper)
                .collect(Collectors.toSet());
        Assert.notEmpty(numbers, "No valid numbers remain.");

        Assert.noNullElements(request.keywords(), "Keywords contains null value.");
        Set<Keyword> keywords = request.keywords()
                .stream()
                .map(Keyword::new)
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
        business.setLocation(new Location(request.latitude(), request.longitude()));
        business.setCategories(categories);
        business.setNumbers(numbers);
        business.setKeywords(keywords);
        business.setLinks(links);
        return businessRepository.save(business);
    }
}
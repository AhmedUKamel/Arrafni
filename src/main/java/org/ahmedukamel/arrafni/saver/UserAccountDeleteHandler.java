package org.ahmedukamel.arrafni.saver;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.arrafni.model.Business;
import org.ahmedukamel.arrafni.model.User;
import org.ahmedukamel.arrafni.repository.*;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class UserAccountDeleteHandler
        implements Consumer<User> {

    private final UserRepository userRepository;
    private final OfferRepository offerRepository;
    private final BusinessRepository businessRepository;
    private final AccessTokenRepository accessTokenRepository;
    private final AnnouncementRepository announcementRepository;

    @Override
    public void accept(User user) {
        user.getBusinesses().forEach(this::deleteBusiness);
        userRepository.deleteUser(user);
        accessTokenRepository.deleteAllByUser(user);
    }

    public void deleteBusiness(Business business) {
        business.getAnnouncements().forEach(announcementRepository::deleteAnnouncement);
        business.getOffers().forEach(offerRepository::deleteOffer);
        businessRepository.deleteBusiness(business);
    }
}
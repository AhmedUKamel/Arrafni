package org.ahmedukamel.sohagy.service.business;

import lombok.RequiredArgsConstructor;
import org.ahmedukamel.sohagy.model.Business;
import org.ahmedukamel.sohagy.repository.BusinessRepository;
import org.ahmedukamel.sohagy.service.db.DatabaseService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminBusinessService implements IAdminBusinessService {
    final BusinessRepository repository;

    @Override
    public void setLockStatus(Long id, boolean locked) {
        Business business = DatabaseService.get(repository::findNonDeletedById, id, Business.class);
        business.setLocked(locked);
        repository.save(business);
    }
}
package org.ahmedukamel.arrafni.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.ahmedukamel.arrafni.annotation.SingleSocialLink;
import org.ahmedukamel.arrafni.model.SocialLink;

import java.util.Collection;

public class SingleSocialLinkValidator implements ConstraintValidator<SingleSocialLink, Collection<SocialLink>> {
    @Override
    public boolean isValid(Collection<SocialLink> links, ConstraintValidatorContext constraintValidatorContext) {
        return links.size() == links.stream().map(SocialLink::getType).distinct().count();
    }
}
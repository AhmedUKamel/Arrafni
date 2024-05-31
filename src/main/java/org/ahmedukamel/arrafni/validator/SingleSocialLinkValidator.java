package org.ahmedukamel.arrafni.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.ahmedukamel.arrafni.annotation.SingleSocialLink;
import org.ahmedukamel.arrafni.model.SocialLink;

import java.util.Collection;
import java.util.Objects;

public class SingleSocialLinkValidator implements ConstraintValidator<SingleSocialLink, Collection<SocialLink>> {
    @Override
    public boolean isValid(Collection<SocialLink> links, ConstraintValidatorContext constraintValidatorContext) {
        return Objects.isNull(links) || links.size() == links.stream().map(SocialLink::getType).distinct().count();
    }
}
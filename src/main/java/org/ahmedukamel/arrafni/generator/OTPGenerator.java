package org.ahmedukamel.arrafni.generator;

import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.function.Supplier;

@Component
public class OTPGenerator implements Supplier<Integer> {
    @Override
    public Integer get() {
        return new Random().nextInt(876_544) + 123_456;
    }
}
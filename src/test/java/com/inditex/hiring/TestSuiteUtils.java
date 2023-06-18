package com.inditex.hiring;

import com.inditex.hiring.objectmother.e2e.offer.models.InstantiationNotAllowed;
import net.datafaker.Faker;

public final class TestSuiteUtils {
    public static final Faker FAKER = new Faker();

    private TestSuiteUtils() throws InstantiationNotAllowed {
        throw new InstantiationNotAllowed(this.getClass().getName());
    }

}

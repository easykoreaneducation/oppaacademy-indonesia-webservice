package com.oppaacademy.springboot.web;

import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

import static org.junit.jupiter.api.Assertions.*;

public class ProfileControllerUnitTest {

    @Test
    public void real_profile_selected() {
        String expectedProfile = "real";
        MockEnvironment mockEnvironment = new MockEnvironment();

        mockEnvironment.addActiveProfile(expectedProfile);
        mockEnvironment.addActiveProfile("oauth");
        mockEnvironment.addActiveProfile("real-db");

        ProfileController profileController = new ProfileController(mockEnvironment);

        String profile = profileController.profile();

        assertEquals(profile, expectedProfile);
    }

    @Test
    public void real_profile_nonexitence_first_selected() {
        String expectedProfile = "oauth";
        MockEnvironment mockEnvironment = new MockEnvironment();

        mockEnvironment.addActiveProfile(expectedProfile);
        mockEnvironment.addActiveProfile("real-db");

        ProfileController profileController = new ProfileController(mockEnvironment);

        String profile = profileController.profile();

        assertEquals(profile, expectedProfile);
    }

    @Test
    public void real_profile_nonexitence_default_selected() {
        String expectedProfile = "default";
        MockEnvironment mockEnvironment = new MockEnvironment();

        ProfileController profileController = new ProfileController(mockEnvironment);

        String profile = profileController.profile();

        assertEquals(profile, expectedProfile);
    }
}

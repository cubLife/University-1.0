package com.gmail.sergick6690.Repository;

import com.gmail.sergick6690.spring.SpringConfig;
import com.gmail.sergick6690.universityModels.Audience;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = SpringConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AudienceRepositoryTest {
    @Autowired
    private AudienceRepository audienceRepository;

    @Test
    void shouldAddAudience() {
        audienceRepository.save(new Audience());
        Audience expected = new Audience(1, 0);
        Audience actual = audienceRepository.findAll().get(0);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindAudienceById() {
        for (int i = 0; i < 5; i++) {
            audienceRepository.save(new Audience());
        }
        Audience expected = new Audience(3, 0);
        Audience actual = audienceRepository.findById(3).get();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindAllAudience() {
        for (int i = 0; i < 5; i++) {
            audienceRepository.save(new Audience());
        }
        int expected = 5;
        int actual = audienceRepository.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void ShouldRemoveAudienceById() {
        for (int i = 0; i <= 5; i++) {
            audienceRepository.save(new Audience());
        }
        Audience audience = audienceRepository.findById(1).get();
        audienceRepository.delete(audience);
        int expected = 5;
        int actual = audienceRepository.findAll().size();
        assertEquals(expected, actual);
    }
}
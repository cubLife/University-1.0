package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.Repository.AudienceRepository;
import com.gmail.sergick6690.exceptions.RepositoryException;
import com.gmail.sergick6690.spring.SpringConfig;
import com.gmail.sergick6690.university.Audience;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = SpringConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class JpaAudienceRepositoryTest {
    private AudienceRepository audienceRepository;
    @Mock
    private AudienceRepository mockAudienceRepository;

    @Autowired
    public JpaAudienceRepositoryTest(AudienceRepository audienceRepository) {
        this.audienceRepository = audienceRepository;
    }


    @Test
    void shouldAddAudience() throws RepositoryException {
        audienceRepository.add(new Audience());
        Audience expected = new Audience(1, 0);
        Audience actual = audienceRepository.findAll().get(0);
        System.out.println(audienceRepository.findAll());
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindAudienceById() throws RepositoryException {
        for (int i = 0; i < 5; i++) {
            audienceRepository.add(new Audience());
        }
        Audience expected = new Audience(3, 0);
        Audience actual = audienceRepository.findById(3);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindAllAudience() throws RepositoryException {
        for (int i = 0; i < 5; i++) {
            audienceRepository.add(new Audience());
        }
        int expected = 5;
        int actual = audienceRepository.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void ShouldRemoveAudienceById() throws RepositoryException {
        for (int i = 0; i <= 5; i++) {
            audienceRepository.add(new Audience());
        }
        audienceRepository.removeById(1);
        int expected = 5;
        int actual = audienceRepository.findAll().size();
        assertEquals(expected, actual);
    }

    @Test
    void shouldRepositoryExceptionWhenAddAudienceMethodCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(mockAudienceRepository).add(new Audience());
        assertThrows(RepositoryException.class, () -> {
            mockAudienceRepository.add(new Audience());
        });
    }

    @Test
    void shouldThrowRepositoryExceptionWhenFindAudienceByIdCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(mockAudienceRepository).findById(anyInt());
        assertThrows(RepositoryException.class, () -> {
            mockAudienceRepository.findById(0);
        });
    }

    @Test
    void shouldThrowRepositoryExceptionWhenFindAllAudienceCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(mockAudienceRepository).findAll();
        assertThrows(RepositoryException.class, () -> {
            mockAudienceRepository.findAll();
        });
    }

    @Test
    void shouldThrowRepositoryExceptionWhenRemoveAudienceByIdeCall() throws RepositoryException {
        doThrow(RepositoryException.class).when(mockAudienceRepository).removeById(anyInt());
        assertThrows(RepositoryException.class, () -> {
            mockAudienceRepository.removeById(0);
        });
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenFindAudienceByIdCall() throws RepositoryException {
        doThrow(IllegalArgumentException.class).when(mockAudienceRepository).findById(anyInt());
        assertThrows(IllegalArgumentException.class, () -> {
            mockAudienceRepository.findById(0);
        });
    }
}
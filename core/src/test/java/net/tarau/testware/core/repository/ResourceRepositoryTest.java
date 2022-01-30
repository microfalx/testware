package net.tarau.testware.core.repository;

import net.tarau.resource.Resource;
import net.tarau.resource.TemporaryFileResource;
import net.tarau.testware.core.model.TestGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ResourceRepositoryTest {

    private ResourceRepository repository;

    @BeforeEach
    void setup() throws IOException {
        repository = new ResourceRepository(TemporaryFileResource.directory("repository").create());
        repository.getResource().empty();
    }

    @Test
    void getSessions() throws IOException {
        TestGenerator generator = new TestGenerator();
        assertEquals(0, repository.getSessions().size());

        repository.store(generator.createSession(1, 1));
        assertEquals(1, repository.getSessions().size());
        repository.store(generator.createSession(1, 1));
        assertEquals(2, repository.getSessions().size());
    }

    @Test
    void findLastSession() throws IOException {
        assertFalse(repository.findMostRecentSession().isPresent());
        generateSessions(5);
        assertTrue(repository.findMostRecentSession().isPresent());
    }

    @Test
    void store() throws IOException {
        TestGenerator generator = new TestGenerator();
        Resource resource = repository.store(generator.createSession(10, 10));
        Assertions.assertThat(resource.length()).isGreaterThan(1500L);
    }

    private void generateSessions(int sessionCount) throws IOException {
        TestGenerator generator = new TestGenerator();
        for (int i = 0; i < sessionCount; i++) {
            repository.store(generator.createSession(1, 1));
        }
    }
}
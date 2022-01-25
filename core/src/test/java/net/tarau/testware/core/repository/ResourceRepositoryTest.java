package net.tarau.testware.core.repository;

import net.tarau.resource.Resource;
import net.tarau.resource.TemporaryFileResource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class ResourceRepositoryTest {

    private ResourceRepository repository;

    @BeforeEach
    void setup() throws IOException {
        repository = new ResourceRepository(TemporaryFileResource.directory("repository_").create());
    }

    @Test
    void getSessions() throws IOException {
    }

    @Test
    void findLastSession() throws IOException {
    }

    @Test
    void store() throws IOException {
        TestGenerator generator = new TestGenerator();
        Resource resource = repository.store(generator.createSession(10, 10));
        Assertions.assertThat(resource.length()).isGreaterThan(2000L);
    }
}
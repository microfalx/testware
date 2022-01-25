package net.tarau.testware.core.repository;

import net.tarau.binserde.SerializerFactory;
import net.tarau.resource.Resource;
import net.tarau.testware.core.model.SessionModel;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

import static net.tarau.binserde.utils.ArgumentUtils.requireNonNull;

public class ResourceRepository extends AbstractRepository {

    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyyDDmm_HHmmss");
    private final Resource resource;

    public ResourceRepository(Resource resource) {
        requireNonNull(resource);
        this.resource = resource;
    }

    @Override
    public Collection<SessionModel> getSessions() throws IOException {
        return null;
    }

    @Override
    public SessionModel findLastSession() throws IOException {
        return null;
    }

    @Override
    public Resource store(SessionModel session) throws IOException {
        requireNonNull(session);

        String fileName = "session_" + TIMESTAMP_FORMATTER.format(LocalDateTime.now()) + ".dat";
        Resource sessionResource = resource.resolve(fileName, Resource.Type.FILE);
        SerializerFactory.serialize(session, sessionResource.getOutputStream());
        return sessionResource;
    }
}

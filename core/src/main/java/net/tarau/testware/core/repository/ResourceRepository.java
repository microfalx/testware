package net.tarau.testware.core.repository;

import net.tarau.resource.Resource;
import net.tarau.testware.core.model.Serde;
import net.tarau.testware.core.model.SessionModel;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static net.tarau.binserde.utils.ArgumentUtils.requireNonNull;

public class ResourceRepository extends AbstractRepository {

    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyyDDmm_HHmmss");
    private final Resource resource;

    public ResourceRepository(Resource resource) {
        requireNonNull(resource);
        this.resource = resource;
    }

    public Resource getResource() {
        return resource;
    }

    @Override
    public Collection<SessionModel> getSessions() throws IOException {
        Collection<SessionModel> sessions = new ArrayList<>();
        for (Resource sessionResource : resource.list()) {
            sessions.add(Serde.deserialize(SessionModel.class, sessionResource.getInputStream()));
        }
        return sessions;
    }

    @Override
    public Optional<SessionModel> findMostRecentSession() throws IOException {
        SessionModel mostRecent = null;
        for (Resource sessionResource : resource.list()) {
            SessionModel current = Serde.deserialize(SessionModel.class, sessionResource.getInputStream());
            mostRecent = current.mostRecent(mostRecent);
        }
        return mostRecent != null ? Optional.of(mostRecent) : Optional.ofNullable(mostRecent);
    }

    @Override
    public Resource store(SessionModel session) throws IOException {
        requireNonNull(session);

        String fileName = "session_" + TIMESTAMP_FORMATTER.format(LocalDateTime.now()) + ".dat";
        Resource sessionResource = resource.resolve(fileName, Resource.Type.FILE);
        Serde.serialize(session, sessionResource.getOutputStream());
        return sessionResource;
    }
}

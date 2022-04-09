package net.microfalx.testware.core.repository;

import net.microfalx.resource.Resource;
import net.microfalx.testware.core.model.ForkModel;
import net.microfalx.testware.core.model.Serde;
import net.microfalx.testware.core.model.SessionModel;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static net.microfalx.binserde.utils.ArgumentUtils.requireNonNull;

public class ResourceRepository extends AbstractRepository {

    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyyDDmm_HHmmss");
    private static final String SESSION_PREFIX = "session_";
    private static final String FORK_PREFIX = "fork_";
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
        for (Resource resource : resource.list()) {
            if (!resource.getName().startsWith(SESSION_PREFIX)) continue;
            ;
            sessions.add(Serde.deserialize(SessionModel.class, resource.getInputStream()));
        }
        return sessions;
    }

    @Override
    public Collection<ForkModel> getForks() throws IOException {
        Collection<ForkModel> forks = new ArrayList<>();
        for (Resource resource : resource.list()) {
            if (!resource.getName().startsWith(FORK_PREFIX)) continue;
            ;
            forks.add(Serde.deserialize(ForkModel.class, resource.getInputStream()));
        }
        return forks;
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

        String fileName = SESSION_PREFIX + TIMESTAMP_FORMATTER.format(LocalDateTime.now()) + ".dat";
        Resource sessionResource = resource.resolve(fileName, Resource.Type.FILE);
        Serde.serialize(session, sessionResource.getOutputStream());
        return sessionResource;
    }

    @Override
    public Resource store(ForkModel fork) throws IOException {
        requireNonNull(fork);

        String fileName = FORK_PREFIX + TIMESTAMP_FORMATTER.format(LocalDateTime.now()) + ".dat";
        Resource sessionResource = resource.resolve(fileName, Resource.Type.FILE);
        Serde.serialize(fork, sessionResource.getOutputStream());
        return sessionResource;
    }
}

package net.microfalx.testware.core.repository;

import net.microfalx.resource.Resource;
import net.microfalx.testware.core.model.ForkModel;
import net.microfalx.testware.core.model.SessionModel;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

/**
 * A repository for test results.
 */
public interface Repository {

    Collection<SessionModel> getSessions() throws IOException;

    Collection<ForkModel> getForks() throws IOException;

    Optional<SessionModel> findMostRecentSession() throws IOException;

    Resource store(SessionModel session) throws IOException;

    Resource store(ForkModel fork) throws IOException;
}

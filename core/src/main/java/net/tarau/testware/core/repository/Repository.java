package net.tarau.testware.core.repository;

import net.tarau.resource.Resource;
import net.tarau.testware.core.model.SessionModel;

import java.io.IOException;
import java.util.Collection;

/**
 * A repository for test results.
 */
public interface Repository {

    Collection<SessionModel> getSessions() throws IOException;

    SessionModel findLastSession() throws IOException;

    Resource store(SessionModel session) throws IOException;
}

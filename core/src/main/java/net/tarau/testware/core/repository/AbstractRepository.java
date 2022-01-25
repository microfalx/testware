package net.tarau.testware.core.repository;

import net.tarau.binserde.SerializerFactory;
import net.tarau.testware.api.Status;
import net.tarau.testware.api.Type;
import net.tarau.testware.core.model.AbstractModel;
import net.tarau.testware.core.model.ForkModel;
import net.tarau.testware.core.model.SessionModel;
import net.tarau.testware.core.model.TestModel;

public abstract class AbstractRepository implements Repository {

    static {
        SerializerFactory serializerFactory = SerializerFactory.getInstance();
        serializerFactory.register(Type.class, AbstractModel.BASE_ENUM_TAG);
        serializerFactory.register(Status.class, AbstractModel.BASE_ENUM_TAG + 1);
        serializerFactory.register(SessionModel.class);
        serializerFactory.register(ForkModel.class);
        serializerFactory.register(TestModel.class);
    }
}

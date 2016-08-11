package cn.hope.appplatform.resource;

import org.slf4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by myhtls on 16/5/27.
 */
@ApplicationScoped
public class EntityManagerProvider {

    @Inject
    private transient Logger logger;

    @PersistenceContext(name = "appPm")
    private EntityManager entityManager;

    @Produces
    public EntityManager createPmsEntityManager() {
        return entityManager;
    }

    protected void closeEntityManager(@Disposes EntityManager pmsPu) {
        if (entityManager.isOpen()) {
            logger.debug("关闭实例");
            entityManager.close();
        }
    }

}

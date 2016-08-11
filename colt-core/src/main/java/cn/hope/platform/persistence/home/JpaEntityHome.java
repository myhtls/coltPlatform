package cn.hope.platform.persistence.home;

import javax.persistence.EntityManager;

/**
 * Created by myhtls on 16/5/24.
 */
public class JpaEntityHome<T> extends AbstractEntityHome<T> {

    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    protected T doLoadEntity() {
        return (T) getEntityManager().find(getEntityClass(), getId());
    }

    @Override
    protected void doInsert() {
        getEntityManager().persist(getEntity());
    }

    @Override
    protected void doCancel() {
        getEntityManager().refresh(getEntity());
    }




    @Override
    protected void doDelete() {
        T t = doLoadEntity();
        if (t != null) {
            getEntityManager().remove(t);
        }
    }
}

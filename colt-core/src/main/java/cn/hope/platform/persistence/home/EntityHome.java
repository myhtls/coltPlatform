package cn.hope.platform.persistence.home;

/**
 * Created by myhtls on 16/5/16.
 */
public interface EntityHome<T> {
    T getEntity();
    void setEntity(T entity);
    Object getId();
    void setId(Object id);

    void insert();
    void cancel();
    void update();
    void remove();

    boolean isManaged();
    boolean isIdSet();
}

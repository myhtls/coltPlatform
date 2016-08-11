package cn.hope.platform.persistence.home;

import java.lang.reflect.ParameterizedType;

/**
 * Created by myhtls on 16/5/16.
 */
public abstract class AbstractEntityHome<T> implements EntityHome<T> {

    private T entity;
    private Object id;
    private boolean managed;
    private Class<T> entityClass;

    public T getEntity() {
        if (entity == null) {
            entity = fetchEntity();
        }
        return entity;
    }

    /**
     * 加载或创建一个实体
     *
     * @return 获取一个实体
     */
    private T fetchEntity() {
        if (isIdSet()) {
            return internalLoadEntity();
        } else {
            return internalCreateEntity();
        }
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;

    }

    /**
     * 内部方法用于创建实体. 不覆盖本方法, 覆盖实现AbstractEntityHome的该方法doCreateEntity()
     * {@link AbstractEntityHome#doCreateEntity()} .
     * 当创建一个实体添加额外的处理,创建 覆盖注解到 <code>doPreCreateEntity</code>和
     * <code>doPostCreateEntity</code>
     *
     * @return 加载实体
     */
    protected T internalCreateEntity() {
        doPreCreateEntity();
        T result = doCreateEntity();
        managed = false;
        doPostCreateEntity(result);
        return result;
    }

    /**
     * 内部方法加载实体. 不覆盖本方法, 覆盖实现AbstractEntityHome的该方法doLoadEntity()
     * {@link AbstractEntityHome#doLoadEntity()} .<code>doPreLoadEntity</code> and
     * <code>doPostLoadEntity</code>.
     *
     * @return 加载实体
     */
    protected T internalLoadEntity() {
        doPreLoadEntity();
        T result = doLoadEntity();
        managed = true;
        doPostLoadEntity(result);
        return result;
    }

    /**
     * 重载这个方法以提供额外的处理之前创建一个实体。
     */
    protected void doPreCreateEntity() {}

    /**
     * 覆盖这个方法来构造对象
     *
     * @return 创建一个新的实例
     */
    protected T doCreateEntity() {
        Class<T> type = getEntityClass();
        T entity = null;
        try {
            entity = type.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return entity;
    }

    /**
     *重载这个方法以提供额外的处理后到新创建的实体.
     *
     * @param entity
     *            新创建的实体
     */
    protected void doPostCreateEntity(T entity) {
        // do nothing
    }

    /**
     * 覆盖前添加额外的处理加载一个实体
     */
    protected void doPreLoadEntity() {
        // do nothing
    }

    /**
     * 覆盖来实现实体加载特定数据源
     *
     * @return 加载实体
     */
    protected abstract T doLoadEntity();

    /**
     * 重写这个方法来设置加载实体添加额外的处理。
     *
     * @param entity
     *           加载实体
     */
    protected void doPostLoadEntity(T entity) {
        // do nothing
    }

    public void insert() {
        doPreInsert();
        doInsert();
        managed = true;
        internalPostInsert();
        doPostInsert();
    }

    /**
     * 覆盖添加添加处理一旦实体被插入
     */
    protected void doPostInsert() {
        // do nothing
    }

    /**
     * 内部使用post-insert处理,添加自己的处理,覆盖 {@link AbstractEntityHome#doPostInsert()} 方法.
     */
    protected void internalPostInsert() {
        // do nothing
    }

    /**
     * 执行实际的插入操作
     */
    protected abstract void doInsert();

    /**
     * 重写这个方法来添加额外的插入前处理的实体。
     */
    protected void doPreInsert() {
        // Do Nothing

    }

    public void cancel() {
        if (isManaged()) {
            doPreCancel();
            doCancel();
            internalPostCancel();
            doPostCancel();
        } else {
            // 因为这不是管理,创建一个新的
            setEntity(internalCreateEntity());
        }
    }


    protected void doPreCancel() {
        // do nothing
    }


    protected abstract void doCancel();

    /**
     * 内部方法提供额外的职位取消处理
     */
    protected void internalPostCancel() {
        // do nothing
    }

    /**
     *
     */
    protected void doPostCancel() {
        // do nothing
    }

    public void update() {
        doPreUpdate();
        doUpdate();
        internalPostUpdate();
        doPostUpdate();
    }


    protected void doPreUpdate() {
        // do nothing
    }


    protected void doUpdate() {
        // do nothing
    }


    protected void internalPostUpdate() {
        // do nothing
    }


    protected void doPostUpdate() {
        // do nothing
    }


    public void remove(){
        doPreDelete();
        doDelete();
        internalPostDelete();
        doPostDelete();
    }

    protected  void doPreDelete(){}
    protected abstract void doDelete();
    protected void internalPostDelete(){}
    protected void doPostDelete(){}


    public boolean isIdSet() {
        return id != null;
    }

    public boolean isManaged() {
        return managed;
    }

    public Class<T> getEntityClass() {
        if (entityClass == null) {
            ParameterizedType parameterizedType = (ParameterizedType) getClass()
                    .getGenericSuperclass();

            Object value = parameterizedType.getActualTypeArguments()[0];
            if (value instanceof Class) {
                return (Class<T>) value;
            } else {
                throw new IllegalArgumentException(
                        "无法提取泛型类型信息,请手动设置使用.");
            }
        }
        return entityClass;
    }

}

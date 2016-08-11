package cn.hope.platform.persistence.dao;

import cn.hope.platform.persistence.BaseDao;
import cn.hope.platform.persistence.jpa.AbstractJpaDataProvider;
import cn.hope.platform.persistence.jpa.JpaQueryProvider;
import cn.hope.platform.persistence.jpa.criteria.JpaQueryCriteriaProvider;
import cn.hope.platform.persistence.utils.EntityUtils;

import javax.persistence.EntityManager;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * Created by htl on 16-7-4.
 */
public abstract class BaseDaoImpl<T> implements BaseDao<T> {

    private Class entityClass;
    private static final Map<ClassField, String> ORDER_BY_MAP = new HashMap<>();
    private static final Logger logger = Logger.getLogger(BaseDaoImpl.class.getName());

    private JpaQueryCriteriaProvider<T> jpaQueryCriteriaProvider;
    private AbstractJpaDataProvider<T> abstractJpaDataProvider;

    public BaseDaoImpl() {
        try {
            Type genericSupercclass = getClass().getGenericSuperclass();
            if (genericSupercclass != null && genericSupercclass instanceof ParameterizedType) {
                Type[] arguments = ((ParameterizedType) genericSupercclass).getActualTypeArguments();
                if (arguments != null && arguments.length > 0) {
                    Object object = arguments[0];
                    if (object instanceof Class<?>) {
                        entityClass = (Class) object;
                    } else if (object instanceof Class) {
                        entityClass = (Class) object;
                    }
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, null, e);
        }
    }

    @Override
    public AbstractJpaDataProvider getJpaDataProvider() {
        if (abstractJpaDataProvider == null) {
            abstractJpaDataProvider = new JpaQueryProvider(getEntityManager(), getEntityClass(),
                    getEntityClass().getSimpleName());
        }
        return abstractJpaDataProvider;
    }

    @Override
    public JpaQueryCriteriaProvider createJpaQueryCriteriaProvider() {
        if (jpaQueryCriteriaProvider == null) {
            jpaQueryCriteriaProvider = new JpaQueryCriteriaProvider(getEntityManager(), getEntityClass());
        }
        return jpaQueryCriteriaProvider;
    }

    /**
     * 通过jpa中的named查询
     *
     * @param named
     * @param clazz
     * @param param
     * @return
     */
    @Override
    public T uniqueByNamedQuery(String named, Class clazz, Map<String, Object> param) {
        TypedQuery<T> query = this.getEntityManager().createNamedQuery(named, clazz);
        param.keySet().stream().forEach((key) -> {
            query.setParameter(key, param.get(key));
        });
        query.setMaxResults(1);
        try {
            return query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }

    }

    @Override
    public T uniqueByNamedQuery(String named, Class clazz, List<Object> param) {
        TypedQuery<T> query = this.getEntityManager().createNamedQuery(named, clazz);
        for (int index = 1; index < param.size(); index++) {
            query.setParameter(index, param.get(index - 1));
        }
        query.setMaxResults(1);
        try {
            return query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public void save(T object) {
        this.getEntityManager().persist(object);
    }

    @Override
    public void update(T object) {
        this.merge(object);
    }

    @Override
    public void delete(Class clazz, Object id) {
        Query query = getEntityManager().createQuery("DELETE FROM " + clazz.getName() + " WHERE " + EntityUtils.getIdFieldName(clazz) + " =?1");
        query.setParameter(1, id);
        query.executeUpdate();
    }

    @Override
    public void batchDeleteById(Class clazz, List ids) {
        Query query
                = getEntityManager().createQuery("DELETE FROM " + clazz.getName() + " WHERE " + EntityUtils.getIdFieldName(clazz) + "  in(1)");
        query.setParameter(1, Arrays.asList(ids));
        query.executeUpdate();
    }

    @Override
    public void remove(T object) {

        if (!getEntityManager().contains(object)) {
            object = getEntityManager().merge(object);
        }

        this.getEntityManager().remove(object);
        this.getEntityManager().flush();

    }

    @Override
    public T merge(T object) {
        if (EntityUtils.isPersisted(object)) {
            return this.getEntityManager().merge(object);
        }
        return null;
    }

    @Override
    public T find(Object id) {
        return (T) getEntityManager().find(this.getEntityClass(), id);
    }

    @Override
    public T find(Class clazz, Object id) {
        return (T) getEntityManager().find(clazz, id);
    }

    @Override
    public List<T> listAll() {
        return getEntityManager()
                .createQuery(createJpaQueryCriteriaProvider().createCriteriaQuery())
                .getResultList();

    }

    /**
     * 基于排序的方式
     *
     * @param order 排序字段
     * @param ascing true asc
     * @return
     */
    @Override
    public List<T> listAll(String[] order, boolean ascing) {
        return this.getEntityManager()
                .createQuery(createJpaQueryCriteriaProvider().defaultEntitySelectOrderBy(order, ascing))
                .getResultList();
    }

    /**
     * 传入指定类进行排序并查询所有值
     *
     * @param clazz 指定类
     * @param order 排序字段
     * @param ascing 排序标识
     * @return
     */
    @Override
    public List<T> listAll(Class clazz, String[] order, boolean ascing) {
        return this.getEntityManager()
                .createQuery(createJpaQueryCriteriaProvider().entitySelectOrderBy(clazz, order, ascing))
                .getResultList();
    }

    @Override
    public Long count() {
        TypedQuery<Long> count = this.getEntityManager().createQuery("select count(c) from " + this.getEntityClass().getSimpleName() + " c", Long.class);
        return count.getSingleResult();
    }

    @Override
    public Long count(Map<String, Object> parameters) {
        return null;
    }

    @Override
    public Long count(Class clazz) {
        TypedQuery<Long> count = this.getEntityManager().createQuery("select count(c) from " + clazz.getName() + " c", Long.class);
        return count.getSingleResult();
    }

    @Override
    public Long count(String proerty, Object value) {
        return null;
    }

    @Override
    public T unique(Map<String, Object> parameters) {
        return null;
    }

    @Override
    public T unique(Class clazz) {
        return null;
    }

    @Override
    public T unique(String proerty, Object value) {
        return null;
    }

    @Override
    public List<T> list(Map<String, Object> parameters) {
        return null;
    }

    @Override
    public List<T> createNameQueryList(String named, Class clazz, Map<String, Object> param) {
        TypedQuery<T> typeQuery = this.getEntityManager().createNamedQuery(named, clazz);
        param.keySet().stream().forEach((key) -> {
            typeQuery.setParameter(key, param.get(key));
        });
        return typeQuery.getResultList();
    }

    @Override
    public List<T> createNameQueryList(String named, Class clazz, String param, Object value) {
        TypedQuery<T> typeQuery = this.getEntityManager().createNamedQuery(named, clazz);
        typeQuery.setParameter(param, value);
        return typeQuery.getResultList();
    }

    @Override
    public List<T> createNameQueryList(String named, Class clazz) {
        return this.getEntityManager().createNamedQuery(named, clazz)
                .getResultList();
    }

    @Override
    public Query createNameQuery(String named, Class clazz) {
        return this.getEntityManager().createNamedQuery(named, clazz);
    }
    
    @Override
    public Query createQuery(String sql, Class clazz) {
        return this.getEntityManager().createQuery(sql, clazz);
    }

    @Override
    public Query createQuery(String sql) {
        return this.getEntityManager().createQuery(sql);
    }

    @Override
    public List<T> list(Map<String, Object> parameters, String order) {
        return null;
    }

    @Override
    public List<T> list(Map<String, Object> parameters, String order, Integer firstResult, Integer maxResults) {
        return null;
    }

    @Override
    public List<T> list(String property, Object value, String order) {
        return null;
    }

    @Override
    public List<T> list(String property, Object value) {
        return null;
    }

    @Override
    public List<T> listAttributes(String attributes) {
        return null;
    }

    @Override
    public List<T> listAttributes(String attributes, String order) {
        return null;
    }

    @Override
    public List<T> listAttributes(String property, Object value, String attributes, String order) {
        return null;
    }

    @Override
    public List<T> listAttributes(Map<String, Object> parameters, String attributes, String order) {
        return null;
    }

    @Override
    public List<T> listAttributes(Map<String, Object> parameters, String attributes) {
        return null;
    }

    @Override
    public Connection getConnection() {
        return (Connection) this.getEntityManager().unwrap(Connection.class);
    }

    @Override
    public Class getEntityClass() {
        return entityClass;
    }

    @Override
    public void setEntityClass(Class entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public abstract EntityManager getEntityManager();

 
}

package cn.hope.platform.persistence;

import cn.hope.platform.persistence.exception.DeleteException;
import cn.hope.platform.persistence.jpa.AbstractJpaDataProvider;
import cn.hope.platform.persistence.jpa.criteria.JpaQueryCriteriaProvider;

import javax.persistence.EntityManager;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;

/**
 * Created by myhtls on 16/7/2.
 */
public interface BaseDao<T> {

    /**
     * 创建一个实体管理器
     * @return
     */
    public EntityManager getEntityManager();

    public  AbstractJpaDataProvider getJpaDataProvider();
    
     public JpaQueryCriteriaProvider createJpaQueryCriteriaProvider();
    
    /**
     * 返回jdbc连接
     * @return
     */
    public Connection getConnection();

    public Class getEntityClass();

    public void setEntityClass(Class entityClass);

    public void save(T object);

    public void update(T object);

    public void delete(Class clazz,Object id);
    
    public void batchDeleteById(Class clazz,List ids);
    
    public void remove(T object);

  

    public T merge(T object);

    public List<T> listAll();

    public List<T> listAll(String[] order,boolean ascing);

    public List<T> listAll(Class clazz,String[] order,boolean ascing);

    public T find(Object id);

    public T find(Class clazz,Object id);

    public Long count();
    public Long count(Map<String,Object> parameters);
    public Long count(Class clazz);
    public Long count(String proerty,Object value);

    public T unique(Map<String,Object> parameters);
    public T unique(Class clazz);
    public T unique(String proerty,Object value);
    public T uniqueByNamedQuery(String named,Class clazz,Map<String,Object> param);
    public T uniqueByNamedQuery(String named,Class clazz,List<Object> param);
    
    public List<T> list(Map<String,Object> parameters);
    public List<T> list(Map<String,Object> parameters,String order);
    public List<T>  list(Map<String,Object> parameters,String order,Integer firstResult,Integer maxResults);

    public List<T> list(String property,Object value,String order);
    public List<T> list(String property,Object value);

    public List<T> createNameQueryList(String named,Class clazz,Map<String,Object> param);
    public List<T> createNameQueryList(String named,Class clazz,String key,Object param);
    public List<T> createNameQueryList(String named,Class clazz);
    public Query createNameQuery(String named,Class clazz);
    public Query createQuery(String sql);
    public Query createQuery(String sql,Class clazz);
    public List<T> listAttributes(String attributes);
    public List<T> listAttributes(String attributes,String order);
    public List<T> listAttributes(String property,Object value,String attributes,String order);
    public List<T> listAttributes(Map<String,Object> parameters,String attributes,String order);
    public List<T> listAttributes(Map<String,Object> parameters,String attributes);





}

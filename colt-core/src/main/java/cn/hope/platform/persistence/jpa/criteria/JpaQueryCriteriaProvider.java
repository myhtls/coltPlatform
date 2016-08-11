/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hope.platform.persistence.jpa.criteria;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

/**
 *
 * @author hutianlong
 */
public class JpaQueryCriteriaProvider<T> {

    private CriteriaBuilder criteriaBuilder;
    private Class<T> entityClass;

    public CriteriaQuery<T> createCriteriaQuery() {
        return criteriaBuilder.createQuery(this.getEntityClass());

    }

    public CriteriaQuery<T> createCriteriaQuery(Class clazz) {
        return criteriaBuilder.createQuery(clazz);

    }

    public Root<T> createRoot() {
        return createCriteriaQuery().from(this.getEntityClass());
    }

    public Root<T> createRoot(Class clazz) {
        return createCriteriaQuery().from(clazz);
    }

    public CriteriaQuery<T> entitySelect(Class clazz) {
        return createCriteriaQuery(clazz).select(createRoot(clazz));
    }
    
    public CriteriaQuery<T> entitySelectOrderBy(Class clazz,String[] orders, boolean ascing){
         return entitySelect(clazz).orderBy(createOrder(orders, ascing));
    }

    /**
     * 返回默认实体
     *
     * @return
     */
    public CriteriaQuery<T> defaultEntitySelect() {
        return createCriteriaQuery().select(createRoot());
    }

    public CriteriaQuery<T> defaultEntitySelectOrderBy(String[] orders, boolean ascing) {
        return defaultEntitySelect().orderBy(createOrder(orders, ascing));
    }

    public JpaQueryCriteriaProvider() {
    }

    public JpaQueryCriteriaProvider(EntityManager entityManager, Class<T> clazz) {
        criteriaBuilder = entityManager.getCriteriaBuilder();
        entityClass = clazz;
    }

    public CriteriaBuilder getCriteriaBuilder() {
        return criteriaBuilder;
    }

    public void setCriteriaBuilder(CriteriaBuilder criteriaBuilder) {
        this.criteriaBuilder = criteriaBuilder;
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * 创建排序
     *
     * @param orders 排序字段集
     * @param ascing true asc or false desc
     * @return
     */
    private List<Order> createOrder(String[] orders, boolean ascing) {
        List<Order> orderList = new ArrayList<>();

        if (ascing) {
            for (String str : orders) {
                orderList.add(criteriaBuilder.asc(createRoot().get(str)));
            }
        } else {
            for (String str : orders) {
                orderList.add(criteriaBuilder.desc(createRoot().get(str)));
            }
        }
        return orderList;
    }

}

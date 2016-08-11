/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hope.platform.persistence.jpa;


import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author hutianlong
 * @param <T>
 */
public class JpaQueryProvider<T> extends AbstractJpaDataProvider<T> {

    public JpaQueryProvider() {
    }

    public JpaQueryProvider(EntityManager entityManager) {
        super(entityManager);
    }

    public JpaQueryProvider(EntityManager entityManager, Class<T> clazz, String prefix) {
        super(entityManager, clazz, prefix);
    }

    @Override
    public Query createJpaQuery(String jpql) {
        return getEntityManager().createQuery(jpql);
    }

    @Override
    public TypedQuery<T> createJpaTypeQuery(String sql) {
        return getEntityManager().createQuery(sql, (Class<T>) getEntityClass());
    }

}

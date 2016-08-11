/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hope.platform.persistence.jpa;

import cn.hope.platform.persistence.provider.AbstractQueryDataProvider;
import cn.hope.platform.persistence.utils.DataQuery;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author hutianlong
 */
public abstract class AbstractJpaDataProvider<T> extends
        AbstractQueryDataProvider<T> implements JpaProvider {

    private EntityManager entityManager;

    public AbstractJpaDataProvider() {
    }

    public AbstractJpaDataProvider(EntityManager entityManager) {
        super();
        this.entityManager = entityManager;
    }

    public AbstractJpaDataProvider(EntityManager entityManager, Class<T> clazz, String prefix) {
        this.init(clazz, prefix);
        this.entityManager = entityManager;
    }

    private Query buildJpaQuery(DataQuery dataQuery) {
        Query query = createJpaQuery(dataQuery.getStatement());
        dataQuery.getParameters().stream().forEach((param) -> {
            query.setParameter(param.getName(), param.getValue());
        });
        return query;
    }

    private TypedQuery<T> buildJpaTypeQuery(DataQuery dataQuery) {
        TypedQuery<T> typeQuery = this.createJpaTypeQuery(dataQuery.getStatement());
        dataQuery.getParameters().stream().forEach((param) -> {
            typeQuery.setParameter(param.getName(), param.getValue());
        });
        return typeQuery;
    }

    @Override
    public Integer queryForCount(DataQuery dataQuery) {
        Query query = buildJpaQuery(dataQuery);
        Long result = (Long) query.getSingleResult();
        return result.intValue();
    }

    @Override
    public List<T> queryForResults(DataQuery dataQuery, Integer firstResult, Integer count) {
        TypedQuery<T> typeQuery = buildJpaTypeQuery(dataQuery);
        if (firstResult != null) {
            typeQuery.setFirstResult(firstResult);
        }

        if (count != null) {
            typeQuery.setMaxResults(count);
        }

        return typeQuery.getResultList();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}

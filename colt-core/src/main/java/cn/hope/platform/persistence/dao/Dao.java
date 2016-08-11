/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hope.platform.persistence.dao;


import javax.persistence.EntityManager;

/**
 *
 * @author hutianlong
 */
public class Dao<T> extends BaseDaoImpl<T> {

    private EntityManager entityManager;

    public Dao(Class entityClass) {
        super.setEntityClass(entityClass);
    }

    public Dao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Dao(Class entityClass, EntityManager entityManager) {
        super.setEntityClass(entityClass);
        this.entityManager = entityManager;
    }

    public Dao() {
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

   
}

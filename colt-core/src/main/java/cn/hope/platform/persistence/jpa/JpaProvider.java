/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hope.platform.persistence.jpa;


import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author hutianlong
 */
public interface JpaProvider<T> {

    /**
     * 根据jpql 返回一个Query
     *
     * @param jpql
     * @return
     */
    public Query createJpaQuery(String jpql);

    public TypedQuery<T> createJpaTypeQuery(String sql);

    

}

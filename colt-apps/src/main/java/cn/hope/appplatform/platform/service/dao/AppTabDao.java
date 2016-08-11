/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hope.appplatform.platform.service.dao;

import cn.hope.platform.core.entity.platform.AppTabs;
import cn.hope.platform.persistence.dao.Dao;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author hutianlong
 */
@Singleton
public class AppTabDao extends Dao<AppTabs>{
    private @Inject
    EntityManager entityManager;
    
    @PostConstruct
    public void init(){
        this.setEntityManager(entityManager);
    }
    
    public List<AppTabs> findAll(){
        return entityManager.createNamedQuery(AppTabs.FINDALL).getResultList();
    }


    public AppTabs find(long key){
        return super.find(key);
    }
}

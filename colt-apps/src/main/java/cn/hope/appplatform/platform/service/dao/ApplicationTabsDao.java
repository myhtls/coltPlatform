/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hope.appplatform.platform.service.dao;

import cn.hope.platform.core.entity.platform.ApplicationTabs;
import cn.hope.platform.persistence.dao.Dao;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author hutianlong
 */
@Singleton
public class ApplicationTabsDao extends Dao<ApplicationTabs>{
    
     private @Inject
    EntityManager entityManager;
    
    @PostConstruct
    public void init(){
        this.setEntityManager(entityManager);
    }
    
    public void deleteByAppIdAndAppTabsId(String appId,String appTabsId){
        entityManager.createQuery(ApplicationTabs.REMOVEBYAPPIDANDAPPTABID)
                    .setParameter("appId",appId).setParameter("atId",appTabsId).executeUpdate();
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hope.appplatform.platform.service.dao;

import cn.hope.platform.core.entity.platform.Application;
import cn.hope.platform.persistence.dao.Dao;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * 应用程序管理
 *
 * @author hutianlong
 */
@Singleton
public class ApplicationDao extends Dao<Application>{

    private @Inject
    EntityManager entityManager;
    
    @PostConstruct
    public void init(){
        this.setEntityManager(entityManager);
    }
    
    
    /**
     * 查询所有
     * @return 
     */
     public List<Application> findAll(){
        return entityManager.createNamedQuery(Application.FINDALL).getResultList();
    }
     
     /**
      * 查询唯一值
      * @param param
      * @return 
      */
      public Application uniqueByNamedQuery(Map<String,Object> param){
          return this.uniqueByNamedQuery(Application.REAPINAME,Application.class, param);
      }

}

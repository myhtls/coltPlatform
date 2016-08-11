/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hope.appplatform.platform.service.dao;


import cn.hope.platform.core.entity.base.User;
import cn.hope.platform.persistence.dao.Dao;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author hutianlong
 */
@Singleton
public class UserDao extends Dao<User>{
    
    private @Inject EntityManager entityManager;
    
    @PostConstruct
    public void init(){
        this.setEntityManager(entityManager);
    }
    
    /**
     * 登陆查询
     * @param userName
     * @param password
     * @return 
     */
    public User findUseByUserNameAndPassword(String userName,String password){
        Map<String,Object> map = new HashMap<>();
        map.put("userName", userName);
        map.put("password", password);
        return uniqueByNamedQuery(User.LOGINSQL, User.class, map);
    }
    
    
  
    
}

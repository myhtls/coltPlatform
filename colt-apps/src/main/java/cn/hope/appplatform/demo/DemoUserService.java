/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hope.appplatform.demo;

import cn.hope.appplatform.platform.service.dao.UserDao;
import cn.hope.platform.core.entity.base.User;
import cn.hope.platform.persistence.dao.Dao;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author hutianlong
 */
@Stateless
public class DemoUserService extends Dao<User>{
    
    private @Inject UserDao userDao;

    @Override
    public EntityManager getEntityManager() {
        return userDao.getEntityManager(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}

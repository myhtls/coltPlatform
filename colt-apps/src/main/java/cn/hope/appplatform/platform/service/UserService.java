/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.hope.appplatform.platform.service;

import cn.hope.appplatform.platform.service.dao.UserDao;
import cn.hope.platform.core.entity.base.User;
import cn.hope.platform.persistence.dao.BaseDaoImpl;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author hutianlong
 */
@Stateless
public class UserService extends BaseDaoImpl<User>{
    
    private @Inject UserDao userDao;
    
    public User login(String userName,String password){
        return userDao.findUseByUserNameAndPassword(userName, password);
    }

    @Override
    public EntityManager getEntityManager() {
        return userDao.getEntityManager();
    }
    
}

package cn.hope.appplatform.platform.service;

import cn.hope.appplatform.platform.service.dao.AppTabDao;
import cn.hope.platform.core.entity.platform.AppTabs;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by myhtls on 16/6/8.
 */
@Stateless
public class AppTabService {

    private
    @Inject
    AppTabDao appTabDao;



    /**
     * 查询所有
     * @return
     */
    public List<AppTabs> findAll(){
        return appTabDao.findAll();
    }


    public AppTabs find(long key){
        return appTabDao.find(AppTabs.class,key);
    }

}

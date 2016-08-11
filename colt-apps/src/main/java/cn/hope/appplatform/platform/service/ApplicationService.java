package cn.hope.appplatform.platform.service;



import cn.hope.appplatform.exception.RenamingException;
import cn.hope.appplatform.platform.service.dao.ApplicationDao;
import cn.hope.appplatform.platform.service.dao.ApplicationTabsDao;
import cn.hope.appplatform.resource.ApiNameGenerate;
import cn.hope.platform.core.entity.platform.AppTabs;
import cn.hope.platform.core.entity.platform.Application;
import cn.hope.platform.core.entity.platform.ApplicationTabs;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;

/**
 * 应用程序维护
 * Created by myhtls on 16/6/3.
 */
@Stateless
public class ApplicationService {

    
    
    private @Inject ApplicationDao applicationDao;
    
   private @Inject ApplicationTabsDao applicationTabsDao;

    


    /**
     * 查询所有
     * @return
     */
    public List<Application> findAll(){
        return applicationDao.findAll();
    }




    /**
     * 创建一个应用程序，资源图片，应用选项卡.
     * @param application
     * @param appTabsesResult
     * @throws cn.hope.appplatform.exception.RenamingException
     */
    public void createApplication(Application application, List<AppTabs> appTabsesResult) throws RenamingException{

        Application oldApplication = applicationDao.uniqueByNamedQuery(createQueryParam(application));
        if(oldApplication != null) {
            throw new RenamingException("应用程序ApiName重名.");
        }
        application.setApplicationTabsCollection(getApplicationTabs(application,appTabsesResult));
        application.setCustomeObjects(null);
        application.setStandard(true);
        application.setDeployed(true);
        applicationDao.save(application);

    }

    public void updateApplication(Application application) throws RenamingException{
        Application oldApplication = applicationDao.uniqueByNamedQuery(createQueryParam(application));
        if(oldApplication != null && oldApplication.getId() != application.getId()){
            throw new RenamingException("应用程序ApiName重名.");
        }
        application.setApplicationTabsCollection(null);
        applicationDao.merge(application);
    }


    /**
     * 添加应用程序与标签中间数据
     * @param application
     * @param appTabsesResult
     */
    public void createApplicationTabs(Application application,List<AppTabs> appTabsesResult){
        for(ApplicationTabs at:getApplicationTabs(application,appTabsesResult)){
            applicationTabsDao.save(at);
        }
    }

    public void deleteApplicationTabs(Application application,List<AppTabs> appTabsesResult){
        for(AppTabs appTabs: appTabsesResult){
            applicationTabsDao.deleteByAppIdAndAppTabsId(application.getId(), appTabs.getId());
        }
    }

    private Collection<ApplicationTabs> getApplicationTabs(Application application,List<AppTabs> appTabsesResult){
        Collection<ApplicationTabs> result = new ArrayList<>();
        for(AppTabs appTabs : appTabsesResult){
            result.add(new ApplicationTabs(application,appTabs));
        }
        return result;
    }





    private Map<String,Object> createQueryParam(Application application){
        Map<String,Object> param = new HashMap<>();
        param.put("apiName",application.getApiName());
        return param;
    }


    /**
     * 生成apiname名称
     * @param labelName
     * @return
     */
    public String generateApiName(String labelName)
    {
        return ApiNameGenerate.generateApiName(labelName);
    }

    /**
     * 获得相关Application相关的AppTabs数据
     * @param application
     * @return
     */
    public Map<String,AppTabs> getApplicationRelatedAppTabsData(Application application){
        Map<String,AppTabs> selectAppTabsMap = new HashMap<>();
        application.getApplicationTabsCollection().stream().forEach((appTabs) -> {
            selectAppTabsMap.put(appTabs.getAppTabsId().getId(),appTabs.getAppTabsId());
        });
        return selectAppTabsMap;
    }

}

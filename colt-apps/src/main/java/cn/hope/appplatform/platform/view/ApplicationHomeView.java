package cn.hope.appplatform.platform.view;

import cn.hope.appplatform.exception.RenamingException;
import cn.hope.appplatform.platform.service.AppTabService;
import cn.hope.appplatform.platform.service.ApplicationService;
import cn.hope.platform.core.entity.platform.AppTabs;
import cn.hope.platform.core.entity.platform.Application;
import cn.hope.platform.persistence.home.JpaEntityHome;
import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.util.*;

/**
 * Created by myhtls on 16/6/14.
 */
@Named
@ViewScoped
public class ApplicationHomeView extends JpaEntityHome<Application> implements java.io.Serializable {


    private
    @Inject
    ApplicationService applicationService;

    private
    @Inject
    AppTabService appTabService;

    private Application application;

    private List<AppTabs> appTabResult;

    private DualListModel<AppTabs> appTabModels;

    private String stateUrl;


    public void updateApplicationAction() {
        try {
            applicationService.updateApplication(application);
            stateUrl = "applicationList.xhtml";
            Messages.addGlobalInfo("更新成功.");
        } catch (RenamingException re) {
            Messages.addGlobalWarn(re.getMessage());
        }
    }

    public String navigation() {
        return stateUrl;
    }

    /**
     * 初始化
     */
    public void initData() {
        if (!Faces.isAjaxRequest()) {
            application = this.getEntity();
        }
    }

    /**
     * blur事件
     */
    public void apiNameByLabelNameEvent() {
        application.setApiName(applicationService.generateApiName(this.getApplication().getLabelName()));
    }


    public void onTransfer(TransferEvent event) {
        if (event.isAdd()) {
            List<AppTabs> addAppTabs = new ArrayList<>();
            event.getItems().stream().forEach((item) -> {
                addAppTabs.add((AppTabs) item);
            });

            applicationService.createApplicationTabs(application,addAppTabs);
            Messages.addGlobalInfo("添加成功.");
        }else if (event.isRemove()) {
            List<AppTabs> delAppTabs = new ArrayList<>();
            event.getItems().stream().forEach((item) -> {
                delAppTabs.add((AppTabs) item);
            });
            applicationService.deleteApplicationTabs(application,delAppTabs);
            Messages.addGlobalInfo("移除成功.");

        }


    }


    private Map<String, AppTabs> selectAppTabsMap = new HashMap<>();

    public DualListModel<AppTabs> getAppTabModels() {
        if (appTabModels == null) {
            List<AppTabs> temp = new ArrayList<>();////未选择
            List<AppTabs> temp2 = new ArrayList<>();//被选择

            loadAppTabs();

            getAppTabResult().stream().forEach((appTabs) -> {
                if (selectAppTabsMap.containsKey(appTabs.getId())) {
                    temp2.add(appTabs);
                } else {
                    temp.add(appTabs);
                }
            });
            appTabModels = new DualListModel<>(temp, temp2);
        }
        return appTabModels;
    }

    /**
     * 加载AppTabs
     */
    private void loadAppTabs() {
        selectAppTabsMap.clear();
        selectAppTabsMap.putAll(applicationService.getApplicationRelatedAppTabsData(application));
    }


    public void setAppTabModels(DualListModel<AppTabs> appTabModels) {
        this.appTabModels = appTabModels;
    }

    public List<AppTabs> getAppTabResult() {
        if (appTabResult == null) {
            appTabResult = appTabService.findAll();
        }
        return appTabResult;
    }

    @Override
    public Application doLoadEntity() {
        return null;
    }


    @Override
    public EntityManager getEntityManager() {
        return null;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public Map<String, AppTabs> getSelectAppTabsMap() {
        return selectAppTabsMap;
    }
}

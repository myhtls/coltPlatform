package cn.hope.appplatform.platform.view;


import cn.hope.appplatform.exception.RenamingException;
import cn.hope.appplatform.platform.service.AppTabService;
import cn.hope.appplatform.platform.service.ApplicationService;
import cn.hope.platform.core.entity.platform.AppTabs;
import cn.hope.platform.core.entity.platform.Application;
import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Messages;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.DualListModel;


import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by myhtls on 16/6/3.
 */
@Named
@ViewScoped
public class ApplicationWizardView implements java.io.Serializable{

    private @Inject
    ApplicationService applicationService;

    private @Inject
    AppTabService appTabService;

    private List<Application> result;
    private List<AppTabs> appTabResult;

    private DualListModel<AppTabs> appTabModels;

    private boolean skip;
    private Application application;

    private String stateUrl;


   public void saveActionListener(){
        try{
            applicationService.createApplication(this.application,this.appTabModels.getTarget());
            stateUrl = "applicationList.xhtml";
            Messages.addGlobalInfo("保存成功.");
        }catch(RenamingException re){
            Messages.addGlobalWarn(re.getMessage());
        }
   }

    public String navigation(){
        return stateUrl;
    }


    /**
     * blur事件
     */
    public void apiNameByLabelNameEvent(){
        application.setApiName(applicationService.generateApiName(this.getApplication().getLabelName()));
    }



    public String onFlowProcess(FlowEvent event) {
        if(skip) {
            skip = false;
            return "tabId";
        }
        else {
            return event.getNewStep();
        }

    }


    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public Application getApplication() {
        if(application == null){
            application = new Application();
        }
        return application;
    }


    public List<Application> getResult() {
        if(result == null){
            result = applicationService.findAll();
        }
        return result;
    }


    public List<AppTabs> getAppTabResult() {
        if(appTabResult == null){
            appTabResult = appTabService.findAll();
        }
        return appTabResult;
    }


    public DualListModel<AppTabs> getAppTabModels() {
        if(appTabModels == null) {
            appTabModels = new DualListModel<>(getAppTabResult(), new ArrayList<AppTabs>());
        }
        return appTabModels;
    }

    public void setAppTabModels(DualListModel<AppTabs> appTabModels) {
        this.appTabModels = appTabModels;
    }
}

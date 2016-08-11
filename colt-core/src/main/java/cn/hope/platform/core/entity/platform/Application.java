package cn.hope.platform.core.entity.platform;



import cn.hope.platform.core.entity.SuperObject;

import javax.persistence.*;
import java.util.Collection;

/**
 * 定制化－创建－应用程序
 * @author Javen
 */
@NamedQueries({
        @NamedQuery(name = "Application.findAll",query = "select app from Application app order by app.labelName desc"),
        @NamedQuery(name="Application.reApiName",query="select app from Application app where app.apiName=:apiName")
})
@Entity
@Table(name = "application")
public class Application extends SuperObject implements java.io.Serializable{

    public final static String FINDALL = "Application.findAll";
    public final static String REAPINAME = "Application.reApiName";

    private String labelName;
    private String apiName;
    private String description;
    /**
     * 1:是  0:否
     */
    private boolean isStandard;

    /**
     * 1：是  0:否
     */
    private boolean isDeployed;


    @ManyToMany(mappedBy = "applications")
    private Collection<CustomeObject> customeObjects;

    @OneToMany( mappedBy = "applicationId")
    private Collection<ApplicationTabs> applicationTabsCollection;

    public Application() {

    }

    public Collection<ApplicationTabs> getApplicationTabsCollection() {
        return applicationTabsCollection;
    }

    public void setApplicationTabsCollection(Collection<ApplicationTabs> applicationTabsCollection) {
        this.applicationTabsCollection = applicationTabsCollection;
    }

    public Collection<CustomeObject> getCustomeObjects() {
        return customeObjects;
    }

    public void setCustomeObjects(Collection<CustomeObject> customeObjects) {
        this.customeObjects = customeObjects;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStandard() {
        return isStandard;
    }

    public void setStandard(boolean standard) {
        isStandard = standard;
    }

    public boolean isDeployed() {
        return isDeployed;
    }

    public void setDeployed(boolean deployed) {
        isDeployed = deployed;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }
}



package cn.hope.platform.core.entity.platform;



import cn.hope.platform.core.entity.SuperObject;

import javax.persistence.*;
import java.util.Collection;

/**
 * 定制化－创建－自定义对象
 * @author Javen
 */
@Entity
@Table(name = "customeObject")
public class CustomeObject extends SuperObject {

    private String labelName;
    private String apiName;
    private String Description;
    /**
     * 1:是  0:否
     */
    private String isStandard;

    /**
     * 1：是  0:否
     */
    private String isDeployed;

    @JoinTable(name = "applicationObjeces",joinColumns = @JoinColumn(name = "applicationId"),inverseJoinColumns = @JoinColumn(name = "customObjectId"))
    @ManyToMany
    private Collection<Application> applications;

    @OneToMany(mappedBy = "customeObject")
    private Collection<CustomField> customFields;


    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public Collection<Application> getApplications() {
        return applications;
    }

    public void setApplications(Collection<Application> applications) {
        this.applications = applications;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getIsDeployed() {
        return isDeployed;
    }

    public void setIsDeployed(String isDeployed) {
        this.isDeployed = isDeployed;
    }

    public String getIsStandard() {
        return isStandard;
    }

    public void setIsStandard(String isStandard) {
        this.isStandard = isStandard;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public Collection<CustomField> getCustomFields() {
        return customFields;
    }

    public void setCustomFields(Collection<CustomField> customFields) {
        this.customFields = customFields;
    }


}
